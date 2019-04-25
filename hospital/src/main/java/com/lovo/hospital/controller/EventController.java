package com.lovo.hospital.controller;

import com.alibaba.fastjson.JSONObject;
import com.lovo.hospital.bean.PaginationBean;
import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.dto.EventSinkDto;
import com.lovo.hospital.entity.*;
import com.lovo.hospital.service.DispatchService;
import com.lovo.hospital.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket")
@Controller
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private DispatchService dispatchService;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<EventController> webSocketSet = new CopyOnWriteArraySet<EventController>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        /*addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            System.out.println( "IO异常" );
        }*/
    }

    /**
     * MQ接手数据的方法
     *
     * @param message
     */
    @JmsListener(destination = "queue")
    public void receiveQueue(String message) {
//        System.out.println("监听queue=============监听queue");
        //收到数据，保存数据库
        //将接收到的json转换为对象
        EventSinkDto eventSinkDto = JSONObject.parseObject(message, EventSinkDto.class);
        //判断是否是续派
        String eventId = eventSinkDto.getId();
        boolean haveEvent = eventService.isHaveEventById(eventId);
        if (haveEvent) {
            //事件存在，只需要保存派遣
            DispatchEntity dispatchEntity = new DispatchEntity();
            dispatchEntity.setcNum(eventSinkDto.getCNum());
            dispatchEntity.setpNum(eventSinkDto.getPNum());
            dispatchEntity.setRequestId(eventSinkDto.getRequestId());
            EventEntity eventEntity = new EventEntity();
            eventEntity.setId(eventSinkDto.getId());
            dispatchEntity.setEventEntity(eventEntity);

            dispatchService.saveDispatchEntity(dispatchEntity);

        } else {
            //事件不存在，保存事件和派遣
            EventEntity eventEntity = new EventEntity();
            eventEntity.setId(eventSinkDto.getId());

            eventEntity.setEventProceed(0);
            eventEntity.setEventName(eventSinkDto.getEventName());
            eventEntity.setAlarmAddress(eventSinkDto.getAlarmAddress());
            eventEntity.setAlarmPerson(eventSinkDto.getAlarmPerson());
            eventEntity.setAlarmTel(eventSinkDto.getAlarmTel());
            eventEntity.setEventArea(eventSinkDto.getEventArea());
            eventEntity.setEventTime(eventSinkDto.getEventTime());
            eventEntity.setEventType(eventSinkDto.getEventType());

            DispatchEntity dispatchEntity = new DispatchEntity();
            dispatchEntity.setcNum(eventSinkDto.getCNum());
            dispatchEntity.setpNum(eventSinkDto.getPNum());
            dispatchEntity.setRequestId(eventSinkDto.getRequestId());
            dispatchEntity.setEventEntity(eventEntity);

            eventService.saveEvent(eventEntity);
            dispatchService.saveDispatchEntity(dispatchEntity);
        }
        //把数据通过ws传到页面上
        /*URI requestURI = session.getRequestURI();
        System.out.println(requestURI);*/

        this.onMessage(message, session);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        for (EventController item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除


        System.out.println("有一连接关闭！当前在线人数为");
    }
    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println( "发生错误" );
        error.printStackTrace();
    }
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
    public PaginationBean<EventRecordListDto> eventRecordList(Integer currPage, String eventName, String startTime, String endTime) {
        Integer pageSize = 10;

        List<EventRecordListDto> eventRecordListDtos = eventService.getEventDtoList(currPage, eventName, startTime, endTime);

        Integer totalPage = eventService.getTotalPage(pageSize, eventName, startTime, endTime);

        PaginationBean<EventRecordListDto> paginationBean = new PaginationBean<>();

        paginationBean.setCurrPage(currPage);

        paginationBean.setDataList(eventRecordListDtos);

        paginationBean.setTotalPage(totalPage);

        return paginationBean;
    }


    @RequestMapping("eventInfo")
    public ModelAndView eventInfo(String eid) {
        List<CarLogEntity> eventInfoCarLog = eventService.getEventInfoCar(eid);
        List<PersonnelLogEntity> eventInfoPersonnelLog = eventService.getEventInfoPersonnel(eid);
        EventEntity eventInfo = eventService.getEventInfo(eid);
        ModelAndView modelAndView = new ModelAndView("eventDetails");
        if (eventInfo.getPersonnelEntity() != null) {
            modelAndView.addObject("eventInfoPerson", eventInfo.getPersonnelEntity());
        } else {
            PersonnelEntity personnelEntity = new PersonnelEntity();
            personnelEntity.setName("未指派负责人");
            personnelEntity.setTel("未指派负责人");
            modelAndView.addObject("eventInfoPerson", personnelEntity);
        }

        modelAndView.addObject("eventInfo", eventInfo);
        modelAndView.addObject("eventCarLogList", eventInfoCarLog);
        modelAndView.addObject("eventPersonnelLogList", eventInfoPersonnelLog);
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
    public List<DispatchEntity> getDispatchByEventId(@PathVariable("id") String id) {

        List<DispatchEntity> dispatchEntities = new ArrayList<>();

        dispatchEntities = dispatchService.getDispatchByEventId(id, 0);

        return dispatchEntities;
    }

}
