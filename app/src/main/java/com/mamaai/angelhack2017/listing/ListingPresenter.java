package com.mamaai.angelhack2017.listing;

import com.mamaai.angelhack2017.WorkerManager;
import com.mamaai.angelhack2017.model.Worker;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by mkpazon on 01/07/2017.
 */

class ListingPresenter {

    private CompositeDisposable mDisposables = new CompositeDisposable();
    private ListingView mView;

    ListingPresenter(ListingView listingView) {
        mView = listingView;
    }

    void onActivityCreated() {
        Timber.d(".onActivityCreated");
        WorkerManager.retrieveWorkers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Worker>>() {
                    @Override
                    public void onNext(@NonNull List<Worker> workers) {
                        Timber.d(".onNext");
                        mView.populateRecyclerView(workers);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e, ".onError");
                    }

                    @Override
                    public void onComplete() {
                        Timber.d(".onComplete");
                    }
                });
    }


    public void dispose() {
        mDisposables.dispose();
    }

    public void onItemClick(Worker worker) {
        mView.startDetailsActivity(worker);
    }
}
