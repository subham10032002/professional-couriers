package com.tpcindia.professionalcouriersapp.data.repository

import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.response.DestinationDetails
import java.io.IOException

class CreditBookingRepository(private val networkService: NetworkService) {

    fun getDestination(pincode: String): Result<List<DestinationDetails>> {
        return try {
            val result = networkService.getDestination(pincode)
            if (result.isSuccess) {
                val firmNames = result.getOrThrow().map { DestinationDetails(it) }
                Result.success(firmNames)
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Failed to fetch firm names"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }


}