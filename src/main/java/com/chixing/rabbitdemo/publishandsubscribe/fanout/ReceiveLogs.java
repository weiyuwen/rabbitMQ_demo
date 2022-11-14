package com.chixing.rabbitdemo.publishandsubscribe.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.ibatis.logging.stdout.StdOutImpl;


public class ReceiveLogs {
    private static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection2=factory.newConnection();
        Channel channel2=connection2.createChannel();

        channel2.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String queueName=channel2.queueDeclare().getQueue();

//        交换机和队列绑定
        channel2.queueBind(queueName,EXCHANGE_NAME,"");

        System.out.println("waiting for messages");

        DeliverCallback deliverCallback=(consumerTag,delivery)->{
            String message=new String(delivery.getBody(),"UTF-8");
            System.out.println("Receive："+message);
        };
        channel2.basicConsume(queueName,true,deliverCallback,consumerTag->{});
    }
}
