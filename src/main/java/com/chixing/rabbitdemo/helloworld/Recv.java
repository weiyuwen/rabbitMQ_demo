package com.chixing.rabbitdemo.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {
//    打开一个连接和一个通道，并声明要从中使用的队列。
//    请注意，这与将发布发送到的队列匹配。
    private final static String QUEUE_NAME="hello";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory1=new ConnectionFactory();
        factory1.setHost("localhost");
        Connection connection=factory1.newConnection();
        Channel channel1=connection.createChannel();
        channel1.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println("waiting for message.");
//        我们希望进程在消费者异步侦听消息到达时保持活动状态。   我们将告诉服务器从队列中向我们传递消息。
//        由于它将异步地向我们推送消息，因此我们以对象的形式提供回调，该回调将缓冲消息，直到我们准备好使用它们。
//        这就是回传子类的作用。
        DeliverCallback deliverCallback1=(consumerTag,delivery)->{
          String message =new String(delivery.getBody(),"UTF-8");
            System.out.println("Received message : "+message);
        };
        channel1.basicConsume(QUEUE_NAME,true,deliverCallback1,consumerTag->{});
    }
}
