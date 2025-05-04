package com.amir.caturday.domain.usecase

import com.amir.caturday.data.repo.SettingsRepository
import com.amir.caturday.domain.model.Theme

class SetThemeUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(theme: Theme) {
        return settingsRepository.setTheme(theme)
    }
}