package com.tpcindia.professionalcouriersapp.data.repository

import android.util.Base64
import com.tpcindia.professionalcouriersapp.data.db.dao.PdfDao
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import com.tpcindia.professionalcouriersapp.data.model.entity.PdfEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.IOException

class PdfRepository(private val networkService: NetworkService) {

    suspend fun getTopPdfs(branch: String, userCode: String) : Result<List<CreditBookingData>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = networkService.getTopPdfs(branch, userCode)
                if (result.isSuccess) {
                    val pdfDetails = result.getOrThrow()
                    val jsonArray = JSONArray(pdfDetails)
                    Result.success(parseCreditBookingDetails(jsonArray))
                } else {
                    Result.failure(
                        result.exceptionOrNull()
                            ?: Exception("Failed to fetch pdf from server")
                    )
                }
            } catch (e: IOException) {
                Result.failure(e)
            }
        }
    }

    suspend fun getPDFs(uniqueUser: String, pdfDao: PdfDao) : List<PdfEntity> {
        return withContext(Dispatchers.IO) {
            pdfDao.getAllPdfs(uniqueUser)
        }
    }

    suspend fun clearPDFs( pdfDao: PdfDao) {
        withContext(Dispatchers.IO) {
            pdfDao.deleteAllPdfs()
        }
    }

    private fun parseCreditBookingDetails(jsonArray: JSONArray) : List<CreditBookingData> {
        val creditBookingData = mutableListOf<CreditBookingData>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val pdfAddress = jsonObject.getString("pdfAddress")
            val transactionId = jsonObject.getString("transactionId")
            val consignmentNumber = jsonObject.getString("consignmentNumber")
            if (pdfAddress.isNotEmpty()) {
                val decodedBytes = Base64.decode(pdfAddress, Base64.DEFAULT)
                val details = CreditBookingData(
                    transactionId = transactionId,
                    consignmentNumber = consignmentNumber,
                    pdfAddress = decodedBytes,
                )
                creditBookingData.add(details)
            }
        }
        return creditBookingData
    }
}