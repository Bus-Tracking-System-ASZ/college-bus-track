/**
 * Import function triggers from their respective submodules:
 *
 * import {onCall} from "firebase-functions/v2/https";
 * import {onDocumentWritten} from "firebase-functions/v2/firestore";
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

import { setGlobalOptions } from "firebase-functions";
import { onRequest } from "firebase-functions/https";

setGlobalOptions({ maxInstances: 10 });

export const healthCheck = onRequest((request, response) => {
  response.json({
    status: "ok",
    service: "college-bus-track-backend",
    timestamp: new Date().toISOString()
  });
});