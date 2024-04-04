package com.example.wiseforecast

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wiseforecast.ViewModels.CurrentForecastViewModel
import com.example.wiseforecast.ui.theme.WiseForecastTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WiseForecastTheme {
                CheckLocationServices()
            }
        }
    }
}

@Composable
private fun CheckLocationServices() {
    val locationManager =
        LocalContext.current.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        DisplayRationale()
    } else {
        Toast.makeText(LocalContext.current, "Service is enabled", Toast.LENGTH_LONG).show()
        AskForLocationPermissions()
    }
}

@Composable
private fun DisplayRationale() {
    val context = LocalContext.current
    var shouldAskForCity by remember { mutableStateOf(false) }
    var hasBeenInSettings by remember { mutableStateOf(false) }

    if (!hasBeenInSettings && !shouldAskForCity) {
        AlertDialog(
            title = {
                Text(text = "Application need data about your location")
            },
            text = {
                Text(text = "In order to get proper forecast you can either turn on location service, or simply enter the city that you want see forecast for")
            },
            onDismissRequest = {
                hasBeenInSettings = true
            },
            confirmButton = {
                Button(onClick = {
                    hasBeenInSettings = true
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
    }
    if (hasBeenInSettings) {
        AskForLocationPermissions()
    } else {
        GetCityNameScreen()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun AskForLocationPermissions(
    viewModel: CurrentForecastViewModel = viewModel()
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val allPermissionsRevoked =
        locationPermissionsState.permissions.size ==
                locationPermissionsState.revokedPermissions.size

    var isDeclined by remember { mutableStateOf(false) }
    var isUpgradeToFineDeclined by remember { mutableStateOf(false) }

    // All permissions were granted (fine & coarse). Perform an api call
    if (locationPermissionsState.allPermissionsGranted) {
        Text("Fine location")
        viewModel.fetchUserLocation()
        viewModel.fetchCurrentForecastByLocation()

    } else if (!locationPermissionsState.shouldShowRationale && isDeclined) { // if user declined permissions twice, just ask for a city name
        GetCityNameScreen()
    } else {
        Column {
            // Coarse permission was granted.
            val textToShow = if (!allPermissionsRevoked) {

                /* Check if user tried to upgrade permissions from coarse, to fine.
                 If user will decide to stay with coarse permission, then delete button that displayed rationale */
                if (!locationPermissionsState.permissions[1].status.isGranted &&
                    locationPermissionsState.permissions[1].status.shouldShowRationale
                ) {
                    isUpgradeToFineDeclined = true
                }
                "You can allow program to retrieve your approximate location, or upgrade it to the exact one."
            } else if (locationPermissionsState.shouldShowRationale) {
                isDeclined = true
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

            /* Check if user rejected attempt to upgrading permissions from coarse to fine twice.
             * If yes, then hide button that could have display rationale, because it was rejected twice, so it won't work anymore.
             * If no, display two buttons - first: "Check forecast" will make an api call with coarse location permissions,
             * and the second one, which will allow user to upgrade permissions to fine & coarse. */

            /** the way it works, is that the program will check, if the rationale was displayed once.
            After displaying second rationale, the state of the displaying rationale for fine permission will remain as false,
            and couldn't be triggered for the third time. So if rationale is not needed,
            but displayed already once it will be declined twice, otherwise it would be upgraded to fine **/

            if (isUpgradeToFineDeclined && !locationPermissionsState.permissions[1].status.shouldShowRationale) {
                Button(onClick = {
                    viewModel.fetchUserLocation()
                    viewModel.fetchCurrentForecastByLocation()
                }) {
                    Text(text = "Check forecast")
                }

            } else {
                Button(onClick = {
                    viewModel.fetchUserLocation()
                    viewModel.fetchCurrentForecastByLocation()
                }) {
                    Text(text = "Check forecast")
                }

                Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                    Text(buttonText)
                }
            }
        }
    }
}

@Composable
private fun GetCityNameScreen(
    forecastViewModel: CurrentForecastViewModel = viewModel()
) {
    Column {
        OutlinedTextField(
            value = forecastViewModel.cityName,
            onValueChange = { cityName ->
                forecastViewModel.onCityNameChange(cityName)
            },
            singleLine = true
        )
        Button(onClick = {
            forecastViewModel.funFetchForecastByCity()
        }) {
        }
    }
}
