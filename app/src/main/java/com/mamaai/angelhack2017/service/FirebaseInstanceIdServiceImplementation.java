package com.mamaai.angelhack2017.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mamaai.angelhack2017.ParseConstants;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

import timber.log.Timber;

public class FirebaseInstanceIdServiceImplementation extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseInstanceId";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO send to parseInstallation
        ParseInstallation parseInstallation = ParseInstallation.getCurrentInstallation();
        parseInstallation.put(ParseConstants.Installation.FIELD_FCM_TOKEN, refreshedToken);
        parseInstallation.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Timber.i("[INSTALLATION]Successfully saved last known location");
                } else {
                    Timber.e(e, "[INSTALLATION]Failed to save last known location to parse installation");
                }
            }
        });
    }
}
