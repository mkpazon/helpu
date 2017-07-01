package com.mamaai.angelhack2017.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class Skill implements Parcelable {

    private String id;
    private Worker worker;
    private double price;
    private String name;

    public Skill() {
    }

    protected Skill(Parcel in) {
        id = in.readString();
        worker = in.readParcelable(Worker.class.getClassLoader());
        price = in.readDouble();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(worker, flags);
        dest.writeDouble(price);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Skill> CREATOR = new Creator<Skill>() {
        @Override
        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        @Override
        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
