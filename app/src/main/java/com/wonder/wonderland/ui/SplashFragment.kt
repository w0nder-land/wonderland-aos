package com.wonder.wonderland.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.wonder.wonderland.core.ui.BaseFragment
import com.wonder.wonderland.databinding.FragmentSplashBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val splashViewModel by viewModels<SplashViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            splashViewModel.isReady.collectLatest { isReady ->
                if (isReady) {
                    val action = SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}
