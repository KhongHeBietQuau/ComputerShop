package com.cuong.haui.computershop.di.module

import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.di.scope.PerActivity
import com.cuong.haui.computershop.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    internal abstract fun mainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    internal abstract fun splashActivity(): SplashActivity


}
