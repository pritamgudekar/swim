package com.ci.hightide.service;

import com.ci.hightide.model.Entity;
import com.ci.hightide.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private DataStoreManager dataStoreManager;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private static final String USER_TABLE = "hightide-users";
    private static final String USER_KEY = "username";

    public boolean addUser(User user) {
        Entity currentUser = dataStoreManager.getItem(USER_TABLE, USER_KEY, user.getUserName());
        if (currentUser != null) {
            LOG.info("user with username {} exists!",user.getUserName());
            return false;
        }
        dataStoreManager.saveItem(USER_TABLE, getUserEntity(user));
        return true;
    }

    public boolean isUserNameAvailable(String userName) {
        Entity currentUser = dataStoreManager.getItem(USER_TABLE, USER_KEY, userName);
        return currentUser !=null;
    }

    private Entity getUserEntity(User user) {
        Entity userEntity = new Entity("username", user.getUserName());
        userEntity.addAttribute("fullname", user.getFullName());
        userEntity.addAttribute("password", user.getPassword());
        userEntity.addAttribute("email", user.getEmail());
        userEntity.addAttribute("phonenumber", user.getPhoneNumber());
        return userEntity;
    }
}
