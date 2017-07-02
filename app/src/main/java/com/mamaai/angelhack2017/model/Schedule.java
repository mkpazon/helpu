package com.mamaai.angelhack2017.model;

import java.util.Date;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class Schedule {

    public static final String STATUS_CONFIRMED = "confirmed";
    public static final String STATUS_REJECTED = "rejected";
    public static final String STATUS_REQUESTED = "requested";


    private Skill skill;
    private Worker worker;
    private String status;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
