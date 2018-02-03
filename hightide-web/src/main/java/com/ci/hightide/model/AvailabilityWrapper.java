package com.ci.hightide.model;

import java.util.List;

public class AvailabilityWrapper {

    private Long startDate;
    private Long endDate;
    private String userName;

    private List<AvailabilityWindow> timeWindows;

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public List<AvailabilityWindow> getTimeWindows() {
        return timeWindows;
    }

    public void setTimeWindows(List<AvailabilityWindow> timeWindows) {
        this.timeWindows = timeWindows;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
