package com.mamaai.angelhack2017.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mamaai.angelhack2017.R;
import com.mamaai.angelhack2017.model.Worker;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

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
    }
}
