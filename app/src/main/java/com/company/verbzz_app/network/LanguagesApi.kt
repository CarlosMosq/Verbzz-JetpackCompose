package com.company.verbzz_app.network

import com.company.verbzz_app.model.englishModel.EnglishModel
import com.company.verbzz_app.model.frenchModel.FrenchModel
import com.company.verbzz_app.utils.Constants.ENGLISH_URL
import com.company.verbzz_app.utils.Constants.FRENCH_URL
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface LanguagesApi {

    @GET(ENGLISH_URL)
    suspend fun getEnglishVerbs() : EnglishModel

    @GET(FRENCH_URL)
    suspend fun getFrenchVerbs() : FrenchModel

}