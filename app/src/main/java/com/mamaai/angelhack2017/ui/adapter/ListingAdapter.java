package com.mamaai.angelhack2017.ui.adapter;

import android.support.v7.widget.RecyclerView;
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
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {


    private List<Worker> mItems;

    public ListingAdapter(List<Worker> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listing, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Worker worker = mItems.get(position);
        holder.mTvName.setText(worker.getName());
        holder.mTvLocation.setText(worker.getLocation());
        ImageLoader.getInstance().displayImage(worker.getPhotoUrl(), holder.mIvWorker);
        holder.mLayoutCredentials.removeAllViews();
        List<String> skills = worker.getSkills();
        for (String skill : skills) {
            View view = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.item_credential, holder.mLayoutCredentials, false);
            TextView tvCredential = (TextView) view.findViewById(R.id.textView_credential);
            tvCredential.setText(skill);
            holder.mLayoutCredentials.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<Worker> workers) {
        mItems.addAll(workers);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_name)
        TextView mTvName;

        @BindView(R.id.imageView_worker)
        CircleImageView mIvWorker;

        @BindView(R.id.textView_location)
        TextView mTvLocation;

        @BindView(R.id.layout_credentials)
        LinearLayout mLayoutCredentials;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
