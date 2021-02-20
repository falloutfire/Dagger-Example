package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import javax.inject.Inject

class FragmentNavigation (fragmentActivity: Context) {

    private val navController: NavController? by lazy {
        if (fragmentActivity is MainActivity) Navigation.findNavController(
            fragmentActivity,
            R.id.nav_host_fragment
        ) else null
    }

    fun navigateTo(
        action: NavDirections,
        bundle: Bundle? = null,
        extras: FragmentNavigator.Extras? = null
    ) {
        if (bundle != null) {
            navController?.navigate(action.actionId, bundle)
        } else {
            if (extras != null) {
                navController?.navigate(action, extras)
            } else {
                navController?.navigate(action)
            }
        }
    }

    fun navigateTo(action: Int) {
        navController?.navigate(action)
    }

    fun goBack(action: Int, inclusive: Boolean) {
        navController?.popBackStack(action, inclusive)
    }

    fun goBack() {
        navController?.popBackStack()
    }

    fun getCurrentDestination(): NavDestination? {
        return navController?.currentDestination
    }
}