package com.cuong.haui.computershop.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cuong.haui.computershop.di.ViewModelFactory
import com.cuong.haui.computershop.di.key.ViewModelKey
import com.cuong.haui.computershop.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel


//    @IntoMap
//    @Binds
//    @ViewModelKey(MainViewModel::class)
//    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel


}
