package com.mamaai.angelhack2017.schedule.request;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mamaai.angelhack2017.R;

public class ScheduleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        setTitle(R.string.booking_requests);
    }
}
