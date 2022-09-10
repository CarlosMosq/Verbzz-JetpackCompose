package com.company.verbzz_app.di

import com.company.verbzz_app.network.LanguagesApi
import com.company.verbzz_app.utils.Constants.BASE_URL
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLanguagesApi() : LanguagesApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LanguagesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFirebaseRepository() = FirebaseDatabase
        .getInstance()
        .getReference("Languages")





}