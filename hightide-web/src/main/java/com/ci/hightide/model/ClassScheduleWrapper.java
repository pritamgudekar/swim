package com.ci.hightide.model;

import java.time.LocalDate;
import java.util.List;

public class ClassScheduleWrapper {

    private LocalDate startDate;
    private LocalDate endDate;
    private String userName;
    private String lessonType;

    private List<ScheduleWindow> timeWindows;

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

    public List<ScheduleWindow> getTimeWindows() {
        return timeWindows;
    }

    public void setTimeWindows(List<ScheduleWindow> timeWindows) {
        this.timeWindows = timeWindows;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }
}
