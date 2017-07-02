package com.mamaai.angelhack2017;

import com.mamaai.angelhack2017.model.Customer;
import com.mamaai.angelhack2017.model.Schedule;
import com.mamaai.angelhack2017.model.Skill;
import com.mamaai.angelhack2017.model.Worker;
import com.mamaai.angelhack2017.util.ParseConverter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class ApiHelper {

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

    public static Completable bookSchedule(final Worker worker, final Customer customer, final Skill skill, final Calendar calendar, final String message) {
        Timber.d(".bookSchedule");
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final CompletableEmitter emitter) throws Exception {
                ParseObject parseWorker = ParseObject.createWithoutData(ParseConstants.Worker.TYPE, worker.getId());
                ParseObject parseCustomer = ParseObject.createWithoutData(ParseConstants.Customer.TYPE, customer.getId());
                ParseObject parseSkill = ParseObject.createWithoutData(ParseConstants.Skill.TYPE, skill.getId());
                ParseObject parseSchedule = new ParseObject(ParseConstants.Schedule.TYPE);
                parseSchedule.put(ParseConstants.Schedule.FIELD_DATETIME, calendar.getTime());
                parseSchedule.put(ParseConstants.Schedule.FIELD_CUSTOMER, parseCustomer);
                parseSchedule.put(ParseConstants.Schedule.FIELD_WORKER, parseWorker);
                parseSchedule.put(ParseConstants.Schedule.FIELD_SKILL, parseSkill);
                parseSchedule.put(ParseConstants.Schedule.FIELD_MESSAGE, message);
                parseSchedule.put(ParseConstants.Schedule.FIELD_STATUS, "requested");
                parseSchedule.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Timber.i(".saveInBackground");
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    public static Observable<List<Schedule>> retrieveSchedules(final String customerId) {
        Timber.d(".retrieveWorkers");
        return Observable.create(new ObservableOnSubscribe<List<Schedule>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<Schedule>> emitter) throws Exception {
                ParseObject customer = ParseObject.createWithoutData(ParseConstants.Customer.TYPE, customerId);
                ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.Schedule.TYPE);
                query.whereEqualTo(ParseConstants.Schedule.FIELD_CUSTOMER, customer);
                query.include(ParseConstants.Schedule.FIELD_WORKER);
                query.include(ParseConstants.Schedule.FIELD_CUSTOMER);
                query.include(ParseConstants.Schedule.FIELD_SKILL);
                query.include(ParseConstants.Schedule.FIELD_SKILL + "." + ParseConstants.Skill.FIELD_WORKER);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        Timber.d(".retrieveWorkers -> .done");
                        List<Schedule> schedules = new ArrayList<>();
                        if (parseObjects != null) {
                            for (ParseObject parseObject : parseObjects) {
                                Schedule schedule = ParseConverter.toSchedule(parseObject);
                                schedules.add(schedule);
                            }
                            emitter.onNext(schedules);
                        }
                        emitter.onComplete();
                    }
                });
            }
        });
    }
}
