package com.sangmeebee.wonderland.feature.onboarding.ui

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.sangmeebee.wonderland.core.designsystem.dpToPx
import com.sangmeebee.wonderland.core.ui.BaseFragment
import com.sangmeebee.wonderland.feature.onboarding.databinding.FragmentOnboardingBinding
import com.sangmeebee.wonderland.feature.onboarding.model.OnBoardingProgress

class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpOnBoarding.apply {
            adapter = OnBoardingAdapter(
                listOf(
                    OnBoardingProgress.PROGRESS_1,
                    OnBoardingProgress.PROGRESS_2,
                    OnBoardingProgress.PROGRESS_3,
                    OnBoardingProgress.PROGRESS_FINISH
                )
            )
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


        }
    }
}
