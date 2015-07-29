/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.snowdog.dzialajlokalnie.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import pl.snowdog.dzialajlokalnie.MainActivity;
import pl.snowdog.dzialajlokalnie.R;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        PushNotification pushNotification = new PushNotification(data);
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(pushNotification);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param pushNotification GCM message received.
     */
    private void sendNotification(PushNotification pushNotification) {

        PendingIntent pendingIntent = buildPendingIntent(pushNotification);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_action_accept)
                .setContentTitle(pushNotification.getTitle())
                .setSubText(pushNotification.getSubtitle())
                .setContentText(pushNotification.getMessage())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private PendingIntent buildPendingIntent(PushNotification pushNotification) {
        switch (pushNotification.getAction()) {
            case NotificationAction.NEW_ISSUE_SURROUND:
                break;
            case NotificationAction.EDIT_ISSUE:
                break;
            case NotificationAction.DELETE_ISSUE:
                break;

            case NotificationAction.NEW_EVENT_SURROUND:
                break;
            case NotificationAction.NEW_EVENT_USER_PARTICIPATED:
                break;
            case NotificationAction.EDIT_EVENT:
                break;
            case NotificationAction.DELETE_EVENT_USER_SAVED:
                break;
            case NotificationAction.EVENT_REMINDER:
                break;

            case NotificationAction.COMMENT_PARENT_USER_OWNER:
                break;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(NotificationAction.INTENT_ACTION, pushNotification.getAction());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        return pendingIntent;
    }
}
