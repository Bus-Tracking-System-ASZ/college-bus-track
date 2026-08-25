import * as logger from "firebase-functions/logger";
import {HttpsError, onCall} from "firebase-functions/v2/https";
import {onSchedule} from "firebase-functions/v2/scheduler";

/** Buses whose latest fix is older than this are flagged DELAYED. */
const STALE_AFTER_MS = 15 * 60 * 1000;

/**
 * Returns the age in milliseconds of a busLocations timestamp value,
 * or null when the field is missing/unusable.
 * @param {unknown} timestamp Raw timestamp field from a busLocations doc.
 * @return {number | null} Age in milliseconds, or null when unusable.
 */
function locationAgeMs(timestamp: unknown): number | null {
  if (typeof timestamp === "number") {
    return Date.now() - timestamp;
  }
  // Defensive: tolerate Firestore Timestamp values too.
  if (
    timestamp !== null && typeof timestamp === "object" &&
    "toMillis" in (timestamp as object)
  ) {
    return Date.now() - (timestamp as { toMillis(): number }).toMillis();
  }
  return null;
}

/**
 * Every 10 minutes: flags ACTIVE buses as DELAYED when their latest
 * busLocations fix is missing or older than STALE_AFTER_MS.
 */
export const detectStaleBuses = onSchedule("every 10 minutes", async () => {
  const {getFirestore} = await import("firebase-admin/firestore");
  const db = getFirestore();
  const buses = await db
    .collection("buses")
    .where("status", "==", "ACTIVE")
    .get();

  let flagged = 0;
  for (const bus of buses.docs) {
    const locSnap = await db.collection("busLocations").doc(bus.id).get();
    const age = locSnap.exists ? locationAgeMs(locSnap.get("timestamp")) : null;

    if (age === null || age > STALE_AFTER_MS) {
      await bus.ref.update({status: "DELAYED"});
      flagged++;
      logger.info("Bus flagged DELAYED", {busId: bus.id});
    }
  }
  logger.info("Stale-bus sweep complete", {checked: buses.size, flagged});
});

/**
 * Emulator-only helper that seeds routes, stops, buses and sample users so
 * the frontend and map teammates always have data to work against.
 *
 * Refuses to run against production on purpose.
 */
export const seedDatabase = onCall(async () => {
  if (process.env.FUNCTIONS_EMULATOR !== "true") {
    throw new HttpsError(
      "permission-denied",
      "seedDatabase only runs against the local emulator."
    );
  }

  const {getFirestore} = await import("firebase-admin/firestore");
  const db = getFirestore();
  const batch = db.batch();

  const stops = [
    {id: "stop_main_gate", name: "Main Gate",
      lat: 12.9716, lng: 77.5946, seq: 1},
    {id: "stop_library", name: "Library", lat: 12.9722, lng: 77.5955, seq: 2},
    {id: "stop_hostel_a", name: "Hostel A", lat: 12.9731, lng: 77.5966, seq: 3},
    {id: "stop_hostel_b", name: "Hostel B", lat: 12.9740, lng: 77.5975, seq: 4},
    {id: "stop_city_hub", name: "City Hub", lat: 12.9752, lng: 77.5990, seq: 5},
  ];
  for (const s of stops) {
    batch.set(db.collection("stops").doc(s.id), {
      id: s.id,
      name: s.name,
      latitude: s.lat,
      longitude: s.lng,
      sequence: s.seq,
    });
  }

  batch.set(db.collection("routes").doc("route_1"), {
    id: "route_1",
    name: "Route 1 - Main Gate Loop",
    active: true,
    stopIds: ["stop_main_gate", "stop_library", "stop_hostel_a"],
  });
  batch.set(db.collection("routes").doc("route_2"), {
    id: "route_2",
    name: "Route 2 - Hostel Express",
    active: true,
    stopIds: ["stop_hostel_b", "stop_city_hub", "stop_main_gate"],
  });

  batch.set(db.collection("buses").doc("bus_101"), {
    id: "bus_101",
    number: "TN-01-B-101",
    routeId: "route_1",
    driverId: "driver_001",
    capacity: 45,
    seatsAvailable: 45,
    status: "ACTIVE",
  });
  batch.set(db.collection("buses").doc("bus_102"), {
    id: "bus_102",
    number: "TN-01-B-102",
    routeId: "route_2",
    driverId: "driver_002",
    capacity: 40,
    seatsAvailable: 38,
    status: "ACTIVE",
  });

  batch.set(db.collection("users").doc("driver_001"), {
    uid: "driver_001",
    name: "Sample Driver One",
    email: "driver1@example.com",
    phone: "",
    classYear: 0,
    role: "DRIVER",
    passId: "",
    emergencyContactName: "",
    emergencyContactPhone: "",
  });
  batch.set(db.collection("users").doc("student_001"), {
    uid: "student_001",
    name: "Sample Student",
    email: "student1@example.com",
    phone: "",
    classYear: 2,
    role: "STUDENT",
    passId: "PASS-1001",
    emergencyContactName: "",
    emergencyContactPhone: "",
  });

  await batch.commit();

  // Give the live map something to render immediately.
  await db.collection("busLocations").doc("bus_101").set({
    busId: "bus_101",
    latitude: 12.9716,
    longitude: 77.5946,
    heading: 45.0,
    timestamp: Date.now(),
  });

  logger.info("Seed data written");
  return {ok: true};
});
