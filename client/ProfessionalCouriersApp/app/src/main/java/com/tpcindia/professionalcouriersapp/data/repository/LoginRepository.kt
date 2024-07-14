package com.tpcindia.professionalcouriersapp.data.repository

import android.content.Context
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.request.LoginRequest
import com.tpcindia.professionalcouriersapp.data.model.response.User
import com.tpcindia.professionalcouriersapp.data.utils.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class LoginRepository(private val networkService: NetworkService) {

    suspend fun login(loginRequest: LoginRequest): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkService.requestLogin(loginRequest.username, loginRequest.password)
                if (response.isSuccess) {
                    val responseJson = JSONObject(response.getOrNull() ?: "")
                    val firstName = responseJson.getString("firstName")
                    val lastName = responseJson.getString("lastName")
                    val branchCode = responseJson.getString("branchCode")
                    val branch = responseJson.getString("branch")
                    Result.success(User(firstName, lastName, branchCode, branch))
                } else {
                    Result.failure(Exception("Authentication failed"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    fun saveUser(context: Context, user: User) {
        SharedPreferencesManager.saveUser(context, user)
    }

    fun getUser(context: Context): User? {
        return SharedPreferencesManager.getUser(context)
    }
}
