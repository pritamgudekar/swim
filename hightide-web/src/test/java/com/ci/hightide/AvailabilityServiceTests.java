package com.ci.hightide;


import com.ci.hightide.model.Availability;
import com.ci.hightide.model.AvailabilityWindow;
import com.ci.hightide.model.AvailabilityWrapper;
import com.ci.hightide.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertTrue;


@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
public class AvailabilityServiceTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private AvailabilityService availabilityService;
    @Autowired
    private DynamodbUtils utils;

    @BeforeMethod
    public void setUp() {
        utils.truncateTable("hightide-coach-availability", Arrays.asList("username", "id"));
    }

    @Test
    public void testSaveAvailability() {
        Instant instant = Instant.now();

        AvailabilityWrapper wrapper = new AvailabilityWrapper();
        wrapper.setStartDate(instant.toEpochMilli());
        instant = instant.plus(7, ChronoUnit.DAYS);
        wrapper.setEndDate(instant.toEpochMilli());
        wrapper.setUserName("test");

        AvailabilityWindow window = new AvailabilityWindow();
        window.setStartTime(Instant.now().toEpochMilli());
        window.setEndTime(Instant.now().plus(4, ChronoUnit.HOURS).toEpochMilli());
        wrapper.setTimeWindows(Arrays.asList(window));
        assertTrue(availabilityService.addAvailability(wrapper));
    }

    @Test
    public void testCancellation() {
        Instant instant = Instant.now();
        String userName = "canceltest";

        AvailabilityWrapper wrapper = new AvailabilityWrapper();
        wrapper.setStartDate(instant.toEpochMilli());
        instant = instant.plus(1, ChronoUnit.DAYS);
        wrapper.setEndDate(instant.toEpochMilli());
        wrapper.setUserName(userName);

        AvailabilityWindow window = new AvailabilityWindow();
        window.setStartTime(Instant.now().toEpochMilli());
        window.setEndTime(Instant.now().plus(4, ChronoUnit.HOURS).toEpochMilli());

        wrapper.setTimeWindows(Arrays.asList(window));

        assertTrue(availabilityService.addAvailability(wrapper));
        List<Availability> availabilities = availabilityService.getAvailabilityByUserName(userName);
        assertTrue(availabilityService.cancelAvailability(userName, Arrays.asList(availabilities.get(0).getId())));
    }


    @AfterMethod
    public void tearDown() {
        utils.truncateTable("hightide-coach-availability", Arrays.asList("username", "id"));

    }
}
