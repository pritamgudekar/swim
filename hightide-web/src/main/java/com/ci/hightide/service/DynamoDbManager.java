package com.ci.hightide.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.ci.hightide.model.Entity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DynamoDbManager implements DataStoreManager {

    private AmazonDynamoDB client;

    public DynamoDbManager(@Value("${aws.dynamodb.endpointUrl}")String endpointUrl,
                           @Value("${aws.dynamodb.region}") String region) {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(endpointUrl, region);
        client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();
    }

    @Override
    public void saveItem(String tableName, Entity entity) {
        PutItemRequest request = new PutItemRequest().withTableName(tableName);

        Map<String, AttributeValue> attributes = entity.getAttributeMap().entrySet()
                .stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new AttributeValue(e.getValue())));
        request.withItem(attributes);
        client.putItem(request);
    }

    @Override
    public Entity getItem(String table, String key, String keyValue) {
        GetItemRequest request = new GetItemRequest().withTableName(table).addKeyEntry(key, new AttributeValue(keyValue));
        GetItemResult result = client.getItem(request);
        Entity entity = null;
        if(result.getItem()!=null) {
            Map<String, String> entityAttributes = result.getItem().entrySet()
                    .stream().collect(Collectors.toMap(Map.Entry::getKey, e-> e.getValue().getS()));
            entity = new Entity(key, keyValue, entityAttributes);
        }
        return entity;
    }
}
