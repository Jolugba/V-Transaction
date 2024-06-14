package com.example.v_transaction.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<VB : ViewBinding> : Fragment() {
   protected var _binding: VB? = null
    protected val binding get() = _binding!!

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun run()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        run()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
