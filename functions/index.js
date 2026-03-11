const admin = require("firebase-admin");
const { onDocumentCreated, onDocumentUpdated } = require("firebase-functions/v2/firestore");

admin.initializeApp();

const db = admin.firestore();

async function sendToTokens(tokens, title, body, data = {}) {
  if (!tokens || tokens.length === 0) return;

  const message = {
    notification: {
      title,
      body,
    },
    data,
    tokens,
  };

  const response = await admin.messaging().sendEachForMulticast(message);

  const invalidTokens = [];
  response.responses.forEach((result, index) => {
    if (!result.success) {
      const errorCode = result.error?.code || "";
      if (
        errorCode.includes("registration-token-not-registered") ||
        errorCode.includes("invalid-registration-token")
      ) {
        invalidTokens.push(tokens[index]);
      }
    }
  });

  if (invalidTokens.length > 0) {
    const usersSnap = await db.collection("users").get();
    const batch = db.batch();

    usersSnap.forEach((doc) => {
      const token = doc.get("fcmToken");
      if (token && invalidTokens.includes(token)) {
        batch.update(doc.ref, {
          fcmToken: admin.firestore.FieldValue.delete(),
        });
      }
    });

    await batch.commit();
  }
}

exports.notifyAdminsOnSosCreated = onDocumentCreated("sos_alerts/{sosId}", async (event) => {
  const snap = event.data;
  if (!snap) return;

  const sos = snap.data();

  const userName = sos.userName || "Unknown User";
  const idNumber = sos.idNumber || "No ID";
  const latitude = sos.latitude;
  const longitude = sos.longitude;

  const body =
    latitude != null && longitude != null
      ? `${userName} (${idNumber}) sent SOS. Tap to open admin alerts.`
      : `${userName} (${idNumber}) sent SOS.`;

  const adminsSnap = await db
    .collection("users")
    .where("role", "==", "ADMIN")
    .where("emergencyAlerts", "==", true)
    .get();

  const tokens = adminsSnap.docs
    .map((doc) => doc.get("fcmToken"))
    .filter((token) => typeof token === "string" && token.trim() !== "");

  await sendToTokens(
    tokens,
    "🚨 SOS Emergency",
    body,
    {
      type: "SOS_ALERT",
      sosId: snap.id,
      userName: String(userName),
      idNumber: String(idNumber),
      latitude: latitude != null ? String(latitude) : "",
      longitude: longitude != null ? String(longitude) : "",
    }
  );
});

exports.notifyUserOnReportStatusChanged = onDocumentUpdated("reports/{reportId}", async (event) => {
  const before = event.data.before.data();
  const after = event.data.after.data();

  if (!before || !after) return;

  const oldStatus = before.status || "";
  const newStatus = after.status || "";

  if (oldStatus === newStatus) return;

  const userId = after.userId;
  if (!userId) return;

  const userRef = db.collection("users").doc(userId);
  const userSnap = await userRef.get();

  if (!userSnap.exists) return;

  const wantsStatusUpdates = userSnap.get("statusUpdates") === true;
  const token = userSnap.get("fcmToken");

  if (!wantsStatusUpdates || !token || typeof token !== "string") return;

  const incidentType = after.incidentType || "Report";
  const title = "📄 Report Status Updated";
  const body = `${incidentType} is now ${newStatus}.`;

  await sendToTokens(
    [token],
    title,
    body,
    {
      type: "REPORT_STATUS",
      reportId: event.params.reportId,
      status: String(newStatus),
      incidentType: String(incidentType),
    }
  );
});