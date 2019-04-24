package com.lovo.hospital.controller;

import com.lovo.hospital.bean.PaginationBean;
import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.entity.CarLogEntity;
import com.lovo.hospital.entity.DispatchEntity;
import com.lovo.hospital.entity.EventEntity;
import com.lovo.hospital.entity.PersonnelLogEntity;
import com.lovo.hospital.service.DispatchService;
import com.lovo.hospital.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private DispatchService dispatchService;

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


    @RequestMapping("eventInfo")
    public ModelAndView eventInfo(String eid){
        List<CarLogEntity> eventInfoCarLog = eventService.getEventInfoCar(eid);
        List<PersonnelLogEntity> eventInfoPersonnelLog = eventService.getEventInfoPersonnel(eid);
        EventEntity eventInfo = eventService.getEventInfo(eid);
        ModelAndView modelAndView=new ModelAndView("eventDetails");
        modelAndView.addObject("eventInfo",eventInfo);
        modelAndView.addObject("eventCarLogList",eventInfoCarLog);
        modelAndView.addObject("eventPersonnelLogList",eventInfoPersonnelLog);
        return modelAndView;
    }


    @RequestMapping("eventOperation/{id}")
    public ModelAndView main(@PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("dispatchId", id);
        DispatchEntity dispatchEntity = dispatchService.getDispatchById(id);
        mv.addObject("personNum", dispatchEntity.getpNum());
        mv.addObject("carNum", dispatchEntity.getcNum());
        EventEntity eventEntity = dispatchEntity.getEventEntity();
        mv.addObject("thingName", eventEntity.getEventName());
        mv.addObject("thingAddress", eventEntity.getAlarmAddress());
        mv.setViewName("event");
        return mv;
    }

    @RequestMapping("{id}/getDispatchByEventId")
    @ResponseBody
    public List<DispatchEntity> getDispatchByEventId(@PathVariable("id") String id){

        List<DispatchEntity> dispatchEntities = new ArrayList<>();

        dispatchEntities =  dispatchService.getDispatchByEventId(id,0);

        return dispatchEntities;
    }

}
