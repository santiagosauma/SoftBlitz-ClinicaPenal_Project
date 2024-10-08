@file:Suppress("DEPRECATION")

package com.leotesta017.clinicapenal.view.videoPlayer.fullscreenActivities

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.leotesta017.clinicapenal.view.videoPlayer.youtubeVideoPlayer.YouTubePlayerWithLifecycle

class FullscreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videoUrl = intent.getStringExtra("VIDEO_URL") ?: return

        setContent {
            FullscreenVideoScreen(videoUrl = videoUrl)
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
fun FullscreenVideoScreen(videoUrl: String) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val videoId = extractVideoIdFromUrl(videoUrl) // Extrae el videoId del URL

    var isIconVisible by remember { mutableStateOf(false) }

    // Almacenamos el contexto actual en una variable para usarlo luego
    val activity = LocalContext.current as? Activity

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (videoId != null) {
            YouTubePlayerWithLifecycle(videoUrl = videoUrl,isVisible = true,
                onControllerVisibilityChanged = { isVisible -> isVisible.also { isIconVisible = it } })
        } else {
            Text("Invalid YouTube URL", color = Color.Red)
        }


        if (isIconVisible) {
            IconButton(
                onClick = { activity?.finish() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Salir de pantalla completa",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}




// Helper function to extract videoId from YouTube URL
fun extractVideoIdFromUrl(url: String): String? {
    return try {
        val uri = Uri.parse(url)
        uri.getQueryParameter("v")
    } catch (e: Exception) {
        null
    }
}
