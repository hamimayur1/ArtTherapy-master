package edu.csulb.com.arttherapy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;


public class UnlockNotifier extends BroadcastReceiver {

    Context contxt;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.contxt = context;
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)){

            //CallPushNotification
            callPushNotification();

        }

        else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){

        }
    }

    private void callPushNotification()
    {
        try{
            Intent intent = new Intent(contxt, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(contxt, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder b = new NotificationCompat.Builder(contxt);

            b.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setTicker("ArtCanvas")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Test your Drawing Skills!!")
                    .setContentText("Try your hands on drawing something!")
                    .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                    .setContentIntent(contentIntent)
                    .setContentInfo("Information");


            NotificationManager notificationManager = (NotificationManager) contxt.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, b.build());
        }
        catch (Exception e)
        {
            System.out.print(e.toString());
        }
    }
}
