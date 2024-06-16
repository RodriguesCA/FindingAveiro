package com.example.findingaveiro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findingaveiro.ui.theme.FindingAveiroTheme
import com.google.maps.android.compose.*

class MainPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContent {
            FindingAveiroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val username = remember { "User123" } // Replace with actual username logic
                    MapScreen(username = username)
                }
            }
        }
    }
}

@Composable
fun MapScreen(username: String) {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle shop icon click */ }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Shop")
                    }
                },
                actions = {
                    Text(
                        text = username,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .align(Alignment.CenterVertically),
                        color = Color.White
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    FindingAveiroTheme {
        MapScreen(username = "User123")
    }
}
