package com.example.active.activemq;

import com.example.active.mysql.controller.NumController;
import com.example.active.mysql.dao.NumRepository;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Queue_Produce {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    // 消息队列名称
    public static final String QUEUE_NAME = "queue01";
    public void produce(List<String> strList) throws JMSException { //接下来添加形参来处理列表数据
        // 1. 创建工厂对象 (这个工厂对象连接到哪里？ACTIVEMQ_URL)
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2. 创建连接对象, 并启动访问
        Connection connection = factory.createConnection();
        connection.start();
        // 3. 通过连接对象创建session对象，第一个参数为是否开启事务，第二个参数为签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.创建目的地（队列或者主题）
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5. 创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
        // 6. 发送消息
        int i=0;
        NumController xx=new NumController();
        while(i<strList.size()){
            TextMessage textMessage = session.createTextMessage(strList.get(i));
            producer.send(textMessage);
            i++;
        }
        // 7. 关闭资源
        producer.close();
        session.close();
        connection.close();
        System.out.println("success.....");
    }
}
