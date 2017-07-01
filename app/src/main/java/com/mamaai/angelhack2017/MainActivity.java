package com.mamaai.angelhack2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject parseObject = new ParseObject("Sample");
        parseObject.put("name", "Mark Pazon");
        parseObject.put("event", "AH2017");
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Timber.d("-> Done");
                if (e == null) {
                    Timber.i("Success");
                } else {
                    Timber.e(e, "Failed ");
                }
            }
        });
    }
}
