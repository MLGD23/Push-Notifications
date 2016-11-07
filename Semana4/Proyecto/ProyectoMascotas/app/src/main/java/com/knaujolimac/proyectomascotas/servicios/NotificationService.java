package com.knaujolimac.proyectomascotas.servicios;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Gravity;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.knaujolimac.proyectomascotas.R;

/**
 * Created by Camilo Chaparro on 23/10/2016.
 */

public class NotificationService extends FirebaseMessagingService {

    public static final String TAG = "FIREBASE_PETAGRAM";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        //enviarNotificacion(remoteMessage.getNotification().getBody());
        enviarNotificacion(remoteMessage);
    }

    public void enviarNotificacion(RemoteMessage remoteMessage){

        Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String usuarioLike = "";
        if (remoteMessage.getData().size() > 0) {
            usuarioLike = remoteMessage.getData().get(ConstantesServicios.NOFIFICACION_KEY_SERVER_USUARIO);
            Log.d(TAG, "usuarioLike: " + usuarioLike);
        }
        /**
         * Configuracion de la accion mi perfil
         */
        Intent i = new Intent();
        i.setAction(ConstantesServicios.ACTION_NOT_MI_PERFIL);
        i.putExtra(ConstantesServicios.NOTIFICACION_USUARIO,usuarioLike);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action actionMiPerfil=
                new NotificationCompat.Action.Builder(R.drawable.perfil,
                        getString(R.string.label_icon_media_home), pendingIntent)
                        .build();


        /**
         * Configuracion de la accion follow - unfollow
         */
        i = new Intent();
        i.setAction(ConstantesServicios.ACTION_NOT_FOLLOW_UNFOLLOW);
        i.putExtra(ConstantesServicios.NOTIFICACION_USUARIO,usuarioLike);

        pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action actionFollow =
                new NotificationCompat.Action.Builder(R.drawable.followunfollow,
                        getString(R.string.label_icon_follow_unfolllow), pendingIntent)
                        .build();

        /**
         * Configuracion de la accion perfil usuario
         */
        i = new Intent();
        i.setAction(ConstantesServicios.ACTION_NOT_PERFIL_USUARIO);
        i.putExtra(ConstantesServicios.NOTIFICACION_USUARIO,usuarioLike);

        pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action actionPerfilUsuario=
                new NotificationCompat.Action.Builder(R.drawable.perfil,
                        getString(R.string.label_icon_media_usuario), pendingIntent)
                        .build();


        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true)
                        .setBackground(BitmapFactory.decodeResource(getResources(),
                                R.drawable.bk_fondowear))
                        .setGravity(Gravity.CENTER_VERTICAL)
                ;

        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notificacion")
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(sonido)
                .setContentIntent(pendingIntent)
                .extend(wearableExtender.addAction(actionMiPerfil))
                .extend(wearableExtender.addAction(actionFollow))
                .extend(wearableExtender.addAction(actionPerfilUsuario))
                ;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, notificacion.build());
    }


}
