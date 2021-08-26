package com.cagataykolus.contactsapp.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cagataykolus.contactsapp.databinding.ActivityMainBinding
import com.cagataykolus.contactsapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Çağatay Kölüş on 24.08.2021.
 * cagataykolus@gmail.com
 */
/**
 * [MainActivity] contains fragments.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding { ActivityMainBinding.inflate(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}