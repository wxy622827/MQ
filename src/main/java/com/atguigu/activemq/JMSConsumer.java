package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wxy
 * @create 2019-12-25 20:18
 */
public class JMSConsumer {

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
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //5 获得目的地，此例是队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //获得消费者   消费内容   从哪里消费
        MessageConsumer consumer = session.createConsumer(queue);
        //消费者监听
        consumer.setMessageListener(message -> {
                if(message !=null && message instanceof  TextMessage){
                TextMessage textMessage= (TextMessage) message;

                 try{

                     System.out.println("meaasgaeConsumer:"+textMessage.getText() );
                     textMessage.acknowledge();

                 }catch (Exception e){
                             e.printStackTrace();
                         }
            }
        });

        //session.commit();

        System.out.println("******msgconsumer ok,O(∩_∩)O");
       /* while (true) {
           // TextMessage textMessage = (TextMessage) consumer.receive();
            TextMessage textMessage = (TextMessage) consumer.receive(4000);
            if (null != textMessage) {
                System.out.println("messageConsumer:" + textMessage.getText());
            } else {
                break;
            }
        }

        consumer.close();
        session.close();
        connection.close();
        System.out.println("******msgconsumer ok,O(∩_∩)O");*/
    }
}
