package com.mamaai.angelhack2017;

/**
 * Created by mkpazon on 01/07/2017.
 */

public class ParseConstants {
    public static class Worker {
        public static final String TYPE = "Worker";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_AGE = "age";
        public static final String FIELD_PHOTO = "photo";
        public static final String FIELD_LOCATION = "location";
        public static final String FIELD_SKILLS = "skills";
        public static final String FIELD_CREDENTIALS = "credentials";
        public static final String FIELD_DESCRIPTION = "description";
    }

    public static class Skill {
        public static final String TYPE = "Skill";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_PRICE = "price";
        public static final String FIELD_WORKER = "worker";
    }

    public static class Schedule {
        public static final String TYPE = "Schedule";
        public static final String FIELD_DATETIME = "scheduledDate";
        public static final String FIELD_STATUS = "status";
        public static final String FIELD_WORKER = "worker";
        public static final String FIELD_SKILL = "skill";
        public static final String FIELD_CUSTOMER = "customer";
        public static final String FIELD_MESSAGE = "message";
    }

    public static class Customer {
        public static final String TYPE = "Customer";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_AGE = "age";
        public static final String FIELD_PHOTO = "photo";
    }
}
