import {onRequest} from "firebase-functions/v2/https";

/**
 * Simple HTTP health probe used by teammates to verify the backend is live.
 */
export const healthCheck = onRequest((request, response) => {
  response.json({
    status: "ok",
    service: "college-bus-track-backend",
    timestamp: new Date().toISOString(),
  });
});
