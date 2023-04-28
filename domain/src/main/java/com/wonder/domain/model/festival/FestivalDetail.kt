package com.wonder.domain.model.festival

data class FestivalDetail(
    val festivalId: Int,
    val festivalName: String,
    val thumbNailUrl: String?,
    val startDate: String,
    val endDate: String,
    val images: List<String>,
    val runningTime: Int?,
    val categoryType: String,
    val progressState: String,
    val location: String?,
    val age: String,
    val likeCount: Int,
    val viewCount: Int,
    val isLiked: Boolean,
    val bookingLinkUrl: String?,
    val castings: List<FestivalDetailCasting>,
    val description: String?,
    val links: List<FestivalDetailLink>,
    val ticketingDate: String?,
    val ticketingItems: List<FestivalDetailTicketing>,
)

data class FestivalDetailCasting(
    val imageUrl: String?,
    val name: String
)

data class FestivalDetailLink(
    val linkType: String,
    val linkUrl: String
)

data class FestivalDetailTicketing(
    val linkUrl: String,
    val title: String
)
