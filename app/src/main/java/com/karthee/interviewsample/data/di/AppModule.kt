package com.karthee.interviewsample.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.karthee.interviewsample.data.Constants
import com.karthee.interviewsample.data.SectionApi
import com.karthee.interviewsample.data.repository.SectionRepoImpl
import com.karthee.interviewsample.domain.GetSectionUsecase
import com.karthee.interviewsample.domain.repo.SectionRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.APP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideSectionApi(retrofit: Retrofit): SectionApi {
        return retrofit.create(SectionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSectionRepo(sectionApi: SectionApi): SectionRepo {
        return SectionRepoImpl(sectionApi)
    }

    @Provides
    @Singleton
    fun provideGetSectionUsecase(sectionRepo: SectionRepo): GetSectionUsecase {
        return GetSectionUsecase(sectionRepo)
    }
}