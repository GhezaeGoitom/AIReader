package com.gg.aireader.ui.screens.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.gg.aireader.ui.screens.common.BookItem
import com.gg.aireader.ui.screens.model.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    drawerNavController: NavHostController,
    parentNavController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val books by viewModel.books.collectAsState()

    val choosePdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            // Persist permission for future use
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            // Navigate
            parentNavController.navigate("${Routes.READER}?uri=${Uri.encode(it.toString())}")
        }
    }


    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        ElevatedButton(
            onClick = {choosePdfLauncher.launch(arrayOf("application/pdf"))},

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB71C1C),
                contentColor = Color.White
            )

        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text("Add Book", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn() {
            items(books.size) { index ->
                BookItem(books[index]) {
                    parentNavController.navigate("${Routes.READER}?uri=${Uri.encode(books[index].path)}")
    }
            }
        }
    }

}
