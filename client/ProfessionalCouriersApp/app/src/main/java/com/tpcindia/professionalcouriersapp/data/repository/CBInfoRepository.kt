package com.tpcindia.professionalcouriersapp.data.repository

import com.google.gson.Gson
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CBInfoData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import java.io.IOException

class CBInfoRepository(private val networkService: NetworkService) {

    fun submitCreditBookingDetails(
        creditBookingData: CreditBookingData,
        cbDimensionData: CBDimensionData,
        cbInfoData: CBInfoData): Result<String> {
        return try {
            val result = networkService.sendCreditBookingData(mergeDataToJson(
                creditBookingData,
                cbDimensionData,
                cbInfoData
            ))
            if (result.isSuccess) {
                Result.success(result.getOrThrow())
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Failed to save the data in database"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    private fun mergeDataToJson(
        creditBookingData: CreditBookingData,
        cbDimensionData: CBDimensionData,
        cbInfoData: CBInfoData
    ): String {
        // Create a map to merge all data fields
        val mergedMap = mutableMapOf<String, Any?>()

        // Add CreditBookingData fields
        mergedMap["currentDate"] = creditBookingData.currentDate
        mergedMap["consignmentNumber"] = creditBookingData.consignmentNumber
        mergedMap["balanceStock"] = creditBookingData.balanceStock
        mergedMap["clientName"] = creditBookingData.clientName
        mergedMap["bookingDate"] = creditBookingData.bookingDate
        mergedMap["pincode"] = creditBookingData.pincode
        mergedMap["destination"] = creditBookingData.destination
        mergedMap["consigneeType"] = creditBookingData.consigneeType
        mergedMap["mode"] = creditBookingData.mode
        mergedMap["consigneeName"] = creditBookingData.consigneeName
        mergedMap["noOfPsc"] = creditBookingData.noOfPsc
        mergedMap["weight"] = creditBookingData.weight

        // Add CBDimensionData fields
        mergedMap["unit"] = cbDimensionData.unit
        mergedMap["length"] = cbDimensionData.length
        mergedMap["width"] = cbDimensionData.width
        mergedMap["height"] = cbDimensionData.height

        // Add CBInfoData fields
        mergedMap["invoiceNumber"] = cbInfoData.invoiceNumber
        mergedMap["product"] = cbInfoData.product
        mergedMap["declaredValue"] = cbInfoData.declaredValue
        mergedMap["ewayBill"] = cbInfoData.ewayBill

        // Convert mergedMap to JSON string using Gson
        return Gson().toJson(mergedMap)
    }
}