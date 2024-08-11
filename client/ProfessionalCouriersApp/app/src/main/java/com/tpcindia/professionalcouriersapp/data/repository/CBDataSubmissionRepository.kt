package com.tpcindia.professionalcouriersapp.data.repository

import android.content.Context
import com.google.gson.Gson
import com.tpcindia.professionalcouriersapp.data.db.dao.PdfDao
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CBInfoData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.data.model.entity.PdfEntity
import com.tpcindia.professionalcouriersapp.data.model.response.ConsignmentDetails
import com.tpcindia.professionalcouriersapp.data.utils.PdfGenerator
import java.io.IOException

class CBDataSubmissionRepository(private val networkService: NetworkService) {

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

    fun getConsignmentDetails(branch: String): Result<ConsignmentDetails> {
        return try {
            val result = networkService.getConsignmentDetails(branch)
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

    fun createPdf(context: Context, creditBookingData: CreditBookingData,
                  cbDimensionData: CBDimensionData,
                  cbInfoData: CBInfoData): ByteArray {
        return try {
           val pdgGenerator = PdfGenerator()
            pdgGenerator.createPdf(context, creditBookingData, cbDimensionData, cbInfoData)
        } catch (e: IOException) {
            ByteArray(0)
        }
    }

    suspend fun savePdf(pdfData: ByteArray, fileName: String, branch: String, pdfDao: PdfDao) : Boolean {
        return try {
            pdfDao.insertPdf(PdfEntity(fileName = fileName, pdfData = pdfData, branch = branch))
            true
        } catch (e: Exception) {
            // Handle exception if insertion fails (e.g., database error)
            false
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
        mergedMap["photoOfAddress"] = creditBookingData.photoOfAddress
        mergedMap["username"] = creditBookingData.username
        mergedMap["userCode"] = creditBookingData.userCode

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

        mergedMap["branch"] = creditBookingData.branch

        // Convert mergedMap to JSON string using Gson
        return Gson().toJson(mergedMap)
    }
}