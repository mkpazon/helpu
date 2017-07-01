package com.mamaai.angelhack2017;

import com.mamaai.angelhack2017.model.Skill;
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


    public static Observable<List<Skill>> retrieveSkills(final String workerId) {
        Timber.d(".retrieveWorkers");
        return Observable.create(new ObservableOnSubscribe<List<Skill>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<Skill>> emitter) throws Exception {
                ParseObject worker = ParseObject.createWithoutData(ParseConstants.Worker.TYPE, workerId);
                ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.Skill.TYPE);
                query.whereEqualTo(ParseConstants.Skill.FIELD_WORKER, worker);
                query.include(ParseConstants.Skill.FIELD_WORKER);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        Timber.d(".retrieveWorkers -> .done");
                        List<Skill> skills = new ArrayList<>();
                        if (parseObjects != null) {
                            for (ParseObject parseObject : parseObjects) {
                                Skill skill = ParseConverter.toSkill(parseObject);
                                skills.add(skill);
                            }
                            emitter.onNext(skills);
                        }
                        emitter.onComplete();
                    }
                });
            }
        });
    }
}
