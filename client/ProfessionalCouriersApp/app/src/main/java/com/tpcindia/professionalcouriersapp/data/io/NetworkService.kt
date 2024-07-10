package com.tpcindia.professionalcouriersapp.data.io

import com.tpcindia.professionalcouriersapp.configs.IOConfig
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class NetworkService {

    private val client = OkHttpClient()

    fun requestLogin(username: String, password: String) : Result<String> {
        val json = JSONObject()
        json.put("loginId", username)
        json.put("password", password)

        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(IOConfig.getLoginUrl())
            .post(requestBody)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string() ?: return Result.failure(Exception("Empty response"))
                Result.success(responseBody)
            } else {
                Result.failure(Exception("Authentication failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getFirmNames(branchCode: String): Result<List<String>> {
        val json = JSONObject()
        json.put("branchCode", branchCode)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(IOConfig.getFirmNameUrl())
            .post(requestBody)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseData = response.body?.string() ?: return Result.failure(Exception("Empty response"))
                val jsonArray = JSONArray(responseData)
                val firmNames = mutableListOf<String>()
                for (i in 0 until jsonArray.length()) {
                    val element = jsonArray.get(i)
                    if (element is String) {
                        firmNames.add(element)
                    }
                }
                Result.success(firmNames)
            } else {
                Result.failure(Exception("Failed to fetch firm names"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getConsignmentDetails(firmName: String): Result<Map<String, Any>> {
        val json = JSONObject()
        json.put("firmName", firmName)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(IOConfig.getConsignmentDetailsUrl())
            .post(requestBody)
            .build()

        return try {
//            val response = client.newCall(request).execute()
//            if (response.isSuccessful) {
//                val responseData = response.body?.string() ?: return Result.failure(Exception("Empty response"))
//                val jsonResponse = JSONObject(responseData)
//                val details = mutableMapOf<String, Any>()
//                jsonResponse.keys().forEach {
//                    details[it] = jsonResponse[it]
//                }
//                Result.success(details)
//            } else {
//                Result.failure(Exception("Failed to fetch consignment details"))
//            }
            val details = mutableMapOf<String, Any>()
            details.put("startNo", "123")
            details.put("accCode", "ABC")
            details.put("consignmentNo", "12345")
            details.put("balanceStock", "100")
            Result.success(details)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getDestination(pincode: String): Result<List<String>> {
        val baseUrl = IOConfig.getDestinationUrl()
        val url = baseUrl.toHttpUrlOrNull()?.newBuilder()
            ?.addQueryParameter("pinCode", pincode)
            ?.build()

        val request = url?.let {
            Request.Builder()
                .url(it)
                .build()
        }


        return try {
            val response = request?.let { client.newCall(it).execute() }
            if (response?.isSuccessful == true) {
                val responseData = response.body?.string() ?: return Result.failure(Exception("Empty response"))
                val jsonArray = JSONArray(responseData)
                val destinations = mutableListOf<String>()
                for (i in 0 until jsonArray.length()) {
                    val element = jsonArray.get(i)
                    if (element is String) {
                        destinations.add(element)
                    }
                }
                Result.success(destinations)
            } else {
                Result.failure(Exception("Failed to fetch destinations"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}