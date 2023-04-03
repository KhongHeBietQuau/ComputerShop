package com.cuong.haui.computershop.base

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import dagger.android.AndroidInjection


abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding>(
    @LayoutRes val layout: Int,
    viewModelClass: Class<VM>
) : Fragment() {

    open lateinit var binding: DB
    lateinit var myContext: Context
    private fun init(inflater: LayoutInflater, container: ViewGroup) {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    open fun init() {}

    protected val viewModel by lazy {
        (activity as? BaseActivity<*, *>)?.viewModelProviderFactory?.let {
            ViewModelProvider(
                this,
                it
            ).get(viewModelClass)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.myContext = context
    }

    open fun onInject() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(activity)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init(inflater, container!!)
        init()
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    open fun refresh() {}

    open fun navigate(action: Int) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action)
        }
    }

    fun getPermission(): Array<String> {
        return arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    fun checkPermission(per: Array<String>): Boolean {
        for (s in per) {
            if (requireActivity().checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    open fun goBack() {
        findNavController().popBackStack()
    }

    fun closeKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //cuiwbgf

}
