package com.example.weatherappflow.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.weatherappflow.R
import com.example.weatherappflow.utils.extensions.visibleIf
import kotlinx.android.synthetic.main.dialog_progress.*

class ProgressDialog(
    private val manager: FragmentManager,
    private val title: String? = null,
    private val message: String? = null
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        progressTitle.visibleIf(!title.isNullOrEmpty()) { text = title }
        progressMessage.visibleIf(!message.isNullOrEmpty()) { text = title }
    }

    fun show(tag: String = "progress") {
        if (!isVisible) {
            show(manager, tag)
        }
    }
}
