package com.karthee.interviewsample.data

import com.karthee.interviewsample.data.models.Section
import retrofit2.Response
import retrofit2.http.GET

interface SectionApi {
    @GET("/b/5BEJ")
    suspend fun getAllSections():Response<List<Section>>
}