package com.ci.hightide.resources;


import com.ci.hightide.model.ClassScheduleWrapper;
import com.ci.hightide.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/class")
public class ClassResource {


    @Autowired
    private ClassService classService;

    @RequestMapping(method = RequestMethod.PUT, path = "/scheduleClass")
    @ResponseBody
    public boolean scheduleClass(ClassScheduleWrapper wrapper) {
        return classService.scheduleClass(wrapper);
    }
}
