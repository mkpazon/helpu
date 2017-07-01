package com.mamaai.angelhack2017;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.parse.Parse;

import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .server(BuildConfig.API_ROOT_URL)
                .applicationId(BuildConfig.parse_app_id)
                .clientKey(BuildConfig.parse_client_key)
                .clientBuilder(httpClientBuilder)
                .build();
        Parse.initialize(configuration);
    }
}
