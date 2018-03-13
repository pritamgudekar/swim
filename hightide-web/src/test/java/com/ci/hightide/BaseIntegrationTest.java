package com.ci.hightide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.List;
import java.util.Map;


public abstract class BaseIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DynamodbUtils utils;

    @BeforeMethod
    public void setUp() {
        truncateTables();
    }

    @AfterMethod
    public void tearDown() {
        truncateTables();
    }

    private void truncateTables() {
        Map<String, List<String>> tablesMap = getTablesMap();
        for (Map.Entry<String, List<String>> entry : tablesMap.entrySet()) {
            utils.truncateTable(entry.getKey(), entry.getValue());
        }
    }


    public abstract Map<String, List<String>> getTablesMap();
}
