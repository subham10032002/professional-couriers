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
import com.tpcindia.professionalcouriersapp.data.model.response.MasterAddressDetails
import com.tpcindia.professionalcouriersapp.data.utils.PdfGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException

class CBDataSubmissionRepository(private val networkService: NetworkService) {

    suspend fun submitCreditBookingDetails(
        creditBookingData: CreditBookingData,
        cbDimensionData: CBDimensionData,
        cbInfoData: CBInfoData): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
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
    }

    suspend fun getConsignmentDetails(branch: String, custCode: String): Result<ConsignmentDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val result = networkService.getConsignmentDetails(branch, custCode)
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

    suspend fun getMasterAddressDetails(code: String) : Result<MasterAddressDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val result = networkService.getMasterAddressDetails(code)
                if (result.isSuccess) {
                    val addressDetails = result.getOrThrow()
                    val jsonObject = JSONObject(addressDetails)
                    Result.success(parseMasterAddressDetails(jsonObject))
                } else {
                    Result.failure(
                        result.exceptionOrNull()
                            ?: Exception("Failed to fetch master address details")
                    )
                }
            } catch (e: IOException) {
                Result.failure(e)
            }
        }
    }

    private fun parseMasterAddressDetails(jsonObject: JSONObject) : MasterAddressDetails {
        val code = jsonObject.getString("code")
        val address = jsonObject.getString("address")
        val contactNo = jsonObject.getString("contactNo")
        val gstNo = jsonObject.getString("gstNo")
        val subBranchCode = jsonObject.getString("subBranchCode")

        val masterAddressDetails = MasterAddressDetails(
            code = code,
            address = address,
            contactNo = contactNo,
            gstNo = gstNo,
            subBranchCode = subBranchCode
        )

        return masterAddressDetails
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

    suspend fun savePdf(pdfData: ByteArray, fileName: String, uniqueUser: String, pdfDao: PdfDao) : Boolean {
        return try {
            withContext(Dispatchers.IO) {
                pdfDao.insertPdf(PdfEntity(fileName = fileName, pdfData = pdfData, uniqueUser = uniqueUser))
            }
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
        mergedMap["destination"] = creditBookingData.destDetails.pdfCity
        mergedMap["consigneeType"] = creditBookingData.consigneeType
        mergedMap["mode"] = creditBookingData.mode
        mergedMap["consigneeName"] = creditBookingData.consigneeName
        mergedMap["noOfPsc"] = creditBookingData.noOfPsc
        mergedMap["weight"] = creditBookingData.weight
        mergedMap["photoOfAddress"] = creditBookingData.photoOfAddress
        mergedMap["username"] = creditBookingData.username
        mergedMap["userCode"] = creditBookingData.userCode
        mergedMap["latitude"] = creditBookingData.latitude
        mergedMap["longitude"] = creditBookingData.longitude
        mergedMap["pdfAddress"] = creditBookingData.pdfAddress
        mergedMap["destCode"] = creditBookingData.destDetails.destCode

        // Add CBDimensionData fields
        mergedMap["unit"] = cbDimensionData.unit
        mergedMap["length"] = cbDimensionData.length
        mergedMap["width"] = cbDimensionData.width
        mergedMap["height"] = cbDimensionData.height

        // Add CBInfoData fields
        mergedMap["invoiceNumber"] = cbInfoData.invoiceNumber
        mergedMap["product"] = cbInfoData.product
        mergedMap["declaredValue"] = cbInfoData.invoiceValue
        mergedMap["ewayBill"] = cbInfoData.ewayBill

        mergedMap["branch"] = creditBookingData.branch

        // Convert mergedMap to JSON string using Gson
        return Gson().toJson(mergedMap)
    }
}