package com.cuong.haui.computershop.ui.main

import com.cuong.haui.computershop.base.BaseViewModel
import com.cuong.haui.computershop.db.LocalDataSource
import javax.inject.Inject

class MainViewModel @Inject constructor(private val localDataSource: LocalDataSource) : BaseViewModel() {
}