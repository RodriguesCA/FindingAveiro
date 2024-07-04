package com.example.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.test.ui.theme.TestTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.GoogleMap
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberMarkerState

class MapPage : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainMap(
                        sharedViewModel = sharedViewModel,
                        onCartClick = { navigateToShopPage() },
                        onProfileClick = { navigateToProfilePage() },
                        onQRCodeScan = { navigateToQRCodeScanner() }
                    )
                }
            }
        }
    }

    private fun navigateToShopPage() {
        val intent = Intent(this, ShopPage::class.java)
        startActivity(intent)
    }
    private fun navigateToProfilePage() {
        val intent = Intent(this, ShopPage::class.java)
        startActivity(intent)
    }
    private fun navigateToQRCodeScanner() {
        val intent = Intent(this, QRCodeScannerActivity::class.java)
        startActivity(intent)
    }
}

data class PolygonData(
    val id: String,
    val points: List<LatLng>,
    val title: String,
    val snippet: String
)
data class StoreData(
    val id: String,
    val name: String,
    val position: LatLng,
    val snippet: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMap(sharedViewModel: SharedViewModel, onCartClick: () -> Unit, onProfileClick: () -> Unit, onQRCodeScan: () -> Unit){
    val aveiro = LatLng(40.6412,  -8.65362)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(aveiro, 11.5f)
    }
    val username by sharedViewModel.username
    val polygonData = sharedViewModel.polygonData.value

    val storeData by remember {
        mutableStateOf(
            listOf(
                StoreData(
                    id = "store1",
                    name = "Ramona",
                    position = LatLng(40.6381, -8.6513),
                    snippet = "Melhor Hamburgaria de Aveiro"
                ),
                StoreData(
                    id = "store2",
                    name = "CUA",
                    position = LatLng(40.6310, -8.6593),
                    snippet = "Cuidado com os copos"
                ),
                StoreData(
                    id = "store3",
                    name = "Boteco",
                    position = LatLng(40.64313464731195, -8.655556784389967),
                    snippet = "Mojitos sao bons"
                ),
                StoreData(
                    id = "store4",
                    name = "Love Store",
                    position = LatLng(40.63409794242317, -8.648636295746705),
                    snippet = "Oferta de Um Cafe"

                )
            )
        )
    }

    var selectedMarkerData by remember {mutableStateOf<PolygonData?>(null)}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(username) },
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                    }
                    IconButton(onClick = onProfileClick) {
                        Icon(Icons.Filled.Person, contentDescription = null)
                    }
                },
            )
        }
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = true)
        ) {
            polygonData?.forEach { polygonData ->
                val centroid = calculateCentroid(polygonData.points)
                Polygon(
                    points = polygonData.points,
                    strokeColor = Color.White,
                    strokeWidth = 5f,
                    fillColor = Color.Black

                )
                Marker(
                    state = rememberMarkerState(position = centroid),
                    title = polygonData.title,
                    snippet = polygonData.snippet,
                    onClick ={
                        selectedMarkerData = polygonData
                        true
                    }

                )
            }
            storeData.forEach { store ->
                Marker(
                    state = rememberMarkerState(position = store.position),
                    title = store.name,
                    snippet = store.snippet,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                )
            }
        }
    }
    selectedMarkerData?.let { markerData ->
        LocationInfoDialog(
            title = markerData.title,
            snippet = markerData.snippet,
            onDismiss = { selectedMarkerData = null },
            onQRCodeClick = {
                onQRCodeScan()
                selectedMarkerData = null
                sharedViewModel.updateId(markerData.id)
            }
        )
    }
}
@Composable
fun LocationInfoDialog(title: String, snippet: String, onDismiss: () -> Unit, onQRCodeClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                Text(snippet)
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        confirmButton = {
            Button(onClick = onQRCodeClick) {
                Text("Scan QR Code")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

fun calculateCentroid(points: List<LatLng>): LatLng {
    var area = 0.0
    var cx = 0.0
    var cy = 0.0
    for (i in points.indices) {
        val j = (i + 1) % points.size
        val xi = points[i].longitude
        val yi = points[i].latitude
        val xj = points[j].longitude
        val yj = points[j].latitude
        val a = xi * yj - xj * yi
        area += a
        cx += (xi + xj) * a
        cy += (yi + yj) * a
    }
    area *= 0.5
    cx /= (6.0 * area)
    cy /= (6.0 * area)
    return LatLng(cy, cx)
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    TestTheme {
        MainMap(
            sharedViewModel = viewModel(),
            onCartClick = {},
            onProfileClick = {},
            onQRCodeScan = {}
        )
    }
}