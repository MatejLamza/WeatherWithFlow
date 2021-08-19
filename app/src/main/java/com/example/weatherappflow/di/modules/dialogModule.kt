package com.example.weatherappflow.di.modules

import androidx.fragment.app.FragmentManager
import com.example.weatherappflow.common.ProgressDialog
import org.koin.dsl.module

val dialogModule = module {
    factory { (manager: FragmentManager) ->
        ProgressDialog(manager)
    }

}
