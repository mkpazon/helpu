package com.mamaai.angelhack2017.schedule;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mamaai.angelhack2017.R;
import com.mamaai.angelhack2017.WorkerManager;
import com.mamaai.angelhack2017.model.Customer;
import com.mamaai.angelhack2017.model.Skill;
import com.mamaai.angelhack2017.model.Worker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class ScheduleFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final String PARAM_WORKER = "worker";
    private CompositeDisposable mDisposables = new CompositeDisposable();

    private Unbinder mUnbinder;
    private Calendar mCalendar;
    private Worker mWorker;
    private Skill mSkillSelected;

    private SimpleDateFormat mTimeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    private SimpleDateFormat mDateFormatter = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());

    @BindView(R.id.editText_message)
    TextView mEtMessage;

    @BindView(R.id.textView_date)
    TextView mTvDate;

    @BindView(R.id.textView_time)
    TextView mTvTime;

    @BindView(R.id.spinner_skills)
    Spinner mSpinnerSkills;

    @BindView(R.id.textView_price)
    TextView mTvPrice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public static ScheduleFragment newInstance(Worker worker) {
        Bundle args = new Bundle();
        args.putParcelable(PARAM_WORKER, worker);
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mWorker = args.getParcelable(PARAM_WORKER);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<Skill> skills = mWorker.getSkills();

        String[] skillArr = new String[skills.size()];
        for (int i = 0; i < skillArr.length; i++) {
            Skill skill = skills.get(i);
            skillArr[i] = skill.getName() + " - " + skill.getPrice();
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, skillArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSkills.setAdapter(adapter);
        mSpinnerSkills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Timber.d(".onItemSelected : " + position);
                mSkillSelected = skills.get(position);
                mTvPrice.setText(String.valueOf(mSkillSelected.getPrice()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick(R.id.button_date)
    public void onClickDate() {
        Timber.d(".onClickDate");

        Calendar calendar;
        if (mCalendar != null) {
            calendar = mCalendar;
        } else {
            calendar = Calendar.getInstance();
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getActivity(), this, year, month, day).show();
    }

    @OnClick(R.id.button_time)
    public void onClickTime() {
        Timber.d(".onClickTime");

        Calendar calendar;
        if (mCalendar != null) {
            calendar = mCalendar;
        } else {
            calendar = Calendar.getInstance();
        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity())).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Timber.d(".onTimeSet");
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);

        mTvTime.setText(mTimeFormatter.format(mCalendar.getTime()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mTvDate.setText(mDateFormatter.format(mCalendar.getTime()));
    }

    @OnClick(R.id.button_book)
    public void onClickBook() {
        Timber.d(".onClickBook");

        // TODO hardcoded, will eventually have to implement login
        Customer customer = new Customer();
        customer.setId("fgVUaZSMxa");

        if (mWorker != null && mSkillSelected != null && mCalendar != null) {
            Timber.i("Booking schedule");
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Please wait while we process your request");
            mDisposables.add(WorkerManager.bookSchedule(mWorker, customer, mSkillSelected, mCalendar, mEtMessage.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            progressDialog.show();
                        }
                    })
                    .doAfterTerminate(new Action() {
                        @Override
                        public void run() throws Exception {
                            progressDialog.dismiss();
                        }
                    })
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            Timber.d("-> .onComplete");
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Timber.e(e, ".onError");
                        }
                    }));
        } else {
            Toast.makeText(getActivity(), "Kindly complete all the required details", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposables.dispose();
    }
}
