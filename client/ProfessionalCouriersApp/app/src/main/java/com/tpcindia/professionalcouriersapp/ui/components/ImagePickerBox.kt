package com.tpcindia.professionalcouriersapp.ui.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpcindia.professionalcouriersapp.R
import java.io.ByteArrayOutputStream

@Composable
fun ImagePickerBox(
    onImagePicked: (ByteArray) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->  
        selectedImageBitmap = bitmap
        bitmap?.let {
            onImagePicked(bitmapToByteArray(it))
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            context.contentResolver.openInputStream(it)?.use { inputStream ->
                val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
                selectedImageBitmap = bitmap
                selectedImageBitmap?.let {
                    onImagePicked(bitmapToByteArray(bitmap))
                }
            }
        }

    }

    if (showDialog) {
        ImagePickerDialog(
            onDismiss = { showDialog = false },
            onCameraSelected = {
                showDialog = false
                cameraLauncher.launch(null)
            },
            onGallerySelected =  {
                showDialog = false
                galleryLauncher.launch("image/*")
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { showDialog = true }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawRoundRect(
                color = Color.Gray,
                style = Stroke(
                    width = 1.5.dp.toPx(),
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                        floatArrayOf(50f, 30f), 20f
                    )
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8.dp.toPx())
            )
        }

        selectedImageBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.matchParentSize()
                    .padding(10.dp)
            )
        } ?: run {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tpc_gallery),
                    contentDescription = null,
                    modifier = Modifier.size(45.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tap to take a picture or",
                    fontSize = 16.sp
                )
                Text(
                    text = "upload an image",
                    fontSize = 16.sp
                )
            }
        }
    }
}

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}
