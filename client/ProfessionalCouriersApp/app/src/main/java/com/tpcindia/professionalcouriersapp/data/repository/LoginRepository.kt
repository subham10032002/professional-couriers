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
                    val userCode = responseJson.getString("userCode")
                    Result.success(User(firstName, lastName, branchCode, branch, userCode))
                } else {
                    Result.failure(Exception(response.exceptionOrNull()?.message))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun saveUser(context: Context, user: User) {
        withContext(Dispatchers.IO) {
            SharedPreferencesManager.saveUser(context, user)
        }
    }

    suspend fun getUser(context: Context): User? {
        return withContext(Dispatchers.IO) {
            SharedPreferencesManager.getUser(context)
        }
    }

    suspend fun clearUser(context: Context) {
        withContext(Dispatchers.IO) {
            SharedPreferencesManager.clearUser(context)
        }
    }
}
