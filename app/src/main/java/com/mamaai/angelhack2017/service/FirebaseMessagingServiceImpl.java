package com.mamaai.angelhack2017.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mamaai.angelhack2017.R;
import com.mamaai.angelhack2017.schedule.BookingConfirmedActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import timber.log.Timber;


public class FirebaseMessagingServiceImpl extends FirebaseMessagingService {
    private static final String TYPE = "type";

    private static final class Payload {
        private static final String FIELD_MESSAGE = "message";

        private static final class NewItemNearby {
            private static final String FIELD_ITEM_ID = "itemId";
            private static final String FIELD_OWNER_ID = "ownerId";
            private static final String FIELD_IMAGE_URL = "imageUrl";
        }
    }


    // App update fields
    private static final String LATEST_VERSION_CODE = "latest_version_code";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Timber.d(".onMessageReceived");
        super.onMessageReceived(remoteMessage);

        Timber.i("From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Timber.d("Message data payload: " + remoteMessage.getData());

            // Check if message contains a data payload.
            Map<String, String> data = remoteMessage.getData();
            if (data.containsKey("data")) {
                String json = data.get("data");
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    handleCustom(jsonObject, getApplicationContext());
                } catch (Exception e) {
                    Timber.e(e, "Failed. ");
                    // Do not crash. Let the push notification fail silently.
                }
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Timber.d("Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void handleCustom(JSONObject json, Context context) throws JSONException {
        Timber.d(".handleCustom()");
        if (json.has(TYPE)) {
            final String type = json.getString(TYPE);
            if ("scheduleConfirmed".equals(type)) {
                handleScheduleConfirmed(json, context);
            } else {
                Timber.d("Type \"" + type + "\" not recognized");
            }
        } else {
            Timber.d("No type");
        }
    }

    private void handleScheduleConfirmed(JSONObject json, Context context) throws JSONException {
        String message = json.getString("message");


        Intent intent = new Intent(context, BookingConfirmedActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_transparent_bg)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(alarmSound);

        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());
    }
}
