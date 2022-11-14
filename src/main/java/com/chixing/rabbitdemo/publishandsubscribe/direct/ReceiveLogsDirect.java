package com.chixing.rabbitdemo.publishandsubscribe.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;


public class ReceiveLogsDirect {
    private static final String EXCHANGE_NAME="direct_logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        String queueName=channel.queueDeclare().getQueue();
        Map<String,Object> map=new HashMap<>();
        map.put("aaa",1);
        map.put("bbb",2);
        map.put("ccc",3);

        if (map.size()<1){
            System.out.println("Receive message: error");
            System.exit(1);
        }
//        交换机和队列绑定
        for (Map.Entry m:map.entrySet()
             ) {
            channel.queueBind(queueName, EXCHANGE_NAME,m.getKey().toString());
        }


        System.out.println("waiting for messages");

        DeliverCallback deliverCallback=(consumerTag,delivery)->{
            String message=new String(delivery.getBody(),"UTF-8");
            System.out.println("Receive："+delivery.getEnvelope().getRoutingKey()+" : "+message);
        };
        channel.basicConsume(queueName,true,deliverCallback,consumerTag->{});
    }
}
