package com.ci.hightide.resources;


import com.ci.hightide.model.Availability;
import com.ci.hightide.model.AvailabilityWrapper;
import com.ci.hightide.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
        return availabilityService.cancelAvailability(userName, Collections.singletonList(id));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/getAvailabilityByCoach/{userName}")
    @ResponseBody
    public List<Availability> getAvailabilityByCoach(@PathVariable("userName") String userName) {
        return availabilityService.getAllAvailabilitiesByUserName(userName);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/getAvailabilityForAllCoaches")
    @ResponseBody
    public List<Availability> getAvailabilityForAllCoaches(@RequestParam("startDate") Long start,
                                                           @RequestParam("endDate") Long end) {

        return availabilityService.getAvailabilityForCoaches(LocalDate.ofEpochDay(start), LocalDate.ofEpochDay(end));
    }


}
