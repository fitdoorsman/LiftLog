package com.example.liftlog.ui.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.liftlog.viewmodel.ThemeViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    navController: NavHostController? = null,
    themeViewModel: ThemeViewModel
) {
    val context = LocalContext.current
    val isDarkTheme = themeViewModel.isDarkTheme.collectAsState().value

    // Access SharedPreferences
    val sharedPrefs = context.getSharedPreferences("LiftLogPrefs", Context.MODE_PRIVATE)

    // Read stored kg toggle value
    var useKg by remember { mutableStateOf(sharedPrefs.getBoolean("use_kg", false)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
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
            // Use Kilograms Toggle
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = useKg,
                    onCheckedChange = { isChecked ->
                        useKg = isChecked
                        sharedPrefs.edit().putBoolean("use_kg", isChecked).apply()
                        Toast.makeText(context, "Units updated", Toast.LENGTH_SHORT).show()
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Use Kilograms (kg)")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Enable Dark Mode Toggle
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isDarkTheme,
                    onCheckedChange = { isChecked ->
                        themeViewModel.setDarkTheme(isChecked)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Enable Dark Mode",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

    // Handle hardware back button
    BackHandler {
        if (navController != null) {
            navController.popBackStack()
        } else if (context is Activity) {
            context.finish()
        }
    }
}