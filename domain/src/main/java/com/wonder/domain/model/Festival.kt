package com.wonder.domain.model

data class Festival(
    val count: Int,
    val festivals: List<FestivalItem>,
    val filterItems: FilterItems,
    val page: Page
)

data class FestivalItem(
    val festivalId: Int,
    val festivalName: String,
    val thumbnailUrl: String?,
    val startDate: String,
    val endDate: String,
)

data class FilterItems(
    val category: Category,
    val state: State,
    val region: Region,
    val age: Age
)

data class Category(
    val F: Int,
    val H: Int,
    val I: Int,
    val V: Int
)

data class State(
    val N: Int,
    val W: Int,
    val Y: Int
)

data class Region(
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
)

data class Age(
    val GR001: Int,
    val GR013: Int,
    val GR999: Int
)

data class Page(
    val currentPage: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val offset: Int,
    val offsetTime: Int,
    val page: Int,
    val size: Int,
    val totalItems: Int,
    val totalPages: Int
)
