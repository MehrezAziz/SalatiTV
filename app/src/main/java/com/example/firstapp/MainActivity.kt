package com.example.firstapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.lifecycleScope
import com.example.firstapp.network.ApiClient
import com.example.firstapp.network.DateInfo
import com.example.firstapp.network.Timings
import com.example.firstapp.ui.screens.MainScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.reflect.Modifier
import java.net.URL
import java.util.Locale
import androidx.activity.ComponentActivity.LOCATION_SERVICE as LOCATION_SERVICE1

class MainActivity : ComponentActivity() {

    private var timings by mutableStateOf<Timings?>(null)
    private var dateInfo by mutableStateOf<DateInfo?>(null)
    private var currentLocation: String = "Fetching location..."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            // Fetch location first
            fetchLocationForTV()

            // Once location is fetched, fetch prayer times
            fetchPrayerTimes("24-11-2024", currentLocation, 18)

            // Set content after both are done
            setContent {
                MainScreen(timings = timings, dateInfo = dateInfo, location = currentLocation)
            }
        }
        setContent {

            MainScreen(timings = timings, dateInfo = dateInfo, location= currentLocation)
        }
    }

    private fun isTVDevice(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
    }

    private suspend fun fetchPrayerTimes(date: String, address: String, method: Int) {
        withContext(Dispatchers.IO) {
            try {
                val response = ApiClient.apiService.getPrayerTimes(date, address, method)
                if (response.code == 200) {
                    // Extract prayer times and date info
                    val fetchedTimings = response.data.timings
                    val fetchedDateInfo = response.data.date

                    // Update the state with fetched data
                    timings = fetchedTimings
                    dateInfo = fetchedDateInfo
                } else {
                    Log.e("PrayerTimes", "Error: ${response.status}")
                }
            } catch (e: Exception) {
                Log.e("PrayerTimes", "Exception: ${e.message}")
            }
        }
    }
    @SuppressLint("NewApi")
    private fun fetchCurrentLocation() {
        // Use `getSystemService` with correct context and service class
        val locationManager = getSystemService(LocationManager::class.java)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location: Location? = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location != null) {
                currentLocation = "${location.latitude}, ${location.longitude}"
            } else {
                currentLocation = "Unable to get location"
            }
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }


    private suspend fun fetchLocationForTV() {
        withContext(Dispatchers.IO) {
            try {
                val response = URL("https://ipinfo.io/json").readText()
                val json = JSONObject(response)
                val city = json.getString("city")
                val country = json.getString("country")
                currentLocation = "$city, $country"
            } catch (e: Exception) {
                currentLocation = "Location unavailable"
            }
        }
    }


}


