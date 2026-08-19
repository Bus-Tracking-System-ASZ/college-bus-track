package com.collegebustrack.app.data.remote

import com.collegebustrack.app.data.model.Bus
import com.collegebustrack.app.data.model.BusLocation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirestoreBusDataSource(
    private val firestore: FirebaseFirestore
) {

    fun observeActiveBuses(): Flow<List<Bus>> {
        return firestore
            .collection("buses")
            .whereEqualTo("status", "ACTIVE")
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull {
                    it.toObject(Bus::class.java)
                }
            }
    }

    fun observeBusLocation(busId: String): Flow<BusLocation?> {
        return firestore
            .collection("busLocations")
            .document(busId)
            .snapshots()
            .map { document ->
                document.toObject(BusLocation::class.java)
            }
    }
}