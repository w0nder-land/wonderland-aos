package com.wonder.data.model.festival

import com.wonder.data.model.DataResponse
import com.wonder.data.model.mapper.DataToDomainMapper
import com.wonder.data.model.mapper.mapper
import com.wonder.domain.model.festival.FestivalDetail
import com.wonder.domain.model.festival.FestivalDetailCasting
import com.wonder.domain.model.festival.FestivalDetailLink
import com.wonder.domain.model.festival.FestivalDetailTicketing

internal data class FestivalDetailResponse(
    val festivalId: Int,
    val festivalName: String,
    val thumbNailUrl: String?,
    val startDate: String,
    val endDate: String?,
    val imageList: List<String>?,
    val runningTime: Int?,
    val categoryType: String,
    val progressState: String,
    val location: String?,
    val age: String,
    val likeCount: Int,
    val viewCount: Int,
    val isLiked: Boolean,
    val bookingLinkUrl: String?,
    val castingList: List<FestivalDetailCastingResponse>?,
    val description: String?,
    val linkList: List<FestivalDetailLinkResponse>?,
    val ticketingDate: String?,
    val ticketingList: List<FestivalDetailTicketingResponse>?,
) : DataResponse, DataToDomainMapper<FestivalDetail> {
    override fun mapper() = FestivalDetail(
        festivalId = festivalId,
        festivalName = festivalName,
        thumbNailUrl = thumbNailUrl,
        startDate = startDate,
        endDate = endDate ?: startDate,
        images = imageList ?: emptyList(),
        runningTime = runningTime,
        categoryType = categoryType,
        progressState = progressState,
        location = location,
        age = age,
        likeCount = likeCount,
        viewCount = viewCount,
        isLiked = isLiked,
        bookingLinkUrl = bookingLinkUrl,
        castings = castingList?.mapper() ?: emptyList(),
        description = description,
        links = linkList?.mapper() ?: emptyList(),
        ticketingDate = ticketingDate,
        ticketingItems = ticketingList?.mapper() ?: emptyList(),
    )
}

internal data class FestivalDetailCastingResponse(
    val imageUrl: String?,
    val name: String
) : DataToDomainMapper<FestivalDetailCasting> {
    override fun mapper() = FestivalDetailCasting(
        imageUrl = imageUrl,
        name = name
    )
}

internal data class FestivalDetailLinkResponse(
    val linkType: String,
    val linkUrl: String
) : DataToDomainMapper<FestivalDetailLink> {
    override fun mapper() = FestivalDetailLink(
        linkType = linkType,
        linkUrl = linkUrl
    )
}

internal data class FestivalDetailTicketingResponse(
    val linkUrl: String,
    val title: String
) : DataToDomainMapper<FestivalDetailTicketing> {
    override fun mapper() = FestivalDetailTicketing(
        linkUrl = linkUrl,
        title = title
    )
}
