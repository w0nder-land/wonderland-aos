package com.wonder.domain.usecase.calendar

import com.wonder.domain.model.calendar.CalendarInfo
import com.wonder.domain.repository.CalendarRepository
import com.wonder.domain.usecase.UseCase
import javax.inject.Inject

class SaveCalendarInfoUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) : UseCase<SaveCalendarInfoParam, Unit>() {

    override suspend fun execute(parameters: SaveCalendarInfoParam) {
        calendarRepository.saveCalendarInfo(
            yearMonth = parameters.yearMonth,
            calendarInfo = parameters.calendarInfo
        )
    }
}

data class SaveCalendarInfoParam(
    val yearMonth: String,
    val calendarInfo: CalendarInfo
)
