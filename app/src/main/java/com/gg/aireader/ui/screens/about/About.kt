package com.gg.aireader.ui.screens.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gg.aireader.R

@Preview(showBackground = true)
@Composable
fun About(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // App Logo (clean UI icon, NOT launcher)
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_round),
            contentDescription = "AI Reader Logo",
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // App Name
        Text(
            text = "AI Reader",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        Text(
            text = "A modern PDF reader built with Jetpack Compose.\n" +
                    "Designed for future AI-powered reading assistance.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Divider()

        Spacer(modifier = Modifier.height(24.dp))

        // Developer Info
        Text(
            text = "Developed by",
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Ghezae G. Weldemariam",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // GitHub Button
        OutlinedButton(onClick = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/GhezaeGoitom/AIReader")
            )
            context.startActivity(intent)
        }) {
            Icon(
                modifier = Modifier
                    .size(25.dp),
                painter = painterResource(R.drawable.ic_github),
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text("GitHub")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Footer
        Text(
            text = "© 2025 AI Reader",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
