package com.example.wiseforecast

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wiseforecast.ViewModels.CurrentForecastViewModel
import com.example.wiseforecast.ui.theme.WiseForecastTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WiseForecastTheme {
                checkLocationServices()
            }
        }
    }
}

@Composable
private fun checkLocationServices() {
    val locationManager =
        LocalContext.current.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        displayRationale()
    } else {
        Toast.makeText(LocalContext.current, "Service is enabled", Toast.LENGTH_LONG).show()
        askForLocationPermissions()
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun askForLocationPermissions() {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    if (locationPermissionsState.allPermissionsGranted) {
        Text("Fine location")
        //TODO FINE LOCATION
    } else {
        Column {
            val allPermissionsRevoked =
                locationPermissionsState.permissions.size ==
                        locationPermissionsState.revokedPermissions.size

            val textToShow = if (!allPermissionsRevoked) {
                // If not all the permissions are revoked, it's because the user accepted the COARSE
                // location permission, but not the FINE one.
                "Coarse, ask for fine location"
                //TODO COARSE ASK FOR FINE
            } else if (locationPermissionsState.shouldShowRationale) {
                // Both location permissions have been denied
                "By giving location permissions, the application will display accurate forecast for the city you are currently in"
            } else {
                // First time the user sees this feature or the user doesn't want to be asked again
                "In order to display forecast, grant location permissions to the application. Otherwise you can type down the city you want to see forecast for"
            }

            val buttonText = if (!allPermissionsRevoked) {
                "Allow precise location"
            } else {
                "Request permissions"
            }

            Text(text = textToShow)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                Text(buttonText)
            }
        }
    }
}

@Composable
private fun displayRationale() {
    val context = LocalContext.current
    var shouldAskForCity by remember { mutableStateOf(false) }

    if (!shouldAskForCity) {
        AlertDialog(
            title = {
                Text(text = "Application need data about your location")
            },
            text = {
                Text(text = "In order to get proper forecast you can either turn on location service, or simply enter the city that you want see forecast for")
            },
            onDismissRequest = {},
            confirmButton = {
                Button(onClick = {
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }) {
                    Text(text = "Turn on location")
                }
            },
            dismissButton = {
                Button(onClick = { shouldAskForCity = true }) {

                    Text(text = "Enter city instead")
                }
            }
        )
    } else {
        getCityNameFromUser()
    }
}

@Composable
fun getCityNameFromUser(
    currentForecastViewModel: CurrentForecastViewModel = viewModel()
) {
    val name by currentForecastViewModel.cityName.observeAsState(initial = "")

    getCityNameScreen(cityName = name, onCityNameChange = {
        currentForecastViewModel.onCityNameChange(it)
    })
}

@Composable
private fun getCityNameScreen(cityName: String, onCityNameChange: (String) -> Unit) {
    Column {

        OutlinedTextField(
            value = cityName,
            onValueChange = onCityNameChange,
            singleLine = true
        )

        Button(onClick = { println(cityName) }) {
        }
    }
}