package com.mamaai.angelhack2017.listing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mamaai.angelhack2017.R;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d(".onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}
