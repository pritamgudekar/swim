package com.ci.hightide.resources;


import com.ci.hightide.model.AvailabilityWrapper;
import com.ci.hightide.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping("/coaches")
public class AvailabilityResource {

    @Autowired
    private AvailabilityService availabilityService;

    @RequestMapping(method = RequestMethod.PUT, path = "/addAvailability")
    @ResponseBody
    public boolean addAvailability(AvailabilityWrapper wrapper) {
        return availabilityService.addAvailability(wrapper);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/cancelSingleAvailability/{userName}/{id}")
    @ResponseBody
    public boolean cancelAvailability(@PathVariable("userName") String userName,
                                      @PathVariable("id") String id) {
        return availabilityService.cancelAvailability(userName, Arrays.asList(id));
    }


}
