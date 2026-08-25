package com.collegebustrack.app.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.collegebustrack.app.data.model.StudentProfile
import com.collegebustrack.app.data.repository.PreviewDashboardDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {
    private val source = PreviewDashboardDataSource()
    private val _profile = MutableStateFlow(source.getDashboardData().student)
    val profile: StateFlow<StudentProfile> = _profile
}
