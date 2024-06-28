package com.example.test

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.TestTheme
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class QRPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QRCodeScreen{ navigateToShopPage() }
                }
            }
        }
    }
    private fun navigateToShopPage() {
        val intent = Intent(this, ShopPage::class.java)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeScreen(onButtonClick: () -> Unit) {
    val qrCodeBitmap = remember { generateQRCode("Desconto FindingAveiro", 512) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shop") },
                actions = {
                    IconButton(onClick = onButtonClick) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onButtonClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Scan this QR Code", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                bitmap = qrCodeBitmap.asImageBitmap(),
                contentDescription = "Generated QR Code",
                modifier = Modifier.size(256.dp)
            )
        }
    }
}

fun generateQRCode(content: String, size: Int): Bitmap {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            if (bitMatrix.get(x, y)) {
                bitmap.setPixel(x, y, android.graphics.Color.BLACK)
            } else {
                bitmap.setPixel(x, y, android.graphics.Color.WHITE)
            }
        }
    }
    return bitmap
}

@Preview(showBackground = true)
@Composable
fun QRCodeScreenPreview() {
    TestTheme {
        QRCodeScreen({})
    }
}
