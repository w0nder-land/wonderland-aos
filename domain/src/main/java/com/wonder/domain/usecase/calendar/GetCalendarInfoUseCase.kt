package com.wonder.domain.usecase.calendar

import com.wonder.domain.model.calendar.CalendarInfo
import com.wonder.domain.repository.CalendarRepository
import com.wonder.domain.usecase.UseCase
import javax.inject.Inject

class GetCalendarInfoUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) : UseCase<String, CalendarInfo>() {

    override suspend fun execute(parameters: String): CalendarInfo =
        calendarRepository.getCalendarInfo(parameters)
}
