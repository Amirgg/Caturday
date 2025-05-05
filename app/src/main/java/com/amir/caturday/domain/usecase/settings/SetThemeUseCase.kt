package com.amir.caturday.domain.usecase.settings

import com.amir.caturday.data.repo.SettingsRepository
import com.amir.caturday.domain.model.Theme

class SetThemeUseCase(
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(theme: Theme) = settingsRepository.setTheme(theme)
}
