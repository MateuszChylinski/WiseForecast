package com.example.wiseforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.wiseforecast.ViewModels.CurrentForecastViewModel
import com.example.wiseforecast.ui.theme.WiseForecastTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WiseForecastTheme {
                checkPermissions()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun checkPermissions(
    forecastViewModel: CurrentForecastViewModel = viewModel()
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    if (locationPermissionsState.allPermissionsGranted) {
        LaunchedEffect(Unit) {
            forecastViewModel.test()
        }
    } else {
        val isAllPermissionsRevoked =
            locationPermissionsState.permissions.size == locationPermissionsState.revokedPermissions.size

        if (!isAllPermissionsRevoked) { // coarse granted
            //TODO - ask for fine location
        } else if (locationPermissionsState.shouldShowRationale) { // both permissions were rejected
            // TODO display rationale?
        } else {
            //TODO user either sees it for the first time, or already denied permissions twice
        }

        //TODO?
        /*
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
         */
    }
}
