package com.wonder.data.model.festival

import com.google.gson.annotations.SerializedName
import com.wonder.data.model.DataResponse
import com.wonder.data.model.mapper.DataToDomainMapper
import com.wonder.data.model.mapper.mapper
import com.wonder.domain.model.festival.Age
import com.wonder.domain.model.festival.Category
import com.wonder.domain.model.festival.Festival
import com.wonder.domain.model.festival.FestivalItem
import com.wonder.domain.model.festival.FilterItems
import com.wonder.domain.model.festival.Page
import com.wonder.domain.model.festival.Region
import com.wonder.domain.model.festival.State

/**
 * 축제 검색 응답 데이터
 * @param count 축제 검색 결과 개수
 * @param festivals 검색된 축제 목록
 * @param filterItems 필터 아이템
 * @param page 검색된 축제 목록 페이지 정보
 */
internal data class FestivalResponse(
    val count: Int,
    @SerializedName("items") val festivals: List<FestivalItemResponse>,
    @SerializedName("countItems") val filterItems: FilterItemsResponse,
    val page: PageResponse
) : DataResponse, DataToDomainMapper<Festival> {
    override fun mapper() = Festival(
        count = count,
        festivals = festivals.mapper(),
        filterItems = filterItems.mapper(),
        page = page.mapper()
    )
}

/**
 * 축제 아이템 응답 데이터
 * @param festivalId 축제 id
 * @param festivalName 축제 이름
 * @param thumbnailUrl 축제 포스터
 * @param startDate 축제 시작일(yyyy-MM-dd)
 * @param endDate 축제 종료일(yyyy-MM-dd)
 */
internal data class FestivalItemResponse(
    val festivalId: Int,
    val festivalName: String,
    val thumbnailUrl: String?,
    val startDate: String,
    val endDate: String?
) : DataToDomainMapper<FestivalItem> {
    override fun mapper() = FestivalItem(
        festivalId = festivalId,
        festivalName = festivalName,
        thumbnailUrl = thumbnailUrl,
        startDate = startDate,
        endDate = endDate ?: startDate
    )
}

/**
 * 축제 필터 응답 데이터
 */
internal data class FilterItemsResponse(
    val category: CategoryResponse,
    val state: StateResponse,
    val region: RegionResponse,
    val age: AgeResponse
) : DataToDomainMapper<FilterItems> {
    override fun mapper() = FilterItems(
        category = category.mapper(),
        state = state.mapper(),
        region = region.mapper(),
        age = age.mapper()
    )
}

internal data class CategoryResponse(
    val F: Int,
    val H: Int,
    val I: Int,
    val V: Int
) : DataToDomainMapper<Category> {
    override fun mapper() = Category(
        F = F,
        H = H,
        I = I,
        V = V
    )
}

internal data class StateResponse(
    val N: Int,
    val W: Int,
    val Y: Int
) : DataToDomainMapper<State> {
    override fun mapper() = State(
        N = N,
        W = W,
        Y = Y
    )
}

internal data class RegionResponse(
    val BUS: Int,
    val CHC: Int,
    val DEG: Int,
    val DEJ: Int,
    val DHK: Int,
    val GGI: Int,
    val GKS: Int,
    val GWJ: Int,
    val HND: Int,
    val INC: Int,
    val JEL: Int,
    val JEU: Int,
    val KWO: Int,
    val SEL: Int,
    val ULS: Int
) : DataToDomainMapper<Region> {
    override fun mapper() = Region(
        BUS = BUS,
        CHC = CHC,
        DEG = DEG,
        DEJ = DEJ,
        DHK = DHK,
        GGI = GGI,
        GKS = GKS,
        GWJ = GWJ,
        HND = HND,
        INC = INC,
        JEL = JEL,
        JEU = JEU,
        KWO = KWO,
        SEL = SEL,
        ULS = ULS
    )
}

internal data class AgeResponse(
    val GR001: Int,
    val GR013: Int,
    val GR999: Int
) : DataToDomainMapper<Age> {
    override fun mapper() = Age(
        GR001 = GR001,
        GR013 = GR013,
        GR999 = GR999
    )
}

internal data class PageResponse(
    val currentPage: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val offset: Int,
    val offsetTime: Int,
    val page: Int,
    val size: Int,
    val totalItems: Int,
    val totalPages: Int
) : DataToDomainMapper<Page> {
    override fun mapper() = Page(
        currentPage = currentPage,
        hasNext = hasNext,
        hasPrevious = hasPrevious,
        offset = offset,
        offsetTime = offsetTime,
        page = page,
        size = size,
        totalItems = totalItems,
        totalPages = totalPages
    )
}
