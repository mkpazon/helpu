package com.mamaai.angelhack2017.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mamaai.angelhack2017.ApiHelper;
import com.mamaai.angelhack2017.R;
import com.mamaai.angelhack2017.model.Skill;
import com.mamaai.angelhack2017.model.Worker;
import com.mamaai.angelhack2017.schedule.ScheduleActivity;
import com.mamaai.angelhack2017.ui.dialog.InfoDialogFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class DetailsFragment extends Fragment {

    private Unbinder mUnbinder;
    private static final String PARAM_WORKER = "worker";

    @BindView(R.id.imageView_worker)
    CircleImageView mIvWorker;

    @BindView(R.id.textView_name)
    TextView mTvName;

    @BindView(R.id.textView_description)
    TextView mTvDescription;

    @BindView(R.id.layout_credentials)
    LinearLayout mLayoutCredentials;

    @BindView(R.id.textView_location)
    TextView mTvLocation;

    @BindView(R.id.layout_services)
    LinearLayout mLayoutServices;

    @BindView(R.id.button_hire)
    Button mBtnHire;

    private Worker mWorker;

    public static DetailsFragment newInstance(Worker worker) {
        Bundle args = new Bundle();
        args.putParcelable(PARAM_WORKER, worker);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mWorker = args.getParcelable(PARAM_WORKER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTvName.setText(mWorker.getName());
        ImageLoader.getInstance().displayImage(mWorker.getPhotoUrl(), mIvWorker);

        mTvLocation.setText(mWorker.getLocation());
        List<String> credentials = mWorker.getCredentials();
        for (String credential : credentials) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_credential, mLayoutCredentials, false);
            TextView tvCredential = (TextView) view.findViewById(R.id.textView_credential);
            tvCredential.setText(credential);
            mLayoutCredentials.addView(view);
        }

        mTvDescription.setText(mWorker.getDescription());
        ApiHelper.retrieveSkills(mWorker.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Skill>>() {
                    @Override
                    public void onNext(@NonNull List<Skill> skills) {
                        Timber.d(".onNext");
                        mWorker.setSkills(skills);
                        populateSkills(skills);
                        mBtnHire.setEnabled(true);
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

    private void populateSkills(List<Skill> skills) {
        for (Skill skill : skills) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_services, mLayoutServices, false);
            TextView tvSkillName = (TextView) view.findViewById(R.id.textView_skillName);
            tvSkillName.setText(skill.getName());
            TextView tvPrice = (TextView) view.findViewById(R.id.textView_price);
            ImageView ivSkillInfo = (ImageView) view.findViewById(R.id.imageView_skill_info);
            ivSkillInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InfoDialogFragment.newInstance(getString(R.string.definition), getString(R.string.house_cleaning_description))
                            .show(getFragmentManager(), "infoDialog");
                }
            });
            tvPrice.setText(String.format(Locale.getDefault(), "P%.2f per session", skill.getPrice()));
            mLayoutServices.addView(view);
        }
    }

    @OnClick(R.id.button_hire)
    public void onClickHire() {
        Timber.d(".onClickHire");
        Intent intent = new Intent(getActivity(), ScheduleActivity.class);
        intent.putExtra(ScheduleActivity.EXTRA_WORKER, mWorker);
        startActivity(intent);
    }
}
