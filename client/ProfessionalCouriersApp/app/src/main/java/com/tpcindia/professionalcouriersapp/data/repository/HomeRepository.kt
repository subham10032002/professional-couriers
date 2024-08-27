package com.tpcindia.professionalcouriersapp.data.repository

import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.response.ClientDetails
import com.tpcindia.professionalcouriersapp.data.model.response.ConsignmentDetails
import org.json.JSONArray
import java.io.IOException

class HomeRepository(private val networkService: NetworkService) {

    fun getClientDetails(branch: String): Result<List<ClientDetails>> {
        return try {
            val result = networkService.getClientDetails(branch)
            if (result.isSuccess) {
                val responseData = result.getOrThrow()
                val clientDetails = mutableListOf<ClientDetails>()

                val jsonArray = JSONArray(responseData)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    val name = jsonObject.getString("firmName")
                    val address = jsonObject.getString("address")
                    val contactNo = jsonObject.getString("contactNo")

                    val details = ClientDetails(
                        firmName = name,
                        clientAddress = address,
                        clientContactNo = contactNo
                    )

                    clientDetails.add(details)
                }
                Result.success(clientDetails)
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
