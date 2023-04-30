package com.imaec.model.festival

import com.wonder.domain.model.festival.Age
import com.wonder.domain.model.festival.Category
import com.wonder.domain.model.festival.FilterItems
import com.wonder.domain.model.festival.Region
import com.wonder.domain.model.festival.State

data class FestivalFilterVo(
    val category: FestivalCategoryCountVo = FestivalCategoryCountVo(0, 0, 0, 0),
    val state: FestivalStateCountVo = FestivalStateCountVo(0, 0, 0),
    val region: FestivalRegionCountVo = FestivalRegionCountVo(
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0
    ),
    val age: FestivalAgeCountVo = FestivalAgeCountVo(0, 0, 0)
)

data class FestivalCategoryCountVo(
    val F: Int,
    val I: Int,
    val V: Int,
    val H: Int
)

data class FestivalStateCountVo(
    val Y: Int,
    val W: Int,
    val N: Int
)

data class FestivalRegionCountVo(
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

data class FestivalAgeCountVo(
    val GR001: Int,
    val GR013: Int,
    val GR999: Int
)

fun FilterItems.toVo() = FestivalFilterVo(
    category = category.toVo(),
    state = state.toVo(),
    region = region.toVo(),
    age = age.toVo()
)

fun Category.toVo() = FestivalCategoryCountVo(
    F = F,
    I = I,
    V = V,
    H = H
)

fun State.toVo() = FestivalStateCountVo(
    Y = Y,
    W = W,
    N = N
)

fun Region.toVo() = FestivalRegionCountVo(
    SEL = SEL,
    DHK = DHK,
    HND = HND,
    GGI = GGI,
    INC = INC,
    DEJ = DEJ,
    DEG = DEG,
    GWJ = GWJ,
    BUS = BUS,
    ULS = ULS,
    CHC = CHC,
    GKS = GKS,
    JEL = JEL,
    KWO = KWO,
    JEU = JEU
)

fun Age.toVo() = FestivalAgeCountVo(
    GR001 = GR001,
    GR013 = GR013,
    GR999 = GR999
)
