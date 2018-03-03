package com.ci.hightide.model;

import java.time.LocalDate;
import java.util.List;

public class AvailabilityWrapper {

    private LocalDate startDate;
    private LocalDate endDate;
    private String userName;

    private List<AvailabilityWindow> timeWindows;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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
