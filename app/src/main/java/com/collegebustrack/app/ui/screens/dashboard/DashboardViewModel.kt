package com.collegebustrack.app.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import com.collegebustrack.app.data.model.DashboardData
import com.collegebustrack.app.data.repository.PreviewDashboardDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DashboardViewModel : ViewModel() {
    private val source = PreviewDashboardDataSource()
    private val _data = MutableStateFlow(source.getDashboardData())
    val data: StateFlow<DashboardData> = _data
}
