package com.lovo.hospital.client;

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
@RequestMapping("/api")
public class ActiveMqClient {

    @Autowired
    private JmsTemplate jmsTemplate;

    @RequestMapping("/send")
    public void send(){
        for (int i = 0; i < 10; i++){
            jmsTemplate.send("queue", new MessageCreator() {
                @Override
                public TextMessage createMessage(Session session) throws JMSException {
                    TextMessage textMessage = session.createTextMessage();
                    textMessage.setText("消息来啦");
                    return textMessage;
                }
            });
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
