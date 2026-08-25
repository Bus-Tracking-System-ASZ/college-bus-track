package com.collegebustrack.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.collegebustrack.app.ui.theme.BusBlue
import com.collegebustrack.app.ui.theme.Muted

@Composable
fun AppScaffold(
    current: AppSection,
    onSelect: (AppSection) -> Unit,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            if (title.isNotEmpty()) {
                TopAppBar(
                    title = { Text(title) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        },
        bottomBar = {
            NavigationBar {
                AppSection.entries.forEach { section ->
                    val (label, icon) = when (section) {
                        AppSection.DASHBOARD -> "Dashboard" to Icons.Outlined.Dashboard
                        AppSection.MAP -> "Live Map" to Icons.Outlined.Map
                        AppSection.PROFILE -> "Profile" to Icons.Outlined.Person
                    }
                    NavigationBarItem(
                        selected = current == section,
                        onClick = { onSelect(section) },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            content = content
        )
    }
}
