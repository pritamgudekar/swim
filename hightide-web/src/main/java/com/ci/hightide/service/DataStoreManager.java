package com.ci.hightide.service;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.ci.hightide.model.Entity;

import java.util.Map;

public interface DataStoreManager {

    void saveItem(String table,Entity entity);

    Entity getItem(String table, String key, String keyValue);

    Entity getItem(String studentTable, Map<String, AttributeValue> keyMap);
}
