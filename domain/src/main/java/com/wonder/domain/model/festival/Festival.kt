package com.wonder.domain.model.festival

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
    val category: Category = Category(0, 0, 0, 0),
    val state: State = State(0, 0, 0),
    val region: Region = Region(
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0
    ),
    val age: Age = Age(0, 0, 0)
)

data class Category(
    val F: Int,
    val I: Int,
    val V: Int,
    val H: Int
)

data class State(
    val Y: Int,
    val W: Int,
    val N: Int
)

data class Region(
    val SEL: Int,
    val DHK: Int,
    val HND: Int,
    val GGI: Int,
    val INC: Int,
    val DEJ: Int,
    val DEG: Int,
    val GWJ: Int,
    val BUS: Int,
    val ULS: Int,
    val CHC: Int,
    val GKS: Int,
    val JEL: Int,
    val KWO: Int,
    val JEU: Int
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
