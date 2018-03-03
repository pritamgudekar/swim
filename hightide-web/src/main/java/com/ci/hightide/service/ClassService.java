package com.ci.hightide.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.ci.hightide.model.ClassSchedule;
import com.ci.hightide.model.ClassScheduleWrapper;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClassService {

    private DynamoDBMapper mapper;

    public ClassService(@Value("${aws.dynamodb.endpointUrl}") String endpointUrl,
                        @Value("${aws.dynamodb.region}") String region) {

        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(endpointUrl, region);
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();
        mapper = new DynamoDBMapper(client);
    }

    public boolean scheduleClass(ClassScheduleWrapper wrapper) {
        List<ClassSchedule> classSchedules = getClassSchedules(wrapper);
        List<DynamoDBMapper.FailedBatch> failedBatches = mapper.batchSave(classSchedules);
        return failedBatches.size() == 0;
    }

    public boolean cancelClass(String userName, String id) {
        return false;
    }

    public List<ClassSchedule> getAllSchedules(String lessonType) {
        return null;
    }

    private List<ClassSchedule> getClassSchedules(ClassScheduleWrapper wrapper) {
        List<ClassSchedule> classSchedules = new ArrayList<>();
        LocalDate startDate = LocalDate.ofEpochDay(wrapper.getStartDate().toEpochDay());
        LocalDate endDate = LocalDate.ofEpochDay(wrapper.getEndDate().toEpochDay());

        for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plus(1, ChronoUnit.DAYS)) {
            ClassSchedule classSchedule = new ClassSchedule();
            classSchedule.setLocalDate(date);
            classSchedule.setTimeWindows(wrapper.getTimeWindows());
            classSchedule.setCancelled(false);
            classSchedule.setUserName(wrapper.getUserName());
            classSchedule.setId(UUID.randomUUID().toString());
            classSchedule.setLessonType(wrapper.getLessonType());
            classSchedules.add(classSchedule);
        }
        return classSchedules;
    }

}
