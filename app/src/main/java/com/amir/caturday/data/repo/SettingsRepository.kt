package com.amir.caturday.data.repo

import com.amir.caturday.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getTheme(): Flow<Theme>
    suspend fun setTheme(theme: Theme)
}