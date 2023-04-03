package com.cuong.haui.computershop.di.component

import android.app.Application
import com.cuong.haui.computershop.App
import com.cuong.haui.computershop.di.module.ActivityModule
import com.cuong.haui.computershop.di.module.ApplicationModule
import com.cuong.haui.computershop.di.module.DatabaseModule
import com.cuong.haui.computershop.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        DatabaseModule::class,
        ViewModelModule::class
    ]
)

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
