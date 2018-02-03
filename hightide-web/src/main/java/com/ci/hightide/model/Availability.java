package com.ci.hightide.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.List;


@DynamoDBTable(tableName = "hightide-coach-availability")
public class Availability {

    private List<AvailabilityWindow> timeWindows;
    private Long date;
    private String userName;
    private String id;
    private boolean cancelled;

    @DynamoDBAttribute
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
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

    @DynamoDBTypeConvertedJson
    public List<AvailabilityWindow> getTimeWindows() {
        return timeWindows;
    }

    public void setTimeWindows(List<AvailabilityWindow> timeWindows) {
        this.timeWindows = timeWindows;
    }

    @DynamoDBAttribute
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
