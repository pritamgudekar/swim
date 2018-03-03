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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;


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

        AvailabilityWrapper wrapper = new AvailabilityWrapper();
        wrapper.setStartDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 1));
        wrapper.setEndDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 8));
        wrapper.setUserName("test");

        AvailabilityWindow window = new AvailabilityWindow();
        window.setStartTime(LocalTime.of(16, 0));
        window.setEndTime(LocalTime.of(20, 0));
        wrapper.setTimeWindows(Collections.singletonList(window));
        assertTrue(availabilityService.addAvailability(wrapper));
    }

    @Test
    public void testCancellation() {
        String userName = "canceltest";

        AvailabilityWrapper wrapper = new AvailabilityWrapper();
        wrapper.setStartDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 1));
        wrapper.setEndDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 8));
        wrapper.setUserName(userName);

        AvailabilityWindow window = new AvailabilityWindow();
        window.setStartTime(LocalTime.of(16, 0));
        window.setEndTime(LocalTime.of(20, 0));

        wrapper.setTimeWindows(Collections.singletonList(window));

        assertTrue(availabilityService.addAvailability(wrapper));
        List<Availability> availabilities = availabilityService.getAllAvailabilitiesByUserName(userName);
        assertTrue(availabilityService.cancelAvailability(userName, Collections.singletonList(availabilities.get(0).getId())));

        final List<Availability> allAvailabilities = availabilityService.getAllAvailabilitiesByUserName(userName);
        assertTrue(allAvailabilities.get(0).isCancelled());

        List<Availability> activeAvailabilities = availabilityService.getActiveAvailabilitiesByUserName(userName, false);
        assertEquals(activeAvailabilities.size(), 7);
        assertTrue(activeAvailabilities.stream().noneMatch(p -> p.getId().equals(allAvailabilities.get(0).getId())));

        List<Availability> cancelled = availabilityService.getActiveAvailabilitiesByUserName(userName, true);
        assertEquals(cancelled.size(), 1);
        assertEquals(cancelled.get(0).getId(), allAvailabilities.get(0).getId());

    }

    @Test
    public void testAvailabilityForAllCoaches() {

        String userName = "canceltest";

        AvailabilityWrapper firstWeek = new AvailabilityWrapper();
        firstWeek.setStartDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 1));
        firstWeek.setEndDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 8));
        firstWeek.setUserName(userName);

        AvailabilityWindow firstWeekWindow1 = new AvailabilityWindow();
        firstWeekWindow1.setStartTime(LocalTime.of(16, 0));
        firstWeekWindow1.setEndTime(LocalTime.of(20, 0));

        AvailabilityWindow firstWeekWindow2 = new AvailabilityWindow();
        firstWeekWindow2.setStartTime(LocalTime.of(8, 0));
        firstWeekWindow2.setEndTime(LocalTime.of(12, 0));

        firstWeek.setTimeWindows(Arrays.asList(firstWeekWindow1, firstWeekWindow2));

        assertTrue(availabilityService.addAvailability(firstWeek));

        //add user 2 availability
        String userName2 = "canceltest2";

        AvailabilityWrapper secondWeek = new AvailabilityWrapper();
        secondWeek.setStartDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 4));
        secondWeek.setEndDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 8));
        secondWeek.setUserName(userName2);

        AvailabilityWindow secondWeekWindow1 = new AvailabilityWindow();
        secondWeekWindow1.setStartTime(LocalTime.of(16, 0));
        secondWeekWindow1.setEndTime(LocalTime.of(20, 0));

        AvailabilityWindow secondWeekWindow2 = new AvailabilityWindow();
        secondWeekWindow2.setStartTime(LocalTime.of(16, 0));
        secondWeekWindow2.setEndTime(LocalTime.of(20, 0));

        secondWeek.setTimeWindows(Arrays.asList(secondWeekWindow1, secondWeekWindow2));
        assertTrue(availabilityService.addAvailability(secondWeek));

        //query for availability from jan 5 to jan 9
        LocalDate startDate = LocalDate.of(Year.now().getValue(), Month.JANUARY, 5);
        LocalDate endDate = LocalDate.of(Year.now().getValue(), Month.JANUARY, 9);
        List<Availability> availabilities = availabilityService.getAvailabilityForCoaches(startDate, endDate);

        assertNotNull(availabilities);
        assertEquals(availabilities.size(), 8);

        assertTrue(availabilities.stream().noneMatch(Availability::isCancelled));

        List<Availability> firstUserRange = availabilities.stream().filter(p -> p.getUserName().equals(userName)).collect(Collectors.toList());
        assertEquals(firstUserRange.size(), 4);
        firstUserRange.sort((o1, o2) -> (int) (o1.getLocalDateEpoch() - o2.getLocalDateEpoch()));
        assertTrue(firstUserRange.get(0).getLocalDateEpoch() == startDate.toEpochDay());
        assertTrue(firstUserRange.get(firstUserRange.size() - 1).getLocalDateEpoch() == endDate.toEpochDay() - 1);

        List<Availability> secondUserRange = availabilities.stream().filter(p -> p.getUserName().equals(userName2)).collect(Collectors.toList());
        assertEquals(secondUserRange.size(), 4);
        secondUserRange.sort((o1, o2) -> (int) (o1.getLocalDateEpoch() - o2.getLocalDateEpoch()));
        assertTrue(secondUserRange.get(0).getLocalDateEpoch() == startDate.toEpochDay());
        assertTrue(secondUserRange.get(secondUserRange.size() - 1).getLocalDateEpoch() == endDate.toEpochDay() - 1);
    }

    @AfterMethod
    public void tearDown() {
        utils.truncateTable("hightide-coach-availability", Arrays.asList("username", "id"));
    }
}
