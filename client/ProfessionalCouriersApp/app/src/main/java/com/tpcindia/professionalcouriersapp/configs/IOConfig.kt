package com.tpcindia.professionalcouriersapp.configs

import com.tpcindia.professionalcouriersapp.BuildConfig

object IOConfig {

    private const val PROTOCOL = BuildConfig.PROTOCOL
    private const val HOST = BuildConfig.HOST
    private const val PORT = BuildConfig.PORT
    private const val ROOT_PATH = BuildConfig.ROOT_PATH

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