package com.chixing.rabbitdemo.publishandsubscribe.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogs2 {
    private static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection2 = factory.newConnection();
        Channel channel2 = connection2.createChannel();

        channel2.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel2.queueDeclare().getQueue();
        channel2.queueBind(queueName, EXCHANGE_NAME, "");
//-----------------------------------------------

//        这一块？？？？？？？
//        Connection connection3 = factory.newConnection();
        Channel channel3 = connection2.createChannel();

        channel3.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName3 = channel3.queueDeclare().getQueue();
        channel3.queueBind(queueName3, EXCHANGE_NAME, "");

//        -------------------------------------------
        System.out.println("waiting for messages");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Receive：" + message);
        };
//        ------------------------------------
        channel2.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
//        --------------------------------------
        channel3.basicConsume(queueName3, true, deliverCallback, consumerTag -> {});
    }
}
