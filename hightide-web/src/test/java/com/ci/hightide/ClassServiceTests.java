package com.ci.hightide;


import com.ci.hightide.model.ClassSchedule;
import com.ci.hightide.model.ClassScheduleWrapper;
import com.ci.hightide.model.ScheduleWindow;
import com.ci.hightide.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
public class ClassServiceTests extends BaseIntegrationTest {

    @Autowired
    private ClassService classService;

    @Test
    public void testSaveClass() {

        ClassScheduleWrapper wrapper = new ClassScheduleWrapper();
        wrapper.setStartDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 1));
        wrapper.setEndDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 8));
        wrapper.setUserName("test");

        ScheduleWindow window = new ScheduleWindow();
        window.setStartTime(LocalTime.of(16, 0));
        window.setEndTime(LocalTime.of(20, 0));
        wrapper.setTimeWindows(Collections.singletonList(window));
        assertTrue(classService.scheduleClass(wrapper));
    }

    @Test
    public void testCancelClass() {

        ClassScheduleWrapper wrapper = new ClassScheduleWrapper();
        wrapper.setStartDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 1));
        wrapper.setEndDate(LocalDate.of(Year.now().getValue(), Month.JANUARY, 8));
        wrapper.setLessonType("test");
        wrapper.setUserName("test");

        ScheduleWindow window = new ScheduleWindow();
        window.setStartTime(LocalTime.of(16, 0));
        window.setEndTime(LocalTime.of(20, 0));
        wrapper.setTimeWindows(Collections.singletonList(window));
        assertTrue(classService.scheduleClass(wrapper));

        List<ClassSchedule> classes = classService.getAllSchedules(wrapper.getLessonType());

        assertNotNull(classes);
        assertEquals(classes.size(), 8);

        List<ClassSchedule> sortedList = new ArrayList<>(classes);
        sortedList.sort((o1, o2) -> (int) (o1.getLocalDateEpoch() - o2.getLocalDateEpoch()));

        assertTrue(sortedList.get(0).getLocalDateEpoch() == wrapper.getStartDate().toEpochDay());
        assertTrue(sortedList.get(7).getLocalDateEpoch() == wrapper.getEndDate().toEpochDay());

        assertTrue(classService.cancelClass(wrapper.getUserName(), sortedList.get(6).getId()));

        ClassSchedule schedule = classService.getScheduleByUserNameAndId(wrapper.getUserName(), sortedList.get(6).getId());

        assertNotNull(schedule);

        assertTrue(schedule.isCancelled());
        assertTrue(!schedule.isArchived());

        List<ClassSchedule> allSchedules = classService.getAllSchedules(wrapper.getLessonType());

        assertNotNull(allSchedules);

        assertTrue(allSchedules.size() == 8);

        ClassSchedule cancelled = allSchedules.stream().filter(ClassSchedule::isCancelled).collect(Collectors.toList()).get(0);
        assertTrue(cancelled.isCancelled());
        assertEquals(cancelled.getId(), sortedList.get(6).getId());

    }


    @Override
    public Map<String, List<String>> getTablesMap() {
        Map<String, List<String>> tableMap = new HashMap<>();
        tableMap.put("hightide-class-schedule", Arrays.asList("username", "id"));
        return tableMap;
    }


}
