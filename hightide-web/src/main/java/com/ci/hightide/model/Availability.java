package com.ci.hightide.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDate;
import java.util.List;


@DynamoDBTable(tableName = "hightide-coach-availability")
public class Availability {

    private List<AvailabilityWindow> timeWindows;
    private LocalDate localDate;
    private Long localDateEpoch;
    private String userName;
    private String id;
    private boolean cancelled;

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
        setLocalDateEpoch(localDate.toEpochDay());
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

    @DynamoDBAttribute
    public Long getLocalDateEpoch() {
        return localDateEpoch;
    }

    public void setLocalDateEpoch(Long localDateEpoch) {
        this.localDateEpoch = localDateEpoch;
    }

    static public class LocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {

        @Override
        public String convert(final LocalDate time) {
            return time.toString();
        }

        @Override
        public LocalDate unconvert(final String stringValue) {
            return LocalDate.parse(stringValue);
        }
    }
}
