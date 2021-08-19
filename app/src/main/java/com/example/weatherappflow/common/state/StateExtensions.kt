package com.example.weatherappflow.common.state

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.observe
import com.example.weatherappflow.common.mvvm.View
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun LiveData<State>.observe(
    owner: LifecycleOwner,
    view: View? = null,
    onError: ((error: Throwable?) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onDone: ((hasData: Boolean?) -> Unit)? = null
) {
    observe(owner) {
        when (it) {
            is Loading -> onLoading?.invoke() ?: view?.showLoading()
            is Done -> {
                view?.dismissLoading()
                onDone?.invoke(it.hasData)
            }
            is Error -> {
                view?.dismissLoading()
                onError?.invoke(it.throwable)
                    ?: it.throwable?.let { error -> view?.showError(error) }
            }
        }
    }
}


fun exceptionHandler(onError: ((Throwable) -> Unit)) =
    CoroutineExceptionHandler { _, exception ->
        Log.e("ViewModel", "Error in ViewModel ${exception.message}", exception)
        onError(exception)
    }

fun exceptionHandler(data: MutableLiveData<State>? = null) =
    exceptionHandler { data?.postValue(Error(it)) }

fun ViewModel.launchWithState(
    data: MutableLiveData<State>,
    onLoading: (() -> Unit)? = { data.value = Loading },
    onError: ((Throwable) -> Unit)? = { data.value = Error(it) },
    onDone: (() -> Unit)? = { data.value = Done() },
    context: CoroutineContext =
        if (onError != null) exceptionHandler(onError = onError)
        else exceptionHandler(data),
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(context, start) {
    onLoading?.invoke()
    block(this)
    onDone?.invoke()
}

fun ViewModel.launch(
    context: CoroutineContext = exceptionHandler(null),
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(context, start) {
    block(this)
}

fun <T> Flow<T>.asLiveDataWithState(
    data: MutableLiveData<State>? = null,
    onLoading: (() -> Unit)? =
        if (data != null) ({ data.value = Loading })
        else null,
    onError: ((Throwable) -> Unit)? =
        if (data != null) ({ data.value = Error(it) })
        else null,
    onDone: (() -> Unit)? =
        if (data != null) ({ data.value = Done() })
        else null,
    context: CoroutineContext =
        if (onError != null) exceptionHandler(onError)
        else exceptionHandler(data)
): LiveData<T> = onStart { onLoading?.invoke() }
    .catch {
        Log.e("ViewModel", "Error in ViewModel", it)
        onError?.invoke(it)
    }
    .onEach {
        onDone?.invoke()
    }
    .asLiveData(context)
