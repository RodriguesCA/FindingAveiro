package com.example.findingaveiro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.findingaveiro.ui.theme.FindingAveiroTheme
import androidx.compose.material.icons.filled.LocationOn

class ShopPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindingAveiroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShopScreen(
                        { navigateToWelcomePage() },
                        { navigateToMainPage() }
                    )
                }
            }
        }
    }

    private fun navigateToWelcomePage() {
        val intent = Intent(this, WelcomePage::class.java)
        startActivity(intent)
    }
    private fun navigateToMainPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(onRedeemClick: () -> Unit, onLocationClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shop") },
                actions = {
                    IconButton(onClick = { /* Handle cart icon click */ }) {
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
                        IconButton(onClick = onLocationClick) {
                            Icon(Icons.Filled.LocationOn, contentDescription = "Home")
                        }
                        IconButton(onClick = { /* Navigate to Shop */ }) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Shop")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        ShopContent(modifier = Modifier.padding(innerPadding), onRedeemClick = onRedeemClick)
    }
}

@Composable
fun ShopContent(modifier: Modifier = Modifier, onRedeemClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        repeat(5) { index ->
            ShopItemCard(itemName = "Item $index", itemPrice = "$${index * 10}", onRedeemClick = onRedeemClick)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ShopItemCard(itemName: String, itemPrice: String, onRedeemClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle card click */ }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = itemName, style = TextStyle(fontSize = 20.sp))
                Text(text = itemPrice, style = TextStyle(fontSize = 16.sp, color = Color.Gray))
            }
            Button(onClick = onRedeemClick) {
                Text(text = "Redeem")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopScreenPreview() {
    FindingAveiroTheme {
        ShopScreen({}, {})
    }
}
