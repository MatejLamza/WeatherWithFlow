package com.example.weatherappflow.common.state

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class StateLiveData(private val singleObserver: Boolean = false) : MediatorLiveData<State>() {

    private val pending: AtomicBoolean = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in State>) {
        super.observe(owner, if (!singleObserver) observer else Observer {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    override fun setValue(value: State?) {
        pending.set(true)
        super.setValue(value)
    }

    fun setLoading() {
        value = Loading
    }

    fun postLoading() {
        postValue(Loading)
    }

    fun setDone(hasData: Boolean? = null) {
        value = Done(hasData)
    }

    fun postDone(hasData: Boolean? = null) {
        postValue(Done(hasData))
    }

    fun setError(error: Throwable? = null) {
        value = Error(error)
    }

    fun postError(error: Throwable? = null) {
        postValue(Error(error))
    }
}
