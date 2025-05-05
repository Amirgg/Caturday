package com.amir.caturday.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.amir.caturday.domain.model.Theme
import com.amir.caturday.util.PreferenceKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingsRepositoryImpl
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : SettingsRepository {
        override fun getTheme(): Flow<Theme> =
            dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }.map {
                    Theme.valueOf(it[PreferenceKeys.THEME] ?: Theme.DEFAULT_THEME.value)
                }

        override suspend fun setTheme(theme: Theme) {
            dataStore.edit {
                it[PreferenceKeys.THEME] = theme.value
            }
        }
    }
