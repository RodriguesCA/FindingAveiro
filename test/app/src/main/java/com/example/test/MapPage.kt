package com.example.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.test.ui.theme.TestTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.GoogleMap
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberMarkerState


class MapPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainMap { navigateToShopPage() }
                }
            }
        }
    }
    private fun navigateToShopPage() {
        val intent = Intent(this, ShopPage::class.java)
        startActivity(intent)
    }
}

data class PolygonData(
    val points: List<LatLng>,
    val title: String,
    val snippet: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMap(onCartClick: () -> Unit) {
    val aveiro = LatLng(40.6412,  -8.65362)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(aveiro, 10f)
    }
    val PolygonPointsUni = listOf(
        LatLng(40.63787867676456, -8.65856742392844), // top left corner
        LatLng(40.63361546575474, -8.661243742835701), // bottom left corner
        LatLng(40.62004155057745, -8.658948487500192), // bottom right corner
        LatLng(40.62452855358944, -8.64976460361052), // top right corner
    )
    val PolygonPointsMacaca = listOf(
        LatLng(40.63272744890467, -8.64855518938427), // top right corner
        LatLng(40.63338985277366, -8.648786538128617), // extra
        LatLng(40.6399927705059, -8.655116460602876), // top left corner
        LatLng(40.63787867676456, -8.65856742392844), // bottom left corner
        LatLng(40.63066400728303, -8.653815495400446), // bottom right corner
    )
    val PolygonPointsSalinas = listOf(
        LatLng(40.64584894137766, -8.662885293949005), // top left corner
        LatLng(40.6366802512532, -8.686951856569015), // extra
        LatLng(40.63755790248674, -8.687716520992657), // extra
        LatLng(40.63754339594855, -8.6886914681328), // extra
        LatLng(40.626249207879724, -8.682347226061006), // bottom right corner
        LatLng(40.63787867676456, -8.65856742392844), // top right corner
        LatLng(40.639593752531106, -8.655944809760609), // extra
    )
    val PolygonPointsBarra = listOf(
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
    )
    val PolygonPointsCostaNova = listOf(
        LatLng(40.63754339594855, -8.6886914681328), // top left corner
        LatLng(40.630624731442886, -8.750970539177398), // bottom left corner
        LatLng(40.608877485635745, -8.75648653881182), // bottom right corner
        LatLng(40.6005038853122, -8.690439825852636), // top right corner
        LatLng(40.626249207879724, -8.682347226061006), // extra
    )
    val PolygonPointsPassadicos = listOf(
        LatLng(40.626249207879724, -8.682347226061006), // bottom right corner
        LatLng(40.6005038853122, -8.690439825852636), // extra
        LatLng(40.615252910083306, -8.677101677162222), // bottom left corner
        LatLng(40.62004155057745, -8.658948487500192), // top left corner
        LatLng(40.63361546575474, -8.661243742835701), // extra
        LatLng(40.63787867676456, -8.65856742392844), // top right corner
    )
    val PolygonPointsPraca = listOf(
        LatLng(40.64302240946543, -8.659765781903431), // top left corner
        LatLng(40.651240505405895, -8.645279160695267), // top right corner
        LatLng(40.64406001340473, -8.641126114819752), // bottom right corner
        LatLng(40.64089602753747, -8.657386395925464), // bottom left corner
    )
    val PolygonPointsPontes = listOf(
        LatLng(40.64089602753747, -8.657386395925464), // bottom left corner
        LatLng(40.639593752531106, -8.655944809760609), // extra
        LatLng(40.6399927705059, -8.655116460602876), // extra
        LatLng(40.63749526174772, -8.652744098440001), // bottom right corner
        LatLng(40.64101637820578, -8.641803370634964), // top right corner
        LatLng(40.64406001340473, -8.641126114819752), // top left corner
    )
    val PolygonPointsFabrica = listOf(
        LatLng(40.64101637820578, -8.641803370634964), // top left corner
        LatLng(40.63749526174772, -8.652744098440001), // bottom left corner
        LatLng(40.63338985277366, -8.648786538128617), // extra
        LatLng(40.63272744890467, -8.64855518938427), // extra
        LatLng(40.63066400728303, -8.653815495400446), // extra
        LatLng(40.62452855358944, -8.64976460361052), // bottom right corner
        LatLng(40.630406337092076, -8.642941462098724), // top right corner
    )

    val polygonData = listOf(
        PolygonData(
            points = PolygonPointsUni,
            title = "Universidade de Aveiro",
            snippet = "Universidade de Aveiro"
        ),
        PolygonData(
            points = PolygonPointsMacaca,
            title = "Parque da Macaca",
            snippet = "Não sejas assaltado"
        ),
        PolygonData(
            points = PolygonPointsSalinas,
            title = "Salinas de Aveiro",
            snippet = "Bem Salgado"
        ),
        PolygonData(
            points = PolygonPointsBarra,
            title = "Praia da Barra",
            snippet = "Passa no Setimo"
        ),
        PolygonData(
            points = PolygonPointsCostaNova,
            title = "Praia da Costa Nova",
            snippet = "Ve as casas riscas"
        ),
        PolygonData(
            points = PolygonPointsPassadicos,
            title = "Passadiços de Aveiro",
            snippet = "Não Caias a Agua"
        ),
        PolygonData(
            points = PolygonPointsFabrica,
            title= "Fabrica do Melia",
            snippet ="Não vais ser explorado"
        ),
        PolygonData(
            points = PolygonPointsPontes,
            title = "Pontes",
            snippet = "Atravessa para o QRCODE"
        ),
        PolygonData(
            points = PolygonPointsPraca,
            title = "Praça de Aveiro",
            snippet = "Vai ao Mercado"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FindingAveiro") },
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                    }
                },
            )
        }
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            cameraPositionState = cameraPositionState
        ) {
            polygonData.forEach { polygonData ->
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
                    snippet = polygonData.snippet
                )
            }
        }
    }
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
        MainMap {}
    }
}