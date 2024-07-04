package com.example.test

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.test.ui.theme.TestTheme
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

class QRCodeScannerActivity : ComponentActivity() {
    private val sharedViewModel by viewModels<SharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                QRCodeScannerScreen({navigateToShopPage()}, {navigateToMapPage()}, sharedViewModel)
            }
        }
    }
    private fun navigateToShopPage() {
        val intent = Intent(this, ShopPage::class.java)
        startActivity(intent)
    }
    private fun navigateToMapPage() {
        val intent = Intent(this, MapPage::class.java)
        startActivity(intent)
    }
}
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun QRCodeScannerScreen(onButtonClick: () -> Unit, onArrowClick: () -> Unit, sharedViewModel: SharedViewModel) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FindingAveiro") },
                actions = {
                    IconButton(onClick = {
                        sharedViewModel.updateId("")
                        onButtonClick()
                    }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        sharedViewModel.updateId("")
                        onArrowClick()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        },
    ) {
        if (cameraPermissionState.status.isGranted) {
            CameraScreen(modifier = Modifier.padding(it), sharedViewModel)
        } else if (cameraPermissionState.status.shouldShowRationale) {
            Text("Camera Permission permanently denied")
        } else {
            SideEffect {
                cameraPermissionState.run { launchPermissionRequest() }
            }
            Text("No Camera Permission")
        }
    }
}

@Composable
fun CameraScreen(modifier: Modifier, sharedViewModel: SharedViewModel) {
    val localContext = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(localContext) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val previewView = PreviewView(context)
            val preview = Preview.Builder().build()
            val selector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            val analyzer = BarcodeAnalyzer(context, sharedViewModel)

            preview.setSurfaceProvider(previewView.surfaceProvider)

            runCatching {
                cameraProviderFuture.get().bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    ImageAnalysis.Builder().build().also {
                        it.setAnalyzer(ContextCompat.getMainExecutor(context), analyzer)
                    }
                )
            }.onFailure {
                Log.e("CAMERA", "Camera bind error ${it.localizedMessage}", it)
            }

            previewView
        }
    )
}

class BarcodeAnalyzer(private val context: Context, private val sharedViewModel: SharedViewModel) : ImageAnalysis.Analyzer {
    private val options = BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()
    private val scanner = BarcodeScanning.getClient(options)
    private val id = sharedViewModel.id.value

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcode.rawValue?.let {
                            // Assuming 10000 points are added for each QR code scanned
                            sharedViewModel.addPoints(10000)
                            if (!id.isNullOrEmpty()) {
                                sharedViewModel.removePolygonById(id)
                                sharedViewModel.updateId("")
                            }
                            Toast.makeText(context, "Scanned: $it", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("BarcodeAnalyzer", "Error processing image: ${it.localizedMessage}")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}