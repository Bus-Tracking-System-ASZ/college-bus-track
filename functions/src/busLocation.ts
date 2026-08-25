import {HttpsError, onCall} from "firebase-functions/v2/https";

interface UpdateLocationData {
  busId?: string;
  latitude?: number;
  longitude?: number;
  heading?: number;
}

/**
 * Callable endpoint that driver devices use to push live GPS fixes.
 *
 * Writes busLocations/{busId} exactly as documented in
 * docs/api/backend-contract.md:
 *   { busId, latitude, longitude, heading, timestamp }
 *
 * Only the driver assigned to the bus (or an ADMIN) may push updates.
 */
export const updateBusLocation = onCall(async (request) => {
  const uid = request.auth?.uid;
  if (!uid) {
    throw new HttpsError(
      "unauthenticated",
      "Sign in required to update bus location."
    );
  }

  const data = request.data as UpdateLocationData;
  const busId = data.busId;
  const latitude = data.latitude;
  const longitude = data.longitude;
  const heading = typeof data.heading === "number" ? data.heading : 0.0;

  if (typeof busId !== "string" || busId.length === 0) {
    throw new HttpsError("invalid-argument", "busId is required.");
  }
  if (
    typeof latitude !== "number" || typeof longitude !== "number" ||
    latitude < -90.0 || latitude > 90.0 || longitude < -180.0 ||
    longitude > 180.0
  ) {
    throw new HttpsError("invalid-argument", "Invalid coordinates.");
  }
  if (heading < 0.0 || heading > 360.0) {
    throw new HttpsError("invalid-argument", "Heading must be 0..360.");
  }

  // Lazy-load: keeps cold starts fast (heavy SDK loads on first use).
  const {getFirestore} = await import("firebase-admin/firestore");
  const db = getFirestore();

  const [busSnap, userSnap] = await Promise.all([
    db.collection("buses").doc(busId).get(),
    db.collection("users").doc(uid).get(),
  ]);

  if (!busSnap.exists) {
    throw new HttpsError("not-found", `Unknown bus: ${busId}`);
  }

  const role = userSnap.get("role");
  const assignedDriver = busSnap.get("driverId");
  if (role !== "ADMIN" && !(role === "DRIVER" && assignedDriver === uid)) {
    throw new HttpsError(
      "permission-denied",
      "Only the assigned driver can update this bus."
    );
  }

  await db.collection("busLocations").doc(busId).set({
    busId: busId,
    latitude: latitude,
    longitude: longitude,
    heading: heading,
    timestamp: Date.now(),
  });

  return {ok: true};
});
