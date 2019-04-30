package com.lovo.police_office.controller;


import com.lovo.police_office.dto.EventSinkDto;
import com.lovo.police_office.util.MQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Date;

@Controller
public class TestController {
    @Autowired
    private MQUtil mqUtil;

    @RequestMapping("/test2")
    public ModelAndView socket() {
        ModelAndView mav = new ModelAndView("/test2");
        return mav;
    }

    @RequestMapping("/mqtest")
    @ResponseBody
    public String mqtest() {

        for (Integer i = 1; i <=21; i++) {


            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            EventSinkDto eventSinkDto = new EventSinkDto(i.toString(), "MQ测试事件", "火灾", "锦江区", timestamp, "MQ测试报警人", "10010", "测试地址", i.toString() ,3, 1);


            mqUtil.testSendMQ(eventSinkDto);
        }
        return "";
    }
}
