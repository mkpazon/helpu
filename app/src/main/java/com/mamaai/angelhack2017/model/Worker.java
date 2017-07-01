package com.mamaai.angelhack2017.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class Worker {
    private String name;
    private int age;
    private String photoUrl;
    private List<String> skills = new ArrayList<>();
    private String location;

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

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
