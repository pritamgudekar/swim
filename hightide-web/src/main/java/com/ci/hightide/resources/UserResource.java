package com.ci.hightide.resources;

import com.ci.hightide.model.Student;
import com.ci.hightide.model.User;
import com.ci.hightide.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method=RequestMethod.PUT,path="/createUser" )
    @ResponseBody
    public boolean createUser(@RequestBody User user) {
        LOG.info("got user {}", user.toString());
        return userService.addUser(user);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/isUserNameAvailable/{username}")
    @ResponseBody
    public boolean isUserNameAvailable(@PathVariable("username") String userName) {
        LOG.info("checking user name availability for {}", userName);
        return userService.isUserNameAvailable(userName);
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/createStudent/{username}")
    @ResponseBody
    public boolean addStudent(@PathVariable("username") String username, @RequestBody Student student) {
        if (!userService.isUserNameAvailable(username)) {
            LOG.info("trying to add student {} for user {}", username, student.toString());
            student.setUserName(username);
            return userService.addStudent(student);
        }
        LOG.info("user {} does not exist, student {} not added", username, student.toString());
        return false;
    }

}
