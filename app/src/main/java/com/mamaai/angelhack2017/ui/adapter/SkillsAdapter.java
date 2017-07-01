package com.mamaai.angelhack2017.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mamaai.angelhack2017.model.Skill;

import java.util.List;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class SkillsAdapter extends ArrayAdapter<Skill> {
    private List<Skill> mItems;
    private static final int RESOURCE_ID = android.R.layout.simple_list_item_2;

    public SkillsAdapter(@NonNull Context context, List<Skill> items) {
        super(context, RESOURCE_ID);
        mItems = items;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(RESOURCE_ID, parent, false);
        }
        Skill skill = mItems.get(position);
        TextView mTvSkill = (TextView) convertView.findViewById(android.R.id.text1);
        TextView mTvPrice = (TextView) convertView.findViewById(android.R.id.text2);

        mTvSkill.setText(skill.getName());
        mTvPrice.setText(String.valueOf(skill.getPrice()));

        return super.getView(position, convertView, parent);
    }
}
