package com.sangmeebee.wonderland.feature.onboarding.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.sangmeebee.wonderland.core.designsystem.dpToPx
import com.sangmeebee.wonderland.core.ui.BaseFragment
import com.sangmeebee.wonderland.feature.onboarding.databinding.FragmentOnboardingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    private val onBoardingViewModel by viewModels<OnBoardingViewModel>()
    private val onBoardingAdapter = OnBoardingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.vpOnBoarding.apply {
            adapter = onBoardingAdapter
            offscreenPageLimit = 1

            val leftPaddingPx = 40.dpToPx()
            setPadding(leftPaddingPx, 0, 0, 0) // left padding
            val pageMarginPx = 40.dpToPx() // 카드 사이 margin (40 + 30)
            val pagerWidth = 225.dpToPx()
            val screenWidth = resources.displayMetrics.widthPixels
            val offsetPx = screenWidth - pageMarginPx - pagerWidth - leftPaddingPx

            setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    onBoardingViewModel.fetchBlur(position)
                }
            })
        }
    }

    private fun observeViewModel() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            onBoardingViewModel.uiState.collectLatest {
                onBoardingAdapter.submitList(it)
            }
        }
    }
}
