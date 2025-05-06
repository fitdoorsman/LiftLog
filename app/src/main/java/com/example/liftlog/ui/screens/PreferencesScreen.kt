package com.example.liftlog.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Arrangement
import com.example.liftlog.viewmodel.ThemeViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    navController: NavHostController? = null,
    themeViewModel: ThemeViewModel
) {
    val context = LocalContext.current
    val isDarkTheme = themeViewModel.isDarkTheme.collectAsState().value
    val useKg = false // Optional: Implement if needed

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Preferences",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    if (navController != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = useKg, onCheckedChange = { /* Handle unit toggle */ })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Use Kilograms (kg)")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isDarkTheme,
                    onCheckedChange = { themeViewModel.setDarkTheme(it) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Enable Dark Mode",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

    // Handle hardware back for standalone use
    BackHandler {
        if (navController != null) {
            navController.popBackStack()
        } else if (context is Activity) {
            context.finish()
        }
    }
}