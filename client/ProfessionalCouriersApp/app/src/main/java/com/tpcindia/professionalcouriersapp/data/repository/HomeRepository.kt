package com.tpcindia.professionalcouriersapp.data.repository

import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.response.ClientDetails
import com.tpcindia.professionalcouriersapp.data.model.response.ConsignmentDetails
import java.io.IOException

class HomeRepository(private val networkService: NetworkService) {

    fun getFirmNames(branch: String): Result<List<ClientDetails>> {
        return try {
            val result = networkService.getFirmNames(branch)
            if (result.isSuccess) {
                val firmNames = result.getOrThrow().map { ClientDetails(it) }
                Result.success(firmNames)
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Failed to fetch firm names"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    fun sendEmails(branch: String, branchCode: String, userName: String) : Result<String> {
        return try {
            val result = networkService.sendEmails(
                branch = branch,
                branchCode = branchCode,
                username = userName
            )
            return result
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
