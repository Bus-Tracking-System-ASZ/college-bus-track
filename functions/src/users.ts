import * as logger from "firebase-functions/logger";
import {
  onDocumentCreated,
  onDocumentUpdated,
} from "firebase-functions/v2/firestore";

const VALID_ROLES = ["STUDENT", "DRIVER", "ADMIN"];

/**
 * Writes (or clears) the "role" custom claim for the given user.
 * @param {string} uid The Firebase Auth user id.
 * @param {unknown} role The role value from the users document.
 * @return {Promise<void>} A promise that resolves once the claim is stored.
 */
async function applyRoleClaim(uid: string, role: unknown): Promise<void> {
  // Lazy-load: keeps cold starts fast (heavy SDK loads on first use).
  const {getAuth} = await import("firebase-admin/auth");
  if (typeof role === "string" && VALID_ROLES.includes(role)) {
    await getAuth().setCustomUserClaims(uid, {role: role});
  } else {
    // Remove the claim when the profile has no valid role.
    await getAuth().setCustomUserClaims(uid, {role: null});
  }
}

/**
 * Mirrors users/{uid}.role into the Firebase auth custom claim "role" so
 * firestore.rules can authorize without reading the profile document.
 */
export const onUserCreated = onDocumentCreated("users/{uid}", async (event) => {
  const uid = event.params.uid;
  const role = event.data?.get("role");
  try {
    await applyRoleClaim(uid, role);
    logger.info("Role claim set", {uid, role});
  } catch (error) {
    logger.error("Failed to set role claim", {uid, error: error});
  }
});

/**
 * Keeps the custom claim in sync whenever the role field changes.
 */
export const onUserUpdated = onDocumentUpdated("users/{uid}", async (event) => {
  const uid = event.params.uid;
  const before = event.data?.before.get("role");
  const after = event.data?.after.get("role");
  if (before === after) {
    return;
  }
  try {
    await applyRoleClaim(uid, after);
    logger.info("Role claim updated", {uid, role: after});
  } catch (error) {
    logger.error("Failed to update role claim", {uid, error: error});
  }
});
