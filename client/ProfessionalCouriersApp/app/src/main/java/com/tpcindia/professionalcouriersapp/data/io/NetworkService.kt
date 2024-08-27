package com.tpcindia.professionalcouriersapp.data.io

import com.tpcindia.professionalcouriersapp.configs.IOConfig
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

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
            val responseBody = response.body?.string() ?: return Result.failure(Exception("Empty response"))
            if (response.isSuccessful) {
                Result.success(responseBody)
            } else {
                Result.failure(Exception(responseBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun sendEmails(branch: String, branchCode: String, username: String) : Result<String> {
        val json = JSONObject()
        json.put("branch", branch)
        json.put("branchCode", branchCode)
        json.put("userName", username)

        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(IOConfig.getEmailSendUrl())
            .post(requestBody)
            .build()

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string() ?: return Result.failure(Exception("Empty response"))
                Result.success(responseBody)
            } else if (response.code == 404) {
                Result.failure(Exception("Branch not found"))
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun getClientDetails(branch: String): Result<String> {
        val json = JSONObject()
        json.put("branch", branch)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(IOConfig.getFirmNameUrl())
            .post(requestBody)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseData = response.body?.string() ?: return Result.failure(Exception("Empty response"))
                Result.success(responseData)
            } else {
                Result.failure(Exception("Failed to fetch firm names"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getConsignmentDetails(branch: String): Result<Map<String, Any>> {
        val json = JSONObject()
        json.put("branch", branch)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(IOConfig.getConsignmentDetailsUrl())
            .post(requestBody)
            .build()

        return try {
            val response = client.newCall(request).execute()
            val responseData = response.body?.string() ?: return Result.failure(Exception("Empty response"))
            if (response.isSuccessful) {
                val jsonResponse = JSONObject(responseData)
                val details = mutableMapOf<String, Any>()
                jsonResponse.keys().forEach {
                    details[it] = jsonResponse[it]
                }
                Result.success(details)
            } else {
                Result.failure(Exception(responseData))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getDestination(pincode: String): Result<Map<String, Any>> {
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
            val responseData = response?.body?.string() ?: return Result.failure(Exception("Empty response"))
            if (response.isSuccessful) {
                val jsonResponse = JSONObject(responseData)
                val details = mutableMapOf<String, Any>()
                jsonResponse.keys().forEach {
                    details[it] = jsonResponse[it]
                }
                Result.success(details)
            } else {
                Result.failure(Exception(responseData))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun sendCreditBookingData(data: String) : Result<String> {
        val requestBody = data.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(IOConfig.getSaveDataUrl())
            .post(requestBody)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string() ?: return Result.failure(Exception("Empty response"))
                Result.success(responseBody)
            } else {
                val responseBody = response.body?.string() ?: return Result.failure(Exception("Empty response"))
                Result.failure(Exception(responseBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun fetchCreditBookingData(branch: String): Result<List<CreditBookingData>> {
        val json = JSONObject()
        json.put("branch", branch)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(IOConfig.getCBDataFetchUrl())
            .post(requestBody)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseData = response.body?.string() ?: return Result.failure(Exception("Empty response"))

                val listType: Type? = object : TypeToken<List<CreditBookingData>>() {}.type
                val bookingDataList: List<CreditBookingData> = Gson().fromJson(responseData, listType)

                Result.success(bookingDataList)
            } else {
                Result.failure(Exception("Failed to fetch credit booking data"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}