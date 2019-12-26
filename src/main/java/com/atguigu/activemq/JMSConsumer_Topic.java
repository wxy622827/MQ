package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @auther zzyy
 * @create 2019-10-23 14:47
 */
public class JMSConsumer_Topic
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
        //5 通过session创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);


        /*
        异步非阻塞方式(监听器onMessage())
        订阅者或接收者通过MessageConsumer的setMessageListener(MessageListener listener)注册一个消息监听器，
        当消息到达之后，系统自动调用监听器MessageListener的onMessage(Message message)方法。*/
        messageConsumer.setMessageListener(message -> {
            if(message != null && message instanceof TextMessage)
            {
                TextMessage textMessage = (TextMessage)message;
                try
                {
                    System.out.println("***收到topic："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
