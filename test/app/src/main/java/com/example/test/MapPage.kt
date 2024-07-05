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
import androidx.compose.foundation.Image
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
    val snippet: String,
    val image: Int
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

    var polygonData by remember {
        mutableStateOf(listOf(
            PolygonData(
                id = "1",
                points = listOf(
                    LatLng(40.63787867676456, -8.65856742392844), // top left corner
                    LatLng(40.63361546575474, -8.661243742835701), // bottom left corner
                    LatLng(40.62004155057745, -8.658948487500192), // bottom right corner
                    LatLng(40.62452855358944, -8.64976460361052), // top right corner
                ),
                title = "Universidade de Aveiro",
                snippet = "Universidade de Aveiro",
                image = R.drawable.ua

            ),
            PolygonData(
                id = "2",
                points = listOf(
                    LatLng(40.63272744890467, -8.64855518938427), // top right corner
                    LatLng(40.63338985277366, -8.648786538128617), // extra
                    LatLng(40.6399927705059, -8.655116460602876), // top left corner
                    LatLng(40.63787867676456, -8.65856742392844), // bottom left corner
                    LatLng(40.63066400728303, -8.653815495400446), // bottom right corner
                ),
                title = "Parque da Macaca",
                snippet = "Não sejas assaltado",
                image = R.drawable.macaca
            ),
            PolygonData(
                id = "3",
                points = listOf(
                    LatLng(40.64584894137766, -8.662885293949005), // top left corner
                    LatLng(40.6366802512532, -8.686951856569015), // extra
                    LatLng(40.63755790248674, -8.687716520992657), // extra
                    LatLng(40.63754339594855, -8.6886914681328), // extra
                    LatLng(40.626249207879724, -8.682347226061006), // bottom right corner
                    LatLng(40.63787867676456, -8.65856742392844), // top right corner
                    LatLng(40.639593752531106, -8.655944809760609), // extra
                ),
                title = "Salinas de Aveiro",
                snippet = "Bem Salgado",
                image = R.drawable.salinas
            ),
            PolygonData(
                id = "4",
                points = listOf(
                    LatLng(40.63754339594855, -8.6886914681328), // top right corner
                    LatLng(40.630624731442886, -8.750970539177398), // bottom right corner
                    LatLng(40.640469790728964, -8.75011962660272), // extra
                    LatLng(40.64205154345762, -8.758143275839053), // extra
                    LatLng(40.642417082386906, -8.757913786302257), // extra
                    LatLng(40.64075503936687, -8.750588131372883), // extra
                    LatLng(40.64413984641427, -8.749853123659687), // bottom left corner
                    LatLng(40.65825001627285, -8.719445773361878), // extra
                    LatLng(40.65896005972578, -8.709161235108828), // extra
                    LatLng(40.65476769594774, -8.702753020122302), // extra
                ),
                title = "Praia da Barra",
                snippet = "Passa no Setimo",
                image = R.drawable.paredao
            ),
            PolygonData(
                id = "5",
                points = listOf(
                    LatLng(40.63754339594855, -8.6886914681328), // top left corner
                    LatLng(40.630624731442886, -8.750970539177398), // bottom left corner
                    LatLng(40.608877485635745, -8.75648653881182), // bottom right corner
                    LatLng(40.6005038853122, -8.690439825852636), // top right corner
                    LatLng(40.626249207879724, -8.682347226061006), // extra
                ),
                title = "Praia da Costa Nova",
                snippet = "Ve as casas riscas",
                image = R.drawable.posto
            ),
            PolygonData(
                id = "6",
                points = listOf(
                    LatLng(40.626249207879724, -8.682347226061006), // bottom right corner
                    LatLng(40.6005038853122, -8.690439825852636), // extra
                    LatLng(40.615252910083306, -8.677101677162222), // bottom left corner
                    LatLng(40.62004155057745, -8.658948487500192), // top left corner
                    LatLng(40.63361546575474, -8.661243742835701), // extra
                    LatLng(40.63787867676456, -8.65856742392844), // top right corner
                ),
                title = "Passadiços de Aveiro",
                snippet = "Não Caias a Agua",
                image = R.drawable.posto

            ),
            PolygonData(
                id = "7",
                points = listOf(
                    LatLng(40.64101637820578, -8.641803370634964), // top left corner
                    LatLng(40.63749526174772, -8.652744098440001), // bottom left corner
                    LatLng(40.63338985277366, -8.648786538128617), // extra
                    LatLng(40.63272744890467, -8.64855518938427), // extra
                    LatLng(40.63066400728303, -8.653815495400446), // extra
                    LatLng(40.62452855358944, -8.64976460361052), // bottom right corner
                    LatLng(40.630406337092076, -8.642941462098724), // top right corner
                ),
                title= "Fabrica do Melia",
                snippet ="Não vais ser explorado",
                image = R.drawable.fabrica
            ),
            PolygonData(
                id = "8",
                points = listOf(
                    LatLng(40.64089602753747, -8.657386395925464), // bottom left corner
                    LatLng(40.639593752531106, -8.655944809760609), // extra
                    LatLng(40.6399927705059, -8.655116460602876), // extra
                    LatLng(40.63749526174772, -8.652744098440001), // bottom right corner
                    LatLng(40.64101637820578, -8.641803370634964), // top right corner
                    LatLng(40.64406001340473, -8.641126114819752), // top left corner
                ),
                title = "Pontes",
                snippet = "Atravessa para o QRCODE",
                image = R.drawable.lacos
            ),
            PolygonData(
                id = "9",
                points = listOf(
                    LatLng(40.64302240946543, -8.659765781903431), // top left corner
                    LatLng(40.651240505405895, -8.645279160695267), // top right corner
                    LatLng(40.64406001340473, -8.641126114819752), // bottom right corner
                    LatLng(40.64089602753747, -8.657386395925464), // bottom left corner
                ),
                title = "Praça de Aveiro",
                snippet = "Vai ao Mercado",
                image = R.drawable.praca
            )
        )
        )}

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
    val deletePoly by sharedViewModel.deletePoly
    var deleteId: List<String> = emptyList()
    if (deletePoly != "") {
        deleteId = deletePoly.split(" ")
    }

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
            polygonData.forEach { polygonData ->
                val centroid = calculateCentroid(polygonData.points)
                if (!deleteId.contains(polygonData.id)) {
                    Polygon(
                        points = polygonData.points,
                        strokeColor = Color.White,
                        strokeWidth = 5f,
                        fillColor = Color.Black

                    )
                }
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
            },
            id = markerData.id,
            deleteId = deleteId,
            image = markerData.image
        )
    }
}
@Composable
fun LocationInfoDialog(title: String, snippet: String, onDismiss: () -> Unit, onQRCodeClick: () -> Unit, id: String, deleteId: List<String>, image: Int) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column () {
                Text(snippet)
                Spacer(modifier = Modifier.height(8.dp))
                if (!deleteId.contains(id)) {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = null,
                        modifier = Modifier.height(100.dp)
                    )
                }
            }
        },
        confirmButton = {
            if (!deleteId.contains(id)) {
                Button(onClick = onQRCodeClick) {
                    Text("Scan QR Code")
                }
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