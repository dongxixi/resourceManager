package com.lovo.hospital.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class MQUtil {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMQ(Object data){
        jmsTemplate.send("sb", new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                String strJson = JSONObject.toJSONString(data);
                textMessage.setText(strJson);
                return textMessage;
            }
        });
    }


    public void testSendMQ(Object data){
        jmsTemplate.send("testSend", new MessageCreator() {
            @Override
            public TextMessage createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                String strJson = JSONObject.toJSONString(data);
                textMessage.setText(strJson);
                return textMessage;
            }
        });
    }
}
