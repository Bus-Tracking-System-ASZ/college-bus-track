package com.collegebustrack.app.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.collegebustrack.app.data.model.StudentProfile
import com.collegebustrack.app.ui.components.*
import com.collegebustrack.app.ui.theme.BusBlue
import com.collegebustrack.app.ui.theme.Muted

@Composable fun ProfileScreen(onSelect: (AppSection) -> Unit) {
    val profile = StudentProfile()
    AppScaffold(AppSection.PROFILE, onSelect, "My Profile & Settings") {
        Column(Modifier.padding(top = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ProfileHero(profile); ContactPanel(profile); PassPanel(profile); EmergencyPanel(profile); SettingsPanel()
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) { OutlinedButton({}, Modifier.weight(1f)) { Icon(Icons.Outlined.Lock, null); Spacer(Modifier.width(6.dp)); Text("Change password") }; Button({}, Modifier.weight(1f)) { Icon(Icons.Outlined.Logout, null); Spacer(Modifier.width(6.dp)); Text("Log out") } }
        }
    }
}

@Composable private fun ProfileHero(p: StudentProfile) = Panel { Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Outlined.AccountCircle, null, tint = BusBlue, modifier = Modifier.size(100.dp)); Text(p.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold); Text(p.course, color = Muted); OutlinedButton({}, Modifier.padding(top = 12.dp)) { Text("Edit profile") } } }
@Composable private fun ContactPanel(p: StudentProfile) = Panel { Text("Contact details", fontWeight = FontWeight.SemiBold); Detail(Icons.Outlined.Email, "Email", p.email); Detail(Icons.Outlined.Phone, "Phone", p.phone); Detail(Icons.Outlined.LocationOn, "Address", p.address) }
@Composable private fun PassPanel(p: StudentProfile) = Panel { Text("Bus pass info", fontWeight = FontWeight.SemiBold); Detail(Icons.Outlined.CreditCard, "Status", "Active"); Detail(Icons.Outlined.ConfirmationNumber, "Pass ID", p.passId); Detail(Icons.Outlined.CalendarToday, "Valid till", p.validTill); OutlinedButton({}, Modifier.padding(top = 10.dp)) { Text("View pass") } }
@Composable private fun EmergencyPanel(p: StudentProfile) = Panel { Text("Emergency contact", fontWeight = FontWeight.SemiBold); Detail(Icons.Outlined.Person, "Name", p.emergencyName); Detail(Icons.Outlined.Phone, "Phone", p.emergencyPhone); Button({}, Modifier.padding(top = 10.dp)) { Text("Update") } }
@Composable private fun Detail(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) = Row(Modifier.padding(top = 12.dp), verticalAlignment = Alignment.Top) { Icon(icon, null, tint = BusBlue, modifier = Modifier.size(20.dp)); Spacer(Modifier.width(10.dp)); Column { Text(label, color = Muted, style = MaterialTheme.typography.labelSmall); Text(value, style = MaterialTheme.typography.bodyMedium) } }
@Composable private fun SettingsPanel() = Panel { Text("App settings", fontWeight = FontWeight.SemiBold); listOf("Push notifications" to true, "Delay alerts" to true, "Route updates" to true, "Sound alerts" to false, "Dark mode" to false).forEach { (label, initial) -> var checked by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(initial) }; Row(Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) { Text(label, Modifier.weight(1f)); Switch(checked, { checked = it }) } } }
