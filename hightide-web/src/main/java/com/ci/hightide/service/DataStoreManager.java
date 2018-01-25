package com.ci.hightide.service;

import com.ci.hightide.model.Entity;

public interface DataStoreManager {

    void saveItem(String table,Entity entity);

    Entity getItem(String table, String key, String keyValue);
}
