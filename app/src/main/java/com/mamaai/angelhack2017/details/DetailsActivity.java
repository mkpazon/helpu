package com.mamaai.angelhack2017.details;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mamaai.angelhack2017.R;
import com.mamaai.angelhack2017.model.Worker;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_WORKER = "worker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(EXTRA_WORKER)) {
            Worker worker = bundle.getParcelable(EXTRA_WORKER);
            DetailsFragment fragment = DetailsFragment.newInstance(worker);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.layout_container, fragment);
            ft.commit();
        } else {
            finish();
        }
    }
}
