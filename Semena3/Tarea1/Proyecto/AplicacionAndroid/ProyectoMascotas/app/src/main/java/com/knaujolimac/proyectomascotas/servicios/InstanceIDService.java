package com.knaujolimac.proyectomascotas.servicios;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Camilo Chaparro on 23/10/2016.
 */

public class InstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FIREBASE_TOKEN_DEVICE";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        Log.d(TAG, "Solicitando token" );
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token Solicitado: " + refreshedToken);
    }
}
