package com.ci.hightide.service;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.ci.hightide.model.Availability;
import com.ci.hightide.model.AvailabilityWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
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
        Instant startDate = Instant.ofEpochMilli(wrapper.getStartDate());
        Instant endDate = Instant.ofEpochMilli(wrapper.getEndDate());

        for (Instant date = startDate; date.isBefore(endDate); date = date.plus(1, ChronoUnit.DAYS)) {
            Availability availability = new Availability();
            availability.setDate(date.toEpochMilli());
            availability.setTimeWindows(wrapper.getTimeWindows());
            availability.setCancelled(false);
            availability.setUserName(wrapper.getUserName());
            availability.setId(UUID.randomUUID().toString());
            availabilities.add(availability);
        }
        return availabilities;
    }


    public List<Availability> getAvailabilityByUserName(String userName) {
        Map<String, AttributeValue> keyMap = new HashMap<>();
        keyMap.put(":userName", new AttributeValue().withS(userName));

        DynamoDBQueryExpression<Availability> queryExpression = new DynamoDBQueryExpression<Availability>()
                .withKeyConditionExpression("username = :userName").withExpressionAttributeValues(keyMap);
        // .withFilterExpression("cancelled = :cancelled").withExpressionAttributeValues(filterMap);
        return mapper.query(Availability.class, queryExpression);
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
}
