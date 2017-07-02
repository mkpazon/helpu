package com.mamaai.angelhack2017.schedule.request;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamaai.angelhack2017.ApiHelper;
import com.mamaai.angelhack2017.R;
import com.mamaai.angelhack2017.model.Schedule;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleListFragment extends Fragment {

    private CompositeDisposable mDisposables = new CompositeDisposable();

    public ScheduleListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Timber.d(".onActivityCreated");

        // TODO change this to a non-hardcoded customer id eventually.
        String customerId = "fgVUaZSMxa";

        mDisposables.add(ApiHelper.retrieveSchedules(customerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Schedule>>() {
                    @Override
                    public void onNext(@NonNull List<Schedule> schedules) {
                        Timber.d(".onNext");

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e,".onError");
                    }

                    @Override
                    public void onComplete() {
                        Timber.d(".onComplete");
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposables.dispose();
    }
}
