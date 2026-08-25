package com.collegebustrack.app.navigation

import androidx.compose.runtime.*
import com.collegebustrack.app.ui.components.AppSection
import com.collegebustrack.app.ui.screens.dashboard.DashboardScreen
import com.collegebustrack.app.ui.screens.map.LiveMapScreen
import com.collegebustrack.app.ui.screens.profile.ProfileScreen

@Composable fun CollegeBusApp() {
    var section by rememberSaveable { mutableStateOf(AppSection.DASHBOARD) }
    when (section) {
        AppSection.DASHBOARD -> DashboardScreen { section = it }
        AppSection.MAP -> LiveMapScreen { section = it }
        AppSection.PROFILE -> ProfileScreen { section = it }
        else -> DashboardScreen { section = it }
    }
}
