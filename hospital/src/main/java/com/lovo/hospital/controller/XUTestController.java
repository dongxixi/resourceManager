package com.lovo.hospital.controller;

import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.entity.EventEntity;
import com.lovo.hospital.entity.PersonnelEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
public class XUTestController {

    /**
     * 跳到日照页面
     *
     * @return
     */
    @RequestMapping("test")
    public String gotoMainPage() {
        return "eventReturn";
    }

    @RequestMapping("eventReturn")
    public ModelAndView eventReturn(String thingID) {
        ModelAndView mv = new ModelAndView("eventReturn");
        List<PersonnelEntity> outerPerson = new ArrayList<>();
        List<PersonnelEntity> innerPerson = new ArrayList<>();
        List<CarEntity> outerCar = new ArrayList<>();
        List<CarEntity> innerCar = new ArrayList<>();
        EventEntity thing = new EventEntity();
        mv.addObject("outerPerson", outerPerson);
        mv.addObject("innerPerson", innerPerson);
        mv.addObject("outerCar", outerCar);
        mv.addObject("innerCar", innerCar);
        mv.addObject("thing", thing);
        return mv;
    }

    @RequestMapping("eventReturn.do")
    public String doEventReturn(String thingID, String persons, String cars) {

        System.out.println(thingID);
        System.out.println(persons);
        System.out.println(cars);

        return "redirect:/eventReturn?thingID=" + thingID;
    }


}
