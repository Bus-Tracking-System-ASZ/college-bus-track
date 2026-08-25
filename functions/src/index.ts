/**
 * College Bus Track - Cloud Functions entry point.
 *
 * Modules:
 *  - health.ts        -> healthCheck (HTTP probe)
 *  - users.ts         -> role -> custom claim sync triggers
 *  - busLocation.ts   -> updateBusLocation callable (driver GPS ingestion)
 *  - notifications.ts -> onNotificationCreated FCM fan-out
 *  - maintenance.ts   -> detectStaleBuses schedule, seedDatabase (emulator)
 */

import {setGlobalOptions} from "firebase-functions";

import {healthCheck} from "./health";
import {onNotificationCreated} from "./notifications";
import {onUserCreated, onUserUpdated} from "./users";
import {updateBusLocation} from "./busLocation";
import {detectStaleBuses, seedDatabase} from "./maintenance";

setGlobalOptions({maxInstances: 10});

export {
  healthCheck,
  onNotificationCreated,
  onUserCreated,
  onUserUpdated,
  updateBusLocation,
  detectStaleBuses,
  seedDatabase,
};
