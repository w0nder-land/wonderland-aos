package com.imaec.model.festival

import com.wonder.component.util.getDiffDay
import com.wonder.component.util.toDate
import com.wonder.component.util.toDateString
import com.wonder.domain.model.festival.FestivalDetail
import com.wonder.domain.model.festival.FestivalDetailCasting
import com.wonder.domain.model.festival.FestivalDetailLink
import com.wonder.domain.model.festival.FestivalDetailTicketing
import java.util.Date
import java.util.Locale

data class FestivalDetailVo(
    val festivalId: Int,
    val festivalName: String,
    val thumbNailUrl: String?,
    val startDate: String,
    val endDate: String,
    val images: List<String>,
    val runningTime: Int?,
    val categoryType: FestivalCategoryType?,
    val progressState: FestivalStateType?,
    val location: String?,
    val age: FestivalAgeType?,
    val likeCount: Int,
    val viewCount: Int,
    val isLiked: Boolean,
    val bookingLinkUrl: String?,
    val castings: List<FestivalDetailCastingVo>,
    val description: String?,
    val links: List<FestivalDetailLinkVo>,
    val ticketingDate: String?,
    val ticketingItems: List<FestivalDetailTicketingVo>,
    val dDay: String?
)

data class FestivalDetailCastingVo(
    val imageUrl: String?,
    val name: String
)

data class FestivalDetailLinkVo(
    val linkType: FestivalLinkType?,
    val linkUrl: String
)

data class FestivalDetailTicketingVo(
    val linkUrl: String,
    val title: String
)

fun FestivalDetail.toVo() = FestivalDetailVo(
    festivalId = festivalId,
    festivalName = festivalName,
    thumbNailUrl = thumbNailUrl,
    startDate = startDate.toDate("yyyy-MM-dd(EE)", Locale.US).toDateString("yyyy.MM.dd(EE)"),
    endDate = endDate.toDate("yyyy-MM-dd(EE)", Locale.US).toDateString("yyyy.MM.dd(EE)"),
    images = images,
    runningTime = runningTime,
    categoryType = FestivalCategoryType.values().firstOrNull { it.name == categoryType },
    progressState = FestivalStateType.values().firstOrNull { it.name == progressState },
    location = location,
    age = FestivalAgeType.values().firstOrNull { it.name == age },
    likeCount = likeCount,
    viewCount = viewCount,
    isLiked = isLiked,
    bookingLinkUrl = bookingLinkUrl,
    castings = castings.map(FestivalDetailCasting::toVo),
    description = description,
    links = links.map(FestivalDetailLink::toVo),
    ticketingDate = ticketingDate
        ?.toDate("yyyy-MM-dd(EE) HH:mm", Locale.US)
        ?.toDateString("yyyy.MM.dd(EE) HH:mm"),
    ticketingItems = ticketingItems.map(FestivalDetailTicketing::toVo),
    dDay = run {
        val diff = getDiffDay(
            firstDate = ticketingDate?.toDate("yyyy-MM-dd(EE) HH:mm", Locale.US) ?: Date(),
            secondDate = Date()
        )
        if (diff == 0) {
            "D-Day"
        } else if (diff > 0) {
            "D-$diff"
        } else {
            null
        }
    }
)

fun FestivalDetailCasting.toVo() = FestivalDetailCastingVo(
    imageUrl = imageUrl,
    name = name
)

fun FestivalDetailLink.toVo() = FestivalDetailLinkVo(
    linkType = FestivalLinkType.values().firstOrNull { it.name == linkType },
    linkUrl = linkUrl
)

fun FestivalDetailTicketing.toVo() = FestivalDetailTicketingVo(
    linkUrl = linkUrl,
    title = title
)
