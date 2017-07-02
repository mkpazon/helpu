package com.mamaai.angelhack2017.listing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mamaai.angelhack2017.ParseConstants;
import com.mamaai.angelhack2017.R;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

import timber.log.Timber;

public class ListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d(".onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.addUnique(ParseConstants.Installation.FIELD_CHANNELS, "ALL");

        installation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Timber.d("Successfully subscribed to push.");
                } else {
                    Timber.d("Failed to subscribe to push.");
                }
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("ALL");
    }
}
