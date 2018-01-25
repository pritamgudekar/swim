package com.ci.hightide.resources;

import com.ci.hightide.model.User;
import com.ci.hightide.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
