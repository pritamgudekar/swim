package com.ci.hightide.service;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.ci.hightide.model.Availability;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityService {

    private DynamoDBMapper mapper;

    public AvailabilityService(@Value("${aws.dynamodb.endpointUrl}") String endpointUrl,
                               @Value("${aws.dynamodb.region}") String region) {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(endpointUrl, region);
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();
        mapper = new DynamoDBMapper(client);
    }

    public boolean addAvailability(Availability availability) {
        mapper.save(availability);
        return true;
    }


}
