package com.tpcindia.professionalcouriersapp.data.repository

import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.response.ClientDetails
import com.tpcindia.professionalcouriersapp.data.model.response.ConsignmentDetails
import java.io.IOException

class HomeRepository(private val networkService: NetworkService) {

    fun getFirmNames(branchCode: String): Result<List<ClientDetails>> {
        return try {
            val result = networkService.getFirmNames(branchCode)
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

    fun getConsignmentDetails(firmName: String): Result<ConsignmentDetails> {
        return try {
            val result = networkService.getConsignmentDetails(firmName)
            if (result.isSuccess) {
                val consignmentDetails = parseConsignmentDetails(result.getOrThrow())
                Result.success(consignmentDetails)
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Failed to fetch consignment details"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    private fun parseConsignmentDetails(detailsMap: Map<String, Any>): ConsignmentDetails {
        return try {
            val startNo = detailsMap["startNo"].toString()
            val accCode = detailsMap["accCode"].toString()
            val consignmentNo = detailsMap["consignmentNo"].toString()
            val balanceStock = detailsMap["balanceStock"].toString()
            ConsignmentDetails(startNo, accCode, consignmentNo, balanceStock)
        } catch (e: Exception) {
            ConsignmentDetails()
        }
    }
}
