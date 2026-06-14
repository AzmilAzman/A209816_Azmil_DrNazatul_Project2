package com.example.a209816_azmil_nazatul_project02

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.LocationServices
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavController, viewModel: com.example.a209816_azmil_nazatul_project02.FeedbackViewModel) {
    val context = LocalContext.current
    val category = viewModel.data.category
    val currentInput = viewModel.messageInput

    var isError by remember { mutableStateOf(false) }

    // Temporary file setup to store captured camera images securely via FileProvider
    val tempFile = remember {
        File(context.cacheDir, "camera_capture_${System.currentTimeMillis()}.jpg")
    }
    val tempUri = remember {
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", tempFile)
    }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            viewModel.updateCapturedImage(tempUri)
        }
    }

    // GPS Location Client Provider
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // System Hardware Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        val locationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

        if (!cameraGranted) {
            Toast.makeText(context, "Camera permission is required!", Toast.LENGTH_SHORT).show()
        }
        if (locationGranted) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        viewModel.updateLocation(location.latitude, location.longitude)
                    } else {
                        Toast.makeText(context, "Unable to fetch GPS location. Please toggle your device GPS.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    // Auto-request device permissions immediately upon screen entry
    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    Scaffold(
        topBar = {
            PeoplesVoiceTopBar(
                title = "Feedback Form",
                navController = navController,
                showBack = true
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Selected Category Block
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Selected Category:", style = MaterialTheme.typography.labelMedium)
                    Text(category, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                }
            }

            // Message Input
            OutlinedTextField(
                value = currentInput,
                onValueChange = {
                    viewModel.updateInput(it)
                    if (it.isNotBlank()) isError = false
                },
                label = { Text("Details / Message for $category") },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                isError = isError,
                supportingText = {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        if (isError) Text("Message cannot be empty!", color = MaterialTheme.colorScheme.error)
                        else Spacer(modifier = Modifier.weight(1f))
                        Text(text = "${currentInput.length} characters")
                    }
                }
            )

            // SENSOR 1: CAMERA UTILITY UI
            Text("Image Attachment (Camera)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            if (viewModel.capturedImageUri != null) {
                Card(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(viewModel.capturedImageUri),
                        contentDescription = "Captured Attachment Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Button(
                onClick = { cameraLauncher.launch(tempUri) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Capture Photo")
            }

            // SENSOR 2: GPS GEO-LOCATION UI
            Text("Location Data (GPS Sensor)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = if(viewModel.latitudeInput.isNotEmpty()) "Lat: ${viewModel.latitudeInput}, Lon: ${viewModel.longitudeInput}" else "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Current Geo-Coordinates") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) }
            )
            Button(
                onClick = {
                    permissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Refresh GPS Location")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.weight(1f)) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        if (currentInput.isBlank()) {
                            isError = true
                        } else {
                            viewModel.submit()
                            navController.navigate("result")
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Submit")
                }
            }
        }
    }
}




