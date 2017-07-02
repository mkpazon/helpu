package com.mamaai.angelhack2017.listing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamaai.angelhack2017.R;
import com.mamaai.angelhack2017.details.DetailsActivity;
import com.mamaai.angelhack2017.model.Worker;
import com.mamaai.angelhack2017.ui.adapter.ListingAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class ListingFragment extends Fragment implements ListingView, ListingAdapter.ListingListener {

    private Unbinder mUnbinder;
    private ListingPresenter mPresenter;
    private ListingAdapter mAdapter;

    @BindView(R.id.recyclerView_listings)
    RecyclerView mRvListings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d(".onCreateView");
        View view = inflater.inflate(R.layout.fragment_listing, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvListings.setLayoutManager(layoutManager);
        mAdapter = new ListingAdapter(new ArrayList<Worker>(), this);
        mRvListings.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Timber.d(".onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        mPresenter = new ListingPresenter(this);
        mPresenter.onActivityCreated();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dispose();
    }

    @Override
    public void populateRecyclerView(List<Worker> workers) {
        mAdapter.addAll(workers);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startDetailsActivity(Worker worker) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_WORKER, worker);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Worker worker, int position) {
        Timber.d(".onItemClick:" + position);
        mPresenter.onItemClick(worker);
    }
}
