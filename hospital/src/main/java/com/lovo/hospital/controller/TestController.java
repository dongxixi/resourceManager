package com.lovo.hospital.controller;

import com.alibaba.fastjson.JSONObject;
import com.lovo.hospital.dto.EventSinkDto;
import com.lovo.hospital.util.MQUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.sql.Timestamp;

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
            EventSinkDto eventSinkDto = new EventSinkDto(i.toString(), "MQ测试事件", "火灾", "锦江区", timestamp, "MQ测试报警人", "10010", "测试地址", "P001", 3, 1);

            String s = JSONObject.toJSONString(eventSinkDto);

            System.out.println(s);
            mqUtil.sendMQ(eventSinkDto);
        }
        return "";
    }
}
