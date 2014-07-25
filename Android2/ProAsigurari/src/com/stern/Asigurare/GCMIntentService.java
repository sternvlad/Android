package com.stern.Asigurare;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;;

public class GCMIntentService extends GCMBaseIntentService {
    private static final String TAG = "GCMIntentService";
    PendingIntent contentIntent;
    Notification notification;
    static int numMessages = 0;
    public GCMIntentService() {
        super(GetObiecte.SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        GetObiecte.deviceToken = registrationId;
        Log.d("GCMIntentService", "in GCMIntentService");			 
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String a = intent.getStringExtra("data");
        String b = intent.getStringExtra("data2");
        generateNotification(context, a,b);
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");

    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);

    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        Log.i(TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }

    private void generateNotification(Context context, String message,String title) {

    	// Creates an explicit intent for an Activity in your app
//    	 NotificationManager notificationManager = (NotificationManager) context
//                 .getSystemService(NOTIFICATION_SERVICE);
//
//         Notification notification = new Notification();
//         notification.icon = R.drawable.logo;
//         notification.ledARGB = 0xff00ff00;
//         notification.flags |= Notification.FLAG_SHOW_LIGHTS;
//         notification.ledOnMS = 300;
//         notification.ledOffMS = 1000;
//         numMessages++;
//         notification.tickerText = "i-Asigutare";
//         notification.defaults = Notification.DEFAULT_ALL;
//         notification.setLatestEventInfo(context, title, message, PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0));
//         notificationManager.notify(0, notification);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(NOTIFICATION_SERVICE);

        if (this.notification == null)
        	this.notification = new Notification();
        this.notification.icon = R.drawable.logo;
        this.notification.ledARGB = 0xff00ff00;
        this.notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        this.notification.ledOnMS = 300;
        this.notification.ledOffMS = 1000;
        numMessages++;
        this.notification.number = numMessages;
        GetObiecte.numberOfNewNotification++;
        this.notification.tickerText = "ProAsigurari";
        this.notification.defaults = Notification.DEFAULT_ALL;
       
        Intent notificationIntent = new Intent(context, SplashScreen.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(context, title, message, intent);
        notificationManager.notify(numMessages , this.notification);
        AppSettings sett = AppSettings.get(this);
        sett.updateNumberOfNewNotifications(numMessages);
    }
}