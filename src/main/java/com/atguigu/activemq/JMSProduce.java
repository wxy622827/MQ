package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wxy
 * @create 2019-12-25 19:41
 */
public class JMSProduce {

    public static final String MQ_URL = "tcp://192.168.112.128:61616";
    public static final String QUEUE_NAME = "queue0805";
    public static void main(String[] args) throws JMSException {
        //获得activeMQConnectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //通过activeMQConnectionFactory获得connection
        Connection connection = activeMQConnectionFactory.createConnection();
        //启动连接准备建立会话
        connection.start();
        //创建session
        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        //创建消息队列   目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        //创建producer    生产的消息放在哪里
        MessageProducer producer = session.createProducer(queue);
        //messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //生产内容
        for (int i = 1; i <=6; i++) {
            TextMessage textMessage = session.createTextMessage("mag--"+i);
            producer.send(textMessage);
        }
        //关闭  自底向上
        producer.close();
        session.commit();
        session.close();
        connection.close();
        System.out.println("******msg send ok,O(∩_∩)O");

//         try{
//                 session.commit();
//                 }catch (Exception e){
//                     e.printStackTrace();
//                 }finally {
//                    session.rollback();
//                 }
    }
}
