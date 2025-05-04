package com.amir.caturday.domain.usecase

import com.amir.caturday.data.repo.SettingsRepository
import com.amir.caturday.domain.model.Theme
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): Flow<Theme> {
        return settingsRepository.getTheme()
    }
}