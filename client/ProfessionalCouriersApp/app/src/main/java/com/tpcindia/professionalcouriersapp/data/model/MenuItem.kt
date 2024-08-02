package com.tpcindia.professionalcouriersapp.data.model

data class MenuItem(
    val text: String,
    val onClick: () -> Unit
)