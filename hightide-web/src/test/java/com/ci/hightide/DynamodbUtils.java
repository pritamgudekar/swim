package com.ci.hightide;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DynamodbUtils {

    private DynamoDBMapper mapper;
    private AmazonDynamoDB client;

    public DynamodbUtils(@Value("${aws.dynamodb.endpointUrl}") String endpointUrl,
                         @Value("${aws.dynamodb.region}") String region) {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(endpointUrl, region);
        client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();
        mapper = new DynamoDBMapper(client);
    }

    public void truncateTable(String tableName, List<String> keyAttributes) {
        ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
        ScanResult result = client.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()) {
            Map<String, AttributeValue> key = new HashMap<>();
            for (String attribute : keyAttributes) {
                key.put(attribute, item.get(attribute));
            }
            client.deleteItem(tableName, key);
        }
    }
}
