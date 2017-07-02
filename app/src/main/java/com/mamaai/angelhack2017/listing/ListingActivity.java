package com.mamaai.angelhack2017.listing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mamaai.angelhack2017.ParseConstants;
import com.mamaai.angelhack2017.R;
import com.mamaai.angelhack2017.schedule.request.ScheduleListActivity;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListingActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigation)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d(".onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        ButterKnife.bind(this);

        initPush();
        initDrawer();
    }

    private void initDrawer() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Menu menu = mNavigationView.getMenu();

        // Set on menu item selection listener
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_booking_requests:
                                Intent intent = new Intent(ListingActivity.this, ScheduleListActivity.class);
                                startActivity(intent);
                                break;
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                Timber.d("onOptionsItemSelected - home");
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }

                return true;
            case R.id.menu_booking_requests:
                Timber.d("clicked");
                final Intent intent = new Intent(this, ScheduleListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initPush() {
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
