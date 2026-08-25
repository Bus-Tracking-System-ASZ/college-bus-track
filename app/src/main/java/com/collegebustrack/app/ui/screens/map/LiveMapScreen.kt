package com.collegebustrack.app.ui.screens.map

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.collegebustrack.app.data.model.DashboardData
import com.collegebustrack.app.ui.components.*
import com.collegebustrack.app.ui.theme.Muted

@Composable fun LiveMapScreen(onSelect: (AppSection) -> Unit) {
    val data = DashboardData()
    var selected by remember { mutableStateOf("Blue Line – Bus 04") }
    AppScaffold(AppSection.MAP, onSelect, "Live Map") {
        Column(Modifier.padding(top = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Select Bus / Route", style = MaterialTheme.typography.labelLarge)
            OutlinedTextField(selected, {}, Modifier.fillMaxWidth(), readOnly = true, label = { Text("Current route") })
            Row { Text("Last updated: 11:45 AM", color = Muted, style = MaterialTheme.typography.labelSmall); Spacer(Modifier.weight(1f)); Icon(Icons.Outlined.Refresh, "Refresh") }
            MapPreview(Modifier.fillMaxWidth().height(300.dp))
            Panel { SectionTitle("Route Details"); Spacer(Modifier.height(8.dp)); Text("Blue Line", fontWeight = FontWeight.SemiBold); Text("Total distance: 18.6 km", color = Muted, style = MaterialTheme.typography.bodySmall); HorizontalDivider(Modifier.padding(vertical = 12.dp)); Text("Stops & ETA", fontWeight = FontWeight.SemiBold); data.stops.forEach { Row(Modifier.fillMaxWidth().padding(top = 9.dp)) { Text(it.time, style = MaterialTheme.typography.labelMedium); Spacer(Modifier.width(18.dp)); Text(it.name, modifier = Modifier.weight(1f)); Text(if (it.state.name == "COMPLETE") "✓" else "•") } }
            }
        }
    }
}
