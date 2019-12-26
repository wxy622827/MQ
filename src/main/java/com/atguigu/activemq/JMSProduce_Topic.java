package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @auther zzyy
 * @create 2019-10-23 14:44
 */
public class JMSProduce_Topic
{
    public static final String MQ_URL = "tcp://192.168.112.128:61616";
    public static final String TOPIC_NAME = "topic0805";

    public static void main(String[] args) throws JMSException
    {
        //1 先通过ActiveMQConnectionFactory获得mq工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //2 获得连接connection
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3 通过connection获得Session
        //3.1 第一个参数叫事务，默认用false
        //3.2 第二个参数叫签收，默认自动签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4 通过session创建目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        //5 通过session创建消息生产者
        MessageProducer messageProducer = session.createProducer(topic);

        for (int i = 1; i <=6; i++)
        {
            //6 编写发送的消息（提问卡msg）
            TextMessage textMessage = session.createTextMessage("---topic提问msg: " + i);
            //7 messageProducer开始发送消息到MQ
            messageProducer.send(textMessage);
        }
        //8 释放资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("-----topic MessageProducer send is ok,O(∩_∩)O");



    }
}
