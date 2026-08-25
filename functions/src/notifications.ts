import * as logger from "firebase-functions/logger";
import {onDocumentCreated} from "firebase-functions/v2/firestore";

/**
 * Fans out every new notifications/{id} document over FCM.
 *
 * Clients subscribe to topics:
 *   - "all-users"                     -> receives every notification
 *   - "route-{routeId}"              -> receives notifications for that route
 */
export const onNotificationCreated = onDocumentCreated(
  "notifications/{notificationId}",
  async (event) => {
    const snapshot = event.data;
    if (!snapshot) {
      return;
    }

    const notification = snapshot.data();
    const title = typeof notification.title === "string" ?
      notification.title :
      "Bus update";
    const message = typeof notification.message === "string" ?
      notification.message :
      "";
    const severity = typeof notification.severity === "string" ?
      notification.severity :
      "INFO";
    const routeId = typeof notification.routeId === "string" ?
      notification.routeId :
      "";

    const topic = routeId.length > 0 ? `route-${routeId}` : "all-users";

    // Lazy-load: keeps cold starts fast (heavy SDK loads on first use).
    const {getMessaging} = await import("firebase-admin/messaging");

    try {
      await getMessaging().send({
        topic: topic,
        notification: {
          title: title,
          body: message,
        },
        data: {
          notificationId: event.params.notificationId,
          routeId: routeId,
          severity: severity,
        },
      });
      logger.info("Notification pushed", {
        notificationId: event.params.notificationId,
        topic: topic,
      });
    } catch (error) {
      // Never retry forever because of FCM problems; log and move on.
      logger.error("FCM fan-out failed", {
        notificationId: event.params.notificationId,
        error: error,
      });
    }
  }
);
