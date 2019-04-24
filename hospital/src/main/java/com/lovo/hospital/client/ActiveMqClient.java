package com.lovo.hospital.client;

import com.lovo.hospital.util.MQUtil;
import org.apache.activemq.Message;
import org.apache.activemq.broker.region.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@RestController
@RequestMapping("api")
public class ActiveMqClient {
    @Autowired
    private MQUtil mqUtil;

    @RequestMapping("/send")
    public void send(){
        for (int i = 0; i < 10; i++){
            mqUtil.sendMQ("啦啦啦" + i);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
