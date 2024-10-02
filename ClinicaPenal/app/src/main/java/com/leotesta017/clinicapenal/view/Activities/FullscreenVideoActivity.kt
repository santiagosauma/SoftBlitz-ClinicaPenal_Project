@file:Suppress("DEPRECATION")

package com.leotesta017.clinicapenal.view.Activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.GoogleDriveVideoPlayer


class FullscreenVideoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el URL del video desde el intent
        val videoUrl = intent.getStringExtra("VIDEO_URL") ?: return

        setContent {
                FullscreenVideoActivityScreen(videoUrl = videoUrl)
            }
        }


    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }
}

@Composable
fun FullscreenVideoActivityScreen(videoUrl: String) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val videoId = formatGoogleDriveUrl(videoUrl) // Extrae el videoId del URL

    // Almacenamos el contexto actual en una variable para usarlo luego
    val activity = LocalContext.current as? Activity

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (videoId != null) {
            GoogleDriveVideoPlayer(videoUrl = videoUrl,isVisible = true)
        } else {
            Text("Invalid GoogleDrive URL", color = Color.Red)
        }

        // Botón de "Salir de pantalla completa" en la esquina superior izquierda
        IconButton(
            onClick = { activity?.finish() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Salir de pantalla completa", tint = Color.White)
        }
    }
}

fun formatGoogleDriveUrl(url: String): String? {
    val regex = Regex(".*?/file/d/(.*?)/.*")
    val matchResult = regex.find(url)
    val fileId = matchResult?.groupValues?.get(1)
    return fileId?.let {
        "https://drive.google.com/uc?export=download&id=$fileId"
    }
}
