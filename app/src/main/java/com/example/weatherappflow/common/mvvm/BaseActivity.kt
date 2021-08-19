package com.example.weatherappflow.common.mvvm

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappflow.R
import com.example.weatherappflow.common.ProgressDialog
import com.example.weatherappflow.navigation.NavigationViewModel
import com.example.weatherappflow.utils.extensions.gone
import com.example.weatherappflow.utils.extensions.visible
import com.example.weatherappflow.utils.helpers.ErrorParser
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

abstract class BaseActivity : AppCompatActivity(), View {
    protected val errorParser: ErrorParser by inject()
    protected val navigation: NavigationViewModel by viewModel()
    protected val progressDialog: ProgressDialog by inject { parametersOf(supportFragmentManager) }

    override fun showLoading() {
        kotlin.runCatching {
            findViewById<android.view.View>(R.id.progress)?.visible()
        }
    }

    override fun showError(error: Throwable) {
        val message = errorParser.parse(error)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun dismissLoading() {
        kotlin.runCatching {
            findViewById<android.view.View>(R.id.progress)?.gone()
        }
    }
}
