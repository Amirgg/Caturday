package com.amir.caturday.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.amir.caturday.ApplicationClass
import com.amir.caturday.BuildConfig
import com.amir.caturday.data.db.BreedDatabase
import com.amir.caturday.data.remote.BreedsApi
import com.amir.caturday.data.repo.BreedsRepository
import com.amir.caturday.data.repo.BreedsRepositoryImpl
import com.amir.caturday.data.repo.SettingsRepository
import com.amir.caturday.data.repo.SettingsRepositoryImpl
import com.amir.caturday.domain.usecase.breed.GetBreedByIdUseCase
import com.amir.caturday.domain.usecase.breed.GetBreedsUseCase
import com.amir.caturday.domain.usecase.breed.InvalidateCacheUseCase
import com.amir.caturday.domain.usecase.breed.PaginateBreedsUseCase
import com.amir.caturday.domain.usecase.breed.ToggleFavoriteUseCase
import com.amir.caturday.domain.usecase.settings.GetThemeUseCase
import com.amir.caturday.domain.usecase.settings.SetThemeUseCase
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
    name = BuildConfig.APPLICATION_ID,
)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplicationClass(
        @ApplicationContext app: Context,
    ): ApplicationClass = app as ApplicationClass

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext applicationContext: Context,
    ): DataStore<Preferences> = applicationContext.dataStore

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(Constant.CON_BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): BreedsApi = retrofit.create(BreedsApi::class.java)

    @Singleton
    @Provides
    fun provideSettingsRepository(dataStore: DataStore<Preferences>): SettingsRepository =
        SettingsRepositoryImpl(
            dataStore = dataStore,
        )

    @Provides
    @Singleton
    fun provideBreedDatabase(app: Application): BreedDatabase =
        Room
            .databaseBuilder(
                app,
                BreedDatabase::class.java,
                BreedDatabase.DATABASE_NAME,
            ).build()

    @Provides
    @Singleton
    fun provideBreedRepository(
        breedsApi: BreedsApi,
        breedDatabase: BreedDatabase,
        dataStore: DataStore<Preferences>,
    ): BreedsRepository = BreedsRepositoryImpl(breedsApi, breedDatabase.breedDao, dataStore)

    @Provides
    fun provideGetThemeUseCase(settingsRepository: SettingsRepository): GetThemeUseCase =
        GetThemeUseCase(
            settingsRepository = settingsRepository,
        )

    @Provides
    fun provideSetThemeUseCase(settingsRepository: SettingsRepository): SetThemeUseCase =
        SetThemeUseCase(
            settingsRepository = settingsRepository,
        )

    @Provides
    fun provideGetBreedByIdUseCase(breedsRepository: BreedsRepository): GetBreedByIdUseCase =
        GetBreedByIdUseCase(
            breedsRepository = breedsRepository,
        )

    @Provides
    fun provideGetBreedsUseCase(breedsRepository: BreedsRepository): GetBreedsUseCase =
        GetBreedsUseCase(
            breedsRepository = breedsRepository,
        )

    @Provides
    fun provideInvalidateCacheUseCase(breedsRepository: BreedsRepository): InvalidateCacheUseCase =
        InvalidateCacheUseCase(
            breedsRepository = breedsRepository,
        )

    @Provides
    fun providePaginateBreedsUseCase(breedsRepository: BreedsRepository): PaginateBreedsUseCase =
        PaginateBreedsUseCase(
            breedsRepository = breedsRepository,
        )

    @Provides
    fun provideToggleFavoriteUseCase(breedsRepository: BreedsRepository): ToggleFavoriteUseCase =
        ToggleFavoriteUseCase(
            breedsRepository = breedsRepository,
        )
}
