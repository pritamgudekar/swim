package com.ci.hightide.service;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.ci.hightide.model.Availability;
import com.ci.hightide.model.AvailabilityWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class AvailabilityService {

    private DynamoDBMapper mapper;

    public AvailabilityService(@Value("${aws.dynamodb.endpointUrl}") String endpointUrl,
                               @Value("${aws.dynamodb.region}") String region) {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(endpointUrl, region);
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();
        mapper = new DynamoDBMapper(client);
    }

    public boolean addAvailability(AvailabilityWrapper wrapper) {
        List<Availability> availabilities = getAvailabilities(wrapper);
        List<DynamoDBMapper.FailedBatch> failedBatches = mapper.batchSave(availabilities);
        return failedBatches.size() == 0;
    }

    private List<Availability> getAvailabilities(AvailabilityWrapper wrapper) {
        List<Availability> availabilities = new ArrayList<>();
        LocalDate startDate = LocalDate.ofEpochDay(wrapper.getStartDate().toEpochDay());
        LocalDate endDate = LocalDate.ofEpochDay(wrapper.getEndDate().toEpochDay());

        for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plus(1, ChronoUnit.DAYS)) {
            Availability availability = new Availability();
            availability.setLocalDate(date);
            availability.setTimeWindows(wrapper.getTimeWindows());
            availability.setCancelled(false);
            availability.setUserName(wrapper.getUserName());
            availability.setId(UUID.randomUUID().toString());
            availabilities.add(availability);
        }
        return availabilities;
    }


    public List<Availability> getAllAvailabilitiesByUserName(String userName) {
        Map<String, AttributeValue> keyMap = new HashMap<>();
        keyMap.put(":userName", new AttributeValue().withS(userName));

        DynamoDBQueryExpression<Availability> queryExpression = new DynamoDBQueryExpression<Availability>()
                .withKeyConditionExpression("username = :userName").withExpressionAttributeValues(keyMap);
        // .withFilterExpression("cancelled = :cancelled").withExpressionAttributeValues(filterMap);
        return mapper.query(Availability.class, queryExpression);
    }

    public List<Availability> getActiveAvailabilitiesByUserName(String userName, boolean isCancelled) {
        Map<String, AttributeValue> filterMap = new HashMap<>();
        filterMap.put(":userName", new AttributeValue().withS(userName));
        filterMap.put(":val2", new AttributeValue().withN(String.valueOf(isCancelled ? 1 : 0)));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("username = :userName and cancelled = :val2").withExpressionAttributeValues(filterMap);
        return mapper.scan(Availability.class, scanExpression);
    }

    public boolean cancelAvailability(String userName, List<String> ids) {
        for (String id : ids) {
            Availability availability = new Availability();
            availability.setUserName(userName);
            availability.setId(id);
            availability.setCancelled(true);

            DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                    .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES).build();
            mapper.save(availability, config);
        }
        return true;
    }

    public List<Availability> getAvailabilityForCoaches(LocalDate start, LocalDate end) {
        Map<String, AttributeValue> filterMap = new HashMap<>();
        filterMap.put(":val1", new AttributeValue().withN(String.valueOf(start.toEpochDay())));
        filterMap.put(":val2", new AttributeValue().withN(String.valueOf(end.toEpochDay())));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("localDateEpoch >= :val1 and localDateEpoch <= :val2").withExpressionAttributeValues(filterMap);
        return mapper.scan(Availability.class, scanExpression);
    }
}
