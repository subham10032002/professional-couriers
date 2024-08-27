package com.tpcindia.professionalcouriersapp.data.model

import com.tpcindia.professionalcouriersapp.data.model.response.ClientDetails

data class HomeScreenData(
    val clientDetails: List<ClientDetails> = emptyList(),
    val currentDate: String ="",
    val branch: String = "",
    val username: String = "",
    val userCode: String = ""
)

