package com.ci.hightide.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.ci.hightide.model.ClassSchedule;
import com.ci.hightide.model.ClassScheduleWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
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

    public ClassSchedule getScheduleByUserNameAndId(String userName, String id) {
        Map<String, AttributeValue> keyMap = new HashMap<>();
        keyMap.put(":userName", new AttributeValue().withS(userName));
        keyMap.put(":id", new AttributeValue().withS(id));

        DynamoDBQueryExpression<ClassSchedule> queryExpression = new DynamoDBQueryExpression<ClassSchedule>()
                .withKeyConditionExpression("username = :userName and id = :id").withExpressionAttributeValues(keyMap);
        List<ClassSchedule> schedules = mapper.query(ClassSchedule.class, queryExpression);
        return schedules.get(0);
    }

    public boolean cancelClass(String userName, String id) {

        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setUserName(userName);
        classSchedule.setId(id);
        classSchedule.setCancelled(true);

        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES).build();
        mapper.save(classSchedule, config);
        return true;
    }

    public List<ClassSchedule> getAllSchedules(String lessonType) {
        Map<String, AttributeValue> filterMap = new HashMap<>();
        filterMap.put(":lessonType", new AttributeValue().withS(lessonType));
        filterMap.put(":archived", new AttributeValue().withN(String.valueOf(0)));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("lessonType = :lessonType and archived = :archived").withExpressionAttributeValues(filterMap);
        return mapper.scan(ClassSchedule.class, scanExpression);
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
