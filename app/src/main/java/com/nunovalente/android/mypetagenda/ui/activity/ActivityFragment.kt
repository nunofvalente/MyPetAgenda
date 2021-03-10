package com.nunovalente.android.mypetagenda.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentActivityBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import javax.inject.Inject

class ActivityFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: ActivityViewModel
    private lateinit var binding: FragmentActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity, container, false)

        viewModel = ViewModelProvider(this, factory).get(ActivityViewModel::class.java)

        return binding.root
    }

}