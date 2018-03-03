package com.ci.hightide.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDate;
import java.util.List;

@DynamoDBTable(tableName = "hightide-class-schedule")
public class ClassSchedule {

    private List<ScheduleWindow> timeWindows;
    private LocalDate localDate;
    private Long localDateEpoch;
    private String userName;
    private String id;
    private boolean cancelled;
    private String lessonType;

    @DynamoDBTypeConvertedJson
    public List<ScheduleWindow> getTimeWindows() {
        return timeWindows;
    }

    public void setTimeWindows(List<ScheduleWindow> timeWindows) {
        this.timeWindows = timeWindows;
    }

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = Availability.LocalDateConverter.class)
    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
        setLocalDateEpoch(localDate.toEpochDay());

    }

    @DynamoDBAttribute
    public Long getLocalDateEpoch() {
        return localDateEpoch;
    }

    public void setLocalDateEpoch(Long localDateEpoch) {
        this.localDateEpoch = localDateEpoch;
    }

    @DynamoDBHashKey(attributeName = "username")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @DynamoDBRangeKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @DynamoDBAttribute
    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }
}
