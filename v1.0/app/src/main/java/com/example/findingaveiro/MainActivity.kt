package com.example.findingaveiro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findingaveiro.ui.theme.FindingAveiroTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindingAveiroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main{ navigateToShop() }
                }
            }
        }
    }

    private fun navigateToShop() {
        val intent = Intent(this, ShopPage::class.java)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(onIconClick: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User") },
                actions = {
                    IconButton(onClick = onIconClick) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.light_sky_blue),
                    scrolledContainerColor = colorResource(id = R.color.light_sky_blue),
                    navigationIconContentColor = colorResource(id = R.color.white),
                    titleContentColor = colorResource(id = R.color.white),
                    actionIconContentColor = colorResource(id = R.color.white)
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.LocationOn, contentDescription = "Home")
                        }
                        IconButton(onClick = onIconClick) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Shop")
                        }
                    }
                }
            )
        }
    ) {
        innerPadding -> Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
                Map()
            }
    }
}

@Composable
fun Map() {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(40.6412,  -8.65362), 5f)
    }
    var isMapLoaded by remember { mutableStateOf(false) }

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL
            )
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        onMapLoaded = { isMapLoaded = true },
    )
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    FindingAveiroTheme {
        Main{}
    }
}
