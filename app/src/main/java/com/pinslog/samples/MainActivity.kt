package com.pinslog.samples

import android.view.LayoutInflater
import com.pinslog.samples.samples.BaseActivity
import com.pinslog.samples.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getBinding(inflater: LayoutInflater): ActivityMainBinding {
        binding = ActivityMainBinding.inflate(inflater)
        return binding
    }

}