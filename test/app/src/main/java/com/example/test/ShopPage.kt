package com.example.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.asImageBitmap

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
    val points by sharedViewModel.points.observeAsState()

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
        ShopContent(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
fun ShopContent(modifier: Modifier = Modifier, sharedViewModel: SharedViewModel) {
    val items = listOf(
        ShopItem("Ramona", "", "10% de desconto nas batatas", 50, "QRCodeStoreA"),
        ShopItem("CUA", "", "1 fino de graça", 100, "QRCodeStoreB"),
        ShopItem("Boteco", "", "2 euros de desconto no Mojito", 150, "QRCodeStoreC"),
        ShopItem("Love Store", "", "Ofera de Um Cafe", 200, "QRCodeStoreD"),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items.forEach { item ->
            ShopItemCard(
                itemName = item.name,
                itemPrice = item.price,
                itemDescription = item.description,
                icon = Icons.Default.ShoppingCart,
                pointsToRedeem = item.points,
                qrCodeContent = item.qrCodeContent,
                sharedViewModel = sharedViewModel
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ShopItemCard(
    itemName: String,
    itemPrice: String,
    itemDescription: String,
    icon: ImageVector,
    pointsToRedeem: Int,
    qrCodeContent: String,
    sharedViewModel: SharedViewModel
) {
    var isRedeemed by remember { mutableStateOf(sharedViewModel.isItemRedeemed(itemName)) }
    var showQRCode by remember { mutableStateOf(false) }


    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle card click */ }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
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
                if (!isRedeemed) {
                    Button(
                        onClick = {
                            if (sharedViewModel.redeemItem(itemName, pointsToRedeem)) {
                                isRedeemed = true
                                showQRCode = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "Redeem ($pointsToRedeem pts)")
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Redeemed", color = Color.Green, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { showQRCode = true }) {
                            Text(text = "View QR Code")
                        }
                    }
                }
            }
            if (showQRCode) {
                val qrCodeBitmap = generateQRCode(qrCodeContent, 256)
                Image(
                    bitmap = qrCodeBitmap.asImageBitmap(),
                    contentDescription = "Generated QR Code",
                    modifier = Modifier
                        .size(128.dp)
                        .padding(8.dp)
                )
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

data class ShopItem(
    val name: String,
    val price: String,
    val description: String,
    val points: Int,
    val qrCodeContent: String
)
