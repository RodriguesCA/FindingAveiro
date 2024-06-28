// ShopPage.kt
package com.example.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test.ui.theme.TestTheme
import androidx.compose.runtime.livedata.observeAsState


class ShopPage : ComponentActivity() {
    private val sharedViewModel by viewModels<SharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShopScreen(
                        sharedViewModel,
                        { navigateToQRPage() },
                        { navigateToMapPage() }
                    )
                }
            }
        }
    }

    private fun navigateToQRPage() {
        val intent = Intent(this, QRPage::class.java)
        startActivity(intent)
    }

    private fun navigateToMapPage() {
        val intent = Intent(this, MapPage::class.java)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(sharedViewModel: SharedViewModel, onRedeemClick: () -> Unit, onArrowClick: () -> Unit) {
    val points by sharedViewModel.points.observeAsState(0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shop") },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { /* Handle cart icon click */ }) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
                        }
                        Text("Points: $points", Modifier.padding(start = 8.dp))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onArrowClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        },
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
            ShopItemCard(
                itemName = "Item $index",
                itemPrice = "$${index * 10}",
                itemDescription = "This is a description of item $index.",
                icon = Icons.Default.ShoppingCart,
                onRedeemClick = onRedeemClick
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ShopItemCard(itemName: String, itemPrice: String, itemDescription: String, icon: ImageVector, onRedeemClick: () -> Unit) {
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
            Column(modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(bottom = 8.dp)
                )
                Text(text = itemName, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                Text(text = itemPrice, style = TextStyle(fontSize = 16.sp, color = Color.Gray))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = itemDescription, style = TextStyle(fontSize = 14.sp, color = Color.Gray))
            }
            Button(
                onClick = onRedeemClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Redeem")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopScreenPreview() {
    TestTheme {
        ShopScreen(sharedViewModel = viewModel(), onRedeemClick = {}, onArrowClick = {})
    }
}
