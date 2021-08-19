package com.example.weatherappflow.utils.helpers

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.florent37.runtimepermission.kotlin.coroutines.experimental.askPermission

class PermissionHelper {
    suspend fun checkForPermissionFragment(permission: String, sourceFragment: Fragment) {
        kotlin.runCatching {
            sourceFragment.askPermission(permission)
        }.onFailure {
            throw it
        }
    }

    suspend fun checkForPermissionActivity(permission: String, sourceActivity: AppCompatActivity) {
        kotlin.runCatching {
            sourceActivity.askPermission(permission)
        }.onFailure {
            throw it
        }
    }
}
