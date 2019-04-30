package com.lovo.police_office.controller;


import com.lovo.police_office.entity.CarLogEntity;
import com.lovo.police_office.entity.EventEntity;
import com.lovo.police_office.entity.PersonnelLogEntity;
import com.lovo.police_office.service.EventService;
import com.lovo.police_office.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class EventReturnController {

    /**
     * 跳到日照页面
     *
     * @return
     */

    @Autowired
    private PersonnelService personnelService;
    @Autowired
    private EventService eventService;

    @RequestMapping("test")
    public String gotoMainPage() {
        return "eventReturn";
    }

    @RequestMapping("eventReturn")
    public ModelAndView eventReturn(String id) {
        ModelAndView mv = new ModelAndView("eventReturn");
        List<PersonnelLogEntity> outerPersons = eventService.getOuterPersons(id);
        mv.addObject("outerPerson", outerPersons);
        List<PersonnelLogEntity> innerPersons = eventService.getInnerPersons(id);
        mv.addObject("innerPerson", innerPersons);
        List<CarLogEntity> outerCars = eventService.getOuterCars(id);
        mv.addObject("outerCar", outerCars);
        List<CarLogEntity> innerCars = eventService.getInnerCars(id);
        mv.addObject("innerCar", innerCars);
        EventEntity eventInfo = eventService.getEventInfo(id);
        mv.addObject("event", eventInfo);
        return mv;
    }

    @RequestMapping("eventReturn.do")
    public String doEventReturn(String id, String persons, String cars, RedirectAttributes reAttr) {
        persons = persons == null ? "" : persons;
        cars = cars == null ? "" : cars;
        eventService.returnPersonAndCar(id, persons, cars);
        reAttr.addAttribute("id", id);
        return "redirect:/eventReturn";
    }


}
