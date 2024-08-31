package com.tpcindia.professionalcouriersapp.configs

object IOConfig {

    private const val PROTOCOL = "http"
//    private const val HOST = "10.0.2.2"
    private const val HOST = "106.51.61.94"
//    private const val PORT = "8080"
    private const val PORT = "8083"
    private const val ROOT_PATH = "professional-couriers"

    private var BASE_URL = String.format("%s://%s:%s/%s", PROTOCOL, HOST, PORT, ROOT_PATH)

    object Endpoints {
        const val LOGIN = "/api/users/authenticate"
        const val CONSIGNMENT_DETAILS = "/api/consignment"
        const val FIRM_DETAILS = "/api/accountsCustomer/firmDetails"
        const val DESTINATION = "/api/destination/cities"
        const val SAVE_DATA = "/api/credit-booking-data/save"
        const val EMAIL_SEND = "/api/email/send"
        const val MASTER_ADDRESS_DETAILS = "/api/companyMaster/addressDetails"
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

    fun getFirmDetailsUrl(): String {
        return String.format("%s%s", BASE_URL, Endpoints.FIRM_DETAILS)
    }

    fun getDestinationUrl(): String {
        return String.format("%s%s", BASE_URL, Endpoints.DESTINATION)
    }

    fun getSaveDataUrl(): String {
        return String.format("%s%s", BASE_URL, Endpoints.SAVE_DATA)
    }

    fun getEmailSendUrl() : String {
        return String.format("%s%s", BASE_URL, Endpoints.EMAIL_SEND)
    }

    fun getMasterAddressUrl() : String {
        return String.format("%s%s", BASE_URL, Endpoints.MASTER_ADDRESS_DETAILS)
    }
}