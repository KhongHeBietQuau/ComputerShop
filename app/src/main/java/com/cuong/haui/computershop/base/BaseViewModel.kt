
package com.cuong.haui.computershop.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


open class BaseViewModel @Inject internal constructor() : ViewModel() {
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var isLoading = ObservableBoolean(true)
    var isError = ObservableBoolean(false)

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
