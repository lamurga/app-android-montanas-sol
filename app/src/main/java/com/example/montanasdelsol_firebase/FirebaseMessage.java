package com.example.montanasdelsol_firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessage extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        if(message.getData().size() > 0){
            mostrarNotificacion(message.getData().get("title"),message.getData().get("message"));
        }
        if (message.getNotification() != null){
            mostrarNotificacion(message.getNotification().getTitle(),message.getNotification().getBody());
        }
    }
    private RemoteViews disenoNotificacion(String titulo, String mensaje){
        RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(),R.layout.notificacion);
        views.setTextViewText(R.id.nTitulo,titulo);
        views.setTextViewText(R.id.nMensaje,mensaje);
        views.setImageViewResource(R.id.nIcono,R.drawable.shop2);
        return views;
    }

    public void mostrarNotificacion(String title, String message){
        Intent intent = new Intent(this,HomeActivity.class);
        String channel_id = "web_app_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.drawable.shop2)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            builder = builder.setContent(disenoNotificacion(title, message));

        }else{
            builder = builder.setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.shop2);
        }
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channel_id,"web_app", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri,null);
            manager.createNotificationChannel(notificationChannel);
        }
        manager.notify(0, builder.build());

    }
}

