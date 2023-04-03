package com.cuong.haui.computershop
import android.app.Application
import com.cuong.haui.computershop.di.AppInjector
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application() , HasAndroidInjector {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        AndroidThreeTen.init(this)
        instance = this
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}