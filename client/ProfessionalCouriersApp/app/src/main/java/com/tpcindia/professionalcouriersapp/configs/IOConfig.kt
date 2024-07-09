package com.tpcindia.professionalcouriersapp.configs

object IOConfig {

    private const val PROTOCOL = "http"
    private const val HOST = "10.0.2.2"
    private const val PORT = "8080"

    private var BASE_URL = String.format("%s://%s:%s", PROTOCOL, HOST, PORT)

    object Endpoints {
        const val LOGIN = "/api/users/authenticate"
        const val CONSIGNMENT_DETAILS = "/api/consignment"
        const val FIRM_NAMES = "/api/accountsCustomer/firmNames"
        const val DESTINATION = "/api/destination/cities"
    }

    fun getBaseUrl(): String {
        return BASE_URL
    }

    fun getLoginUrl(): String {
        return String.format("%s%s", BASE_URL, Endpoints.LOGIN)
    }

    fun getConsignmentDetailsUrl(): String {
        return String.format("%s%s", BASE_URL, Endpoints.CONSIGNMENT_DETAILS)
    }

    fun getFirmNameUrl(): String {
        return String.format("%s%s", BASE_URL, Endpoints.FIRM_NAMES)
    }

    fun getDestinationUrl(): String {
        return String.format("%s%s", BASE_URL, Endpoints.DESTINATION)
    }

}