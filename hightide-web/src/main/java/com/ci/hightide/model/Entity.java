package com.ci.hightide.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Entity {

    private Map<String, String> attributeMap = new LinkedHashMap<>();
    private Map<String, String> keyMap = new LinkedHashMap<>();

    private String keyAttribute;
    private String keyAttributeValue;
    private Integer version = 1;
    private final String VERSION_ATTR = "version";

    public Entity(String keyAttribute, String keyAttributeValue) {
        this.keyAttribute = keyAttribute;
        this.keyAttributeValue = keyAttributeValue;
        attributeMap.put(keyAttribute, keyAttributeValue);
        attributeMap.put(VERSION_ATTR, String.valueOf(version));
    }

    public Entity(Map<String, String> keys, Map<String, String> attributes) {
        keyMap.putAll(keys);
        attributeMap.putAll(keys);
        attributeMap.putAll(attributes);
    }

    public Entity(String keyAttribute, String keyAttributeValue, Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
        this.keyAttribute = keyAttribute;
        this.keyAttributeValue = keyAttributeValue;
        attributeMap.put(VERSION_ATTR, String.valueOf(version));
    }

    public Map<String, String> getAttributeMap() {
        return new LinkedHashMap<>(attributeMap);
    }

    public String getKeyAttribute() {
        return keyAttribute;
    }

    public String getKeyAttributeValue() {
        return keyAttributeValue;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
        attributeMap.put(VERSION_ATTR, String.valueOf(version));
    }

    public void addAttribute(String name, String value) {
        attributeMap.put(name, value);
    }

    public Map<String, String> getKeyMap() {
        return new LinkedHashMap<>(keyMap);
    }
}
