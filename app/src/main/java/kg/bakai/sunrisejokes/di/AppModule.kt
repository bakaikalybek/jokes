package kg.bakai.sunrisejokes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import kg.bakai.sunrisejokes.BuildConfig
import kg.bakai.sunrisejokes.data.repository.JokesRepository
import kg.bakai.sunrisejokes.data.repository.JokesRepositoryImplementation
import kg.bakai.sunrisejokes.data.source.JokesDataSource
import kg.bakai.sunrisejokes.data.source.JokesDataSourceImplementation
import kg.bakai.sunrisejokes.framework.Api
import kg.bakai.sunrisejokes.usecase.GetJokesUseCase
import kg.bakai.sunrisejokes.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideJokesRepository(api: Api): JokesRepository {
        return JokesRepositoryImplementation(api)
    }

    @Provides
    fun provideJokesDataSource(repository: JokesRepository): JokesDataSource {
        return JokesDataSourceImplementation(repository)
    }

    @Provides
    fun provideGetJokesUseCase(dataSource: JokesDataSource): GetJokesUseCase {
        return GetJokesUseCase(dataSource)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
        }

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Api {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(Api::class.java)
    }
}