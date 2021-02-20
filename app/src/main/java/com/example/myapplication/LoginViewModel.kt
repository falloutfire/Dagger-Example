package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Provider

class LoginViewModel @Inject constructor(test: String): ViewModel() {
    private val _infoText = liveData {
        while (true) {
            emit(System.currentTimeMillis().toString())
            delay(1000)
        }
    }
    val infoText: LiveData<String> = _infoText

    init {
        Log.e("test", test)
    }
}

@ActivityScope
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }
}