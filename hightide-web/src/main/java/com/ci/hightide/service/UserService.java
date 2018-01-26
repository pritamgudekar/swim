package com.ci.hightide.service;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.ci.hightide.model.Entity;
import com.ci.hightide.model.Student;
import com.ci.hightide.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserService {

    @Autowired
    private DataStoreManager dataStoreManager;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private static final String USER_TABLE = "hightide-users";
    private static final String STUDENT_TABLE = "hightide-students";
    private static final String USER_KEY = "username";
    private static final String STUDENT_SEARCH_KEY = "studentname";
    private static final String STUDENT_USERNAME_KEY = "username";

    public boolean addUser(User user) {
        Entity currentUser = dataStoreManager.getItem(USER_TABLE, USER_KEY, user.getUserName());
        if (currentUser != null) {
            LOG.info("user with username {} exists!", user.getUserName());
            return false;
        }
        dataStoreManager.saveItem(USER_TABLE, getUserEntity(user));
        return true;
    }

    public boolean isUserNameAvailable(String userName) {
        Entity currentUser = dataStoreManager.getItem(USER_TABLE, USER_KEY, userName);
        return currentUser == null;
    }

    private Entity getUserEntity(User user) {
        Entity userEntity = new Entity("username", user.getUserName());
        userEntity.addAttribute("fullname", user.getFullName());
        userEntity.addAttribute("password", user.getPassword());
        userEntity.addAttribute("email", user.getEmail());
        userEntity.addAttribute("phonenumber", user.getPhoneNumber());
        return userEntity;
    }

    public boolean addStudent(Student student) {
        Map<String, AttributeValue> keyMap = new LinkedHashMap<>();
        keyMap.put(STUDENT_SEARCH_KEY, new AttributeValue(student.getStudentName()));
        keyMap.put(STUDENT_USERNAME_KEY, new AttributeValue(student.getUserName()));
        Entity currentUser = dataStoreManager.getItem(STUDENT_TABLE, keyMap);

        boolean result = false;
        if (currentUser == null) {
            dataStoreManager.saveItem(STUDENT_TABLE, getStudentEntity(student));
            result = true;
        } else {
            LOG.info("student name {} exists for username {}", student.getStudentName(), student.getUserName());
        }
        return result;
    }

    private Entity getStudentEntity(Student student) {
        Map<String, String> keys = new LinkedHashMap<>();
        keys.put("studentname", student.getStudentName());
        keys.put("username", student.getUserName());

        Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put("birthday", student.getBirthDay().toString());
        attributes.put("bithmonth", student.getBirthMonth().toString());

        return new Entity(keys, attributes);
    }
}
