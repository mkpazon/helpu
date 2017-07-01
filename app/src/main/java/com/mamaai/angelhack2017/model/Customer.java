package com.mamaai.angelhack2017.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class Customer implements Parcelable {
    private String id;
    private String name;
    private int age;
    private String photoUrl;
    private String location;
    private String description;

    public Customer() {
    }

    protected Customer(Parcel in) {
        id = in.readString();
        name = in.readString();
        age = in.readInt();
        photoUrl = in.readString();
        location = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(photoUrl);
        dest.writeString(location);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
