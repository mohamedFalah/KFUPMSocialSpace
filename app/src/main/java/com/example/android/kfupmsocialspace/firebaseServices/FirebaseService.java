package com.example.android.kfupmsocialspace.firebaseServices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.android.kfupmsocialspace.MainActivity;
import com.example.android.kfupmsocialspace.MyReservedItemsActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String activity = remoteMessage.getData().get("activity");

        displayNotification(title,body,activity);
    }

    private void displayNotification(String title, String body, String activity) {

        Intent intent ;
        if (activity.equals("MyReservedItemsActivity")) {
            intent = new Intent(this, MyReservedItemsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else{
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defualtSound = RingtoneManager.getDefaultUri((RingtoneManager.TYPE_NOTIFICATION));

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder((this))
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defualtSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}
