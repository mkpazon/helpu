package com.mamaai.angelhack2017.model;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class Worker {
    private String name ;
    private int age;
    private String photoUrl;
    private String[] skills;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
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

    public String[] getSkills() {
        return skills;
    }
}
