package com.amir.caturday.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.amir.caturday.ApplicationClass
import com.amir.caturday.BuildConfig
import com.amir.caturday.data.repo.SettingsRepository
import com.amir.caturday.data.repo.SettingsRepositoryImpl
import com.amir.caturday.domain.usecase.GetThemeUseCase
import com.amir.caturday.domain.usecase.SetThemeUseCase
import com.amir.caturday.util.Constant
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(
    name = BuildConfig.APPLICATION_ID
)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplicationClass(@ApplicationContext app: Context): ApplicationClass =
        app as ApplicationClass

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.dataStore
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.CON_BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(
        dataStore: DataStore<Preferences>,
    ): SettingsRepository {
        return SettingsRepositoryImpl(
            dataStore = dataStore,
        )
    }

    @Provides
    fun provideGetThemeUseCase(
        settingsRepository: SettingsRepository
    ): GetThemeUseCase {
        return GetThemeUseCase(
            settingsRepository = settingsRepository
        )
    }

    @Provides
    fun provideSetThemeUseCase(
        settingsRepository: SettingsRepository
    ): SetThemeUseCase {
        return SetThemeUseCase(
            settingsRepository = settingsRepository
        )
    }
}
