package com.tpcindia.professionalcouriersapp.data.repository

import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.response.DestinationDetails
import org.json.JSONArray
import java.io.IOException

class CreditBookingRepository(private val networkService: NetworkService) {

    fun getDestination(pincode: String): Result<List<DestinationDetails>> {
        return try {
            val result = networkService.getDestination(pincode)
            if (result.isSuccess) {
                val destinations = result.getOrThrow()
                val jsonArray = JSONArray(destinations)
                Result.success(parseDestinationDetails(jsonArray))
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Failed to fetch destinations"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    private fun parseDestinationDetails(jsonArray: JSONArray) : List<DestinationDetails> {
        val finalDestinationDetails = mutableListOf<DestinationDetails>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val cities = jsonObject.getString("city")
            val destCode = jsonObject.getString("destCode")
            val destn = jsonObject.getString("destn")
            val areaCode = jsonObject.getString("areaCode")
            val hub = jsonObject.getString("hub")
            val state = jsonObject.getString("state")
            val pdfCity = jsonObject.getString("pdfCity")

            val details = DestinationDetails(
                city = cities ?: "",
                destCode = destCode,
                destn = destn,
                areaCode = areaCode,
                hub = hub,
                state = state,
                pdfCity = pdfCity
            )
            finalDestinationDetails.add(details)
        }
        return finalDestinationDetails
    }
}