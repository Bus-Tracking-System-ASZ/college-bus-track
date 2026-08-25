package com.collegebustrack.app.data.repository

import com.collegebustrack.app.data.model.Bus
import com.collegebustrack.app.data.model.BusLocation
import kotlinx.coroutines.flow.Flow

interface BusRepository {

    fun observeActiveBuses(): Flow<List<Bus>>

    fun observeBus(busId: String): Flow<Bus?>

    fun observeBusLocation(busId: String): Flow<BusLocation?>
}