package com.sangmeebee.wonderland.feature.onboarding.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sangmeebee.wonderland.feature.onboarding.R
import com.sangmeebee.wonderland.feature.onboarding.databinding.ItemOnboardingCardBinding
import com.sangmeebee.wonderland.feature.onboarding.model.OnBoardingProgress

class OnBoardingAdapter(private val onBoardingProgress: List<OnBoardingProgress>) : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOnboardingCardBinding.inflate(inflater, parent, false)
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(onBoardingProgress[position])
    }

    override fun getItemCount(): Int = ON_BOARDING_ITEM_COUNT

    class OnBoardingViewHolder(private val binding: ItemOnboardingCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnBoardingProgress) {
            binding.viewMargin.isVisible = item.index != 0 && item.index % 2 == 1
            when (item) {
                OnBoardingProgress.PROGRESS_1 -> R.drawable.img_onboarding_card_1
                OnBoardingProgress.PROGRESS_2 -> R.drawable.img_onboarding_card_2
                OnBoardingProgress.PROGRESS_3 -> R.drawable.img_onboarding_card_3
                OnBoardingProgress.PROGRESS_FINISH -> R.drawable.img_onboarding_card_start
            }.let {
                binding.ivOnBoarding.setImageResource(it)
            }
            binding.ivOnBoarding.setBlur(0)
        }
    }

    companion object {
        private const val ON_BOARDING_ITEM_COUNT = 4
    }
}
