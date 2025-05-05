package com.amir.caturday

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestDataStore : DataStore<Preferences> {
    private val state = MutableStateFlow(emptyPreferences())
    override val data: Flow<Preferences> get() = state

    override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences {
        val newPrefs = transform(state.value)
        state.value = newPrefs
        return newPrefs
    }
}
