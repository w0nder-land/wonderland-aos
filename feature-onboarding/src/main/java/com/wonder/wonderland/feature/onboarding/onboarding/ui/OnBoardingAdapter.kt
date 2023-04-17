package com.wonder.wonderland.feature.onboarding.onboarding.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wonder.wonderland.feature.onboarding.R
import com.wonder.wonderland.feature.onboarding.databinding.ItemOnboardingCardBinding
import com.wonder.wonderland.feature.onboarding.onboarding.model.OnBoardingProgress
import com.wonder.wonderland.feature.onboarding.onboarding.model.OnBoardingUiState

class OnBoardingAdapter : ListAdapter<OnBoardingUiState, OnBoardingAdapter.OnBoardingViewHolder>(OnBoardingDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOnboardingCardBinding.inflate(inflater, parent, false)
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OnBoardingViewHolder(private val binding: ItemOnboardingCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnBoardingUiState) {
            binding.viewMargin.isVisible = item.onBoardingProgress.index != 0 && item.onBoardingProgress.index % 2 == 1
            when (item.onBoardingProgress) {
                OnBoardingProgress.PROGRESS_1 -> R.drawable.img_onboarding_card_1
                OnBoardingProgress.PROGRESS_2 -> R.drawable.img_onboarding_card_2
                OnBoardingProgress.PROGRESS_3 -> R.drawable.img_onboarding_card_3
                OnBoardingProgress.PROGRESS_FINISH -> R.drawable.img_onboarding_card_start
            }.let { id ->
                binding.ivOnBoarding.setImageResource(id)
            }

            val blurRadius = if (item.isBlur) 2 else 1
            binding.ivOnBoarding.setBlur(blurRadius)
        }
    }
}

class OnBoardingDiffCallback : DiffUtil.ItemCallback<OnBoardingUiState>() {
    override fun areItemsTheSame(
        oldItem: OnBoardingUiState,
        newItem: OnBoardingUiState,
    ): Boolean = oldItem.onBoardingProgress == newItem.onBoardingProgress

    override fun areContentsTheSame(
        oldItem: OnBoardingUiState,
        newItem: OnBoardingUiState,
    ): Boolean = oldItem == newItem
}
