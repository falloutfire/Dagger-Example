package com.example.myapplication

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CoreModule::class
    ]
)
interface CoreComponent: ICore {

    @Component.Builder
    interface Builder {
        fun build(): CoreComponent
        @BindsInstance
        fun application(application: Application): Builder
    }

    fun getApp(): Application
}

//Отдаем наружу то что сдалил в CoreModule
interface ICore {
    fun getValue(): String
}

@Module
class CoreModule {
    @Provides
    fun getValue(): String = "TEST"
}

interface CoreComponentProvider {
    fun provideCoreComponent(): CoreComponent
}