package com.mamaai.angelhack2017.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mamaai.angelhack2017.R;
import com.mamaai.angelhack2017.model.Schedule;
import com.mamaai.angelhack2017.model.Skill;
import com.mamaai.angelhack2017.model.Worker;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {

    private SimpleDateFormat mDateFormatter = new SimpleDateFormat("MMM dd yyyy, hh:mm a", Locale.getDefault());

    private Context mContext;
    private List<Schedule> mItems;

    public ScheduleListAdapter(Context context, List<Schedule> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Schedule schedule = mItems.get(position);
        final Worker worker = schedule.getWorker();
        holder.mTvName.setText(worker.getName());
        ImageLoader.getInstance().displayImage(worker.getPhotoUrl(), holder.mIvWorker);
        String status = schedule.getStatus();
        if (status != null) {
            holder.mTvStatus.setText(schedule.getStatus());

            if (Schedule.STATUS_REJECTED.equals(status)) {
                holder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.status_rejected));
            } else if (Schedule.STATUS_CONFIRMED.equals(status)) {
                holder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.status_confirmed));
            } else {
                holder.mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.status_default));
            }
        } else {
            holder.mTvStatus.setText(R.string.status_requested);
        }
        holder.mTvDateTime.setText(mDateFormatter.format(schedule.getDate()));
        Skill skill = schedule.getSkill();
        holder.mTvSkill.setText(skill.getName());
        holder.mTvPrice.setText(String.format(Locale.getDefault(), "P%.2f", skill.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<Schedule> schedules) {
        mItems.addAll(schedules);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_name)
        TextView mTvName;

        @BindView(R.id.imageView_worker)
        CircleImageView mIvWorker;

        @BindView(R.id.textView_status)
        TextView mTvStatus;

        @BindView(R.id.textView_skill)
        TextView mTvSkill;

        @BindView(R.id.textView_dateTime)
        TextView mTvDateTime;

        @BindView(R.id.textView_price)
        TextView mTvPrice;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
