package com.lovo.hospital.controller;

import com.lovo.hospital.bean.PaginationBean;
import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.entity.EventEntity;
import com.lovo.hospital.entity.PersonnelEntity;
import com.lovo.hospital.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;


@Controller
public class EventController {
    @Autowired
    private EventService eventService;

    @RequestMapping("eventAll")
    public String eventAll() {
        return "eventAll";
    }

    @RequestMapping("eventDetails")
    public String event() {
        return "eventDetails";
    }

    @RequestMapping("eventRecordList")
    @ResponseBody
    public PaginationBean<EventRecordListDto> eventRecordList(Integer currPage, String eventName,String startTime,String endTime){
        Integer pageSize = 10;

        List<EventRecordListDto> eventRecordListDtos = eventService.getEventDtoList(currPage,eventName,startTime,endTime);

        Integer totalPage = eventService.getTotalPage(pageSize, eventName,startTime,endTime);

        PaginationBean<EventRecordListDto> paginationBean = new PaginationBean<>();

        paginationBean.setCurrPage(currPage);

        paginationBean.setDataList(eventRecordListDtos);

        paginationBean.setTotalPage(totalPage);

        return paginationBean;
    }







}
