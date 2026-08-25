package com.collegebustrack.app.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.collegebustrack.app.data.model.*
import com.collegebustrack.app.ui.components.*
import com.collegebustrack.app.ui.theme.*

@Composable
fun DashboardScreen(
    onSelect: (AppSection) -> Unit,
    viewModel: DashboardViewModel = viewModel()
) {
    val data by viewModel.data.collectAsState()

    AppScaffold(AppSection.DASHBOARD, onSelect, "") {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            item {
                Text(
                    "Welcome, ${data.student.name}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "🚌  ${data.bus.busName} is en route.",
                    color = Muted,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            item { QuickStatus(data.bus) }
            item { RouteProgress(data.stops) }
            item {
                SectionTitle("Live Map Preview", "View full map") { onSelect(AppSection.MAP) }
                MapPreview(Modifier.height(190.dp))
            }
            item { SectionTitle("Recent Notifications", "View all") }
            items(data.alerts.size) { AlertItem(data.alerts[it]) }
        }
    }
}

@Composable
private fun QuickStatus(bus: BusStatus) = Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Panel {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Schedule, null, tint = BusBlue)
            Spacer(Modifier.width(12.dp))
            Column {
                Text("ETA", color = Muted)
                Text(bus.eta, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(bus.destination, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Panel(Modifier.weight(1f)) {
            Icon(Icons.Outlined.EventSeat, null, tint = BusBlue)
            Text("Capacity", modifier = Modifier.padding(top = 6.dp))
            Text(
                "${bus.seatsAvailable} / ${bus.totalSeats}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text("seats available", color = Success, style = MaterialTheme.typography.labelSmall)
        }
        Panel(Modifier.weight(1f)) {
            Icon(Icons.Outlined.PersonOutline, null, tint = BusBlue)
            Text("Driver", modifier = Modifier.padding(top = 6.dp))
            Text(bus.driver, fontWeight = FontWeight.SemiBold)
            Text("★★★★☆  ${bus.rating}", color = BusBlue, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
private fun RouteProgress(stops: List<RouteStop>) = Panel {
    SectionTitle("Blue Line", "4 stops remaining")
    Spacer(Modifier.height(10.dp))
    stops.forEachIndexed { index, stop ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if (stop.state == StopState.COMPLETE) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                null,
                tint = if (stop.state == StopState.CURRENT) BusBlue else Muted,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                stop.name,
                modifier = Modifier.weight(1f),
                fontWeight = if (stop.state == StopState.CURRENT) FontWeight.Bold else FontWeight.Normal
            )
            Text(stop.time, color = Muted, style = MaterialTheme.typography.labelMedium)
        }
        if (index < stops.lastIndex) HorizontalDivider(Modifier.padding(start = 8.dp), color = Border)
    }
}

@Composable
private fun AlertItem(alert: Alert) = Panel {
    Row(verticalAlignment = Alignment.Top) {
        Icon(Icons.Outlined.NotificationsNone, null, tint = BusBlue)
        Spacer(Modifier.width(10.dp))
        Column {
            Text(alert.title, fontWeight = FontWeight.SemiBold)
            Text(alert.message, style = MaterialTheme.typography.bodySmall)
            Text(
                alert.ago,
                color = Muted,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}
