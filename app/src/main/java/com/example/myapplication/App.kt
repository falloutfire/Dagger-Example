package com.example.myapplication

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App: DaggerApplication(), CoreComponentProvider {

    lateinit var coreComponent: CoreComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent
            .builder()
            .application(this)
            .coreComponent(provideCoreComponent())
            .build()
    }

    override fun provideCoreComponent(): CoreComponent {
        if (!this::coreComponent.isInitialized) {
            coreComponent = DaggerCoreComponent.builder().application(this).build()
        }
        return coreComponent
    }
}