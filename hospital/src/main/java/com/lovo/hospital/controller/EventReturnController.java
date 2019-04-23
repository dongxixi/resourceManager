package com.lovo.hospital.controller;

import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.entity.DispatchEntity;
import com.lovo.hospital.entity.EventEntity;
import com.lovo.hospital.entity.PersonnelEntity;
import com.lovo.hospital.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    @RequestMapping("test")
    public String gotoMainPage() {
        return "eventReturn";
    }

    @RequestMapping("eventReturn")
    public ModelAndView eventReturn(String eventId) {
//        System.out.println(eventId);


        List<CarEntity> outerCar = new ArrayList<>();
        List<CarEntity> innerCar = new ArrayList<>();
        CarEntity outC = new CarEntity();
        CarEntity innerC = new CarEntity();
        outC.setId("0123");
        outC.setCarNum("川A.00001");
        outC.setDriver("王铁柱");
        outC.setState(1);
        innerC.setId("01234");
        innerC.setCarNum("川A.00002");
        innerC.setDriver("张柱");
        innerC.setState(2);
        outerCar.add(outC);
        outerCar.add(outC);
        innerCar.add(innerC);
        innerCar.add(innerC);
        EventEntity event = new EventEntity();

        event.setId("event");
        event.setEventName("成都遭遇袭击");
        DispatchEntity dispatch = new DispatchEntity();
        dispatch.setEventEntity(event);
        dispatch.setRequestId("requestID");
        dispatch.setcNum(9);
        dispatch.setpNum(5);

        ModelAndView mv = new ModelAndView("eventReturn");
        List<PersonnelEntity> ps = personnelService.getAllListBySearch("", "", "", 1, 5);
        mv.addObject("outerPerson", ps);
        mv.addObject("innerPerson", ps);
        mv.addObject("outerCar", outerCar);
        mv.addObject("innerCar", innerCar);
        mv.addObject("thing", event);
        mv.addObject("dispatch", dispatch);
        return mv;
    }

    @RequestMapping("eventReturn.do")
    public String doEventReturn(String eventId, String persons, String cars, RedirectAttributes reAttr) {

        System.out.println(eventId);
        System.out.println(persons);
        System.out.println(cars);
        reAttr.addAttribute("eventId", eventId);
        return "redirect:/eventReturn";
    }


}