package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.*
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Scope
import dagger.Binds
import dagger.MapKey
import dagger.Module
import javax.inject.Singleton
import kotlin.reflect.KClass

@Component(modules = [ActivityBindingModule::class, AndroidInjectionModule::class],
    dependencies = [CoreComponent::class])
@ApplicationScope
interface ApplicationComponent: AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder
        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): ApplicationComponent
    }
}

@Module(includes = [AndroidInjectionModule::class])
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class, ViewModelModule::class, ActivityModule::class])
    @ActivityScope
    abstract fun contributeMainActivity(): MainActivity
}

@Module
class ActivityModule {

    @Provides
    fun getNavigation(activity: MainActivity): FragmentNavigation {
        return FragmentNavigation(activity)
    }
}

@Module(includes = [AndroidInjectionModule::class])
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeBlankFragment(): BlankFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class,])
    abstract fun contributeLoginFragment(): LoginFragment
}

@Module
class FragmentModule {
    @Provides
    fun getArray(): ArrayList<String> {
        return arrayListOf("TEST", "SETS")
    }
}

@Module(includes = [AndroidInjectionModule::class])
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(fragmentViewModel: LoginViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope