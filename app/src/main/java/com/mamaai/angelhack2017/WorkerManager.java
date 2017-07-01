package com.mamaai.angelhack2017;

import com.mamaai.angelhack2017.model.Worker;
import com.mamaai.angelhack2017.util.ParseConverter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class WorkerManager {

    public static Observable<List<Worker>> retrieveWorkers() {
        Timber.d(".retrieveWorkers");
        return Observable.create(new ObservableOnSubscribe<List<Worker>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<Worker>> emitter) throws Exception {
                ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.Worker.TYPE);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        Timber.d(".retrieveWorkers -> .done");
                        List<Worker> workers = new ArrayList<>();
                        for (ParseObject parseObject : parseObjects) {
                            Worker worker = ParseConverter.toWorker(parseObject);
                            workers.add(worker);
                        }
                        emitter.onNext(workers);
                        emitter.onComplete();
                    }
                });
            }
        });
    }
}
