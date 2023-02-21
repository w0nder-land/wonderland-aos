package com.sangmeebee.wonderland.ui

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.sangmeebee.wonderland.R
import com.sangmeebee.wonderland.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnBackPressedDispatcher()
    }

    private fun setOnBackPressedDispatcher() =
        onBackPressedDispatcher.addCallback(this) {
            if (!findNavController(R.id.nav_host_fragment).popBackStack()) {
                finish()
            }
        }
}
