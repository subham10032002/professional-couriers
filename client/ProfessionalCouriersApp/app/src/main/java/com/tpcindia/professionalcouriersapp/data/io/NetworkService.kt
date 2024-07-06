package com.tpcindia.professionalcouriersapp.data.io

import com.tpcindia.professionalcouriersapp.configs.IOConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

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
}