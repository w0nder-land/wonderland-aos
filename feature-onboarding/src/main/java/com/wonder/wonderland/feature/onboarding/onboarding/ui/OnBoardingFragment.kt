package com.wonder.wonderland.feature.onboarding.onboarding.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.wonder.wonderland.core.designsystem.dpToPx
import com.wonder.wonderland.core.ui.BaseFragment
import com.wonder.wonderland.feature.onboarding.databinding.FragmentOnboardingBinding
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
        initViewPager()
        initDotsIndicator()
    }

    private fun initViewPager() {
        binding.vpOnBoarding.apply {
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            adapter = onBoardingAdapter
            offscreenPageLimit = 1

            val leftPaddingPx = 40.dpToPx()
            setPadding(leftPaddingPx, 0, 0, 0) // left padding
            val pageMarginPx = 40.dpToPx() // 카드 사이 margin
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

    private fun initDotsIndicator() {
        binding.dotsIndicator.attachTo(binding.vpOnBoarding)
    }

    private fun observeViewModel() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            onBoardingViewModel.uiState.collectLatest {
                onBoardingAdapter.submitList(it)
            }
        }
    }
}
