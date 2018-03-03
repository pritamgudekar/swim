package com.ci.hightide.model;


import java.time.LocalTime;

public class AvailabilityWindow {

    private LocalTime startTime;
    private LocalTime endTime;


    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
