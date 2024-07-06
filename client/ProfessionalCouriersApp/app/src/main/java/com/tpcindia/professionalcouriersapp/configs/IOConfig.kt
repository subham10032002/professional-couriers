package com.tpcindia.professionalcouriersapp.configs

object IOConfig {

    private const val PROTOCOL = "http"
    private const val HOST = "10.0.2.2"
    private const val PORT = "8080"

    private var BASE_URL = String.format("%s://%s:%s", PROTOCOL, HOST, PORT)

    object Endpoints {
        const val LOGIN = "/api/users/authenticate"
    }

    fun getBaseUrl(): String {
        return BASE_URL
    }

    fun getLoginUrl(): String {
        return String.format("%s%s", BASE_URL, Endpoints.LOGIN)
    }

}