package com.karthee.interviewsample.data.repository

import com.karthee.interviewsample.data.ApiResponse
import com.karthee.interviewsample.data.SectionApi
import com.karthee.interviewsample.data.models.toSectionObj
import com.karthee.interviewsample.domain.models.SectionObj
import com.karthee.interviewsample.domain.repo.SectionRepo
import java.net.UnknownHostException
import javax.inject.Inject

class SectionRepoImpl @Inject constructor(val sectionApi: SectionApi): SectionRepo {

    override suspend fun fetchAllSections(): ApiResponse<List<SectionObj>> {
        return try {
            val response = sectionApi.getAllSections()
            if (response.isSuccessful) ApiResponse.SUCCESS(
                response.body()?.map { it.toSectionObj() })
            else ApiResponse.ERROR(response.message(), response.code())
        } catch(e:UnknownHostException) {
            return  ApiResponse.ERROR("No Internet. Check your connection and try again ", -1001)
        }
    }

//    override suspend fun fetchAllSections(): ApiResponse<List<SectionObj>> {
//
//        return ApiResponse.SUCCESS(Constants.getLocalSection())
//    }
}