package com.ci.hightide.model;

public enum DayOfWeek {
    SUN("sunday"),
    MON("monday"),
    TUE("tuesday"),
    WED("wednesday"),
    THU("thursday"),
    FRI("friday"),
    SAT("saturday");

    private String day;

    DayOfWeek(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }
}

