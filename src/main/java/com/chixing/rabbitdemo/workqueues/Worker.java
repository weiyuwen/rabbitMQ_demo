package com.chixing.rabbitdemo.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 在工作workder之间分配任务
 */

public class Worker{
//    打开一个连接和一个通道，并声明要从中使用的队列。
//    请注意，这与将发布发送到的队列匹配。
    private final static String QUEUE_NAME="hello";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory1=new ConnectionFactory(); //新建连接工厂
        factory1.setHost("localhost");  //ip地址

        Connection connection=factory1.newConnection(); //连接
        Channel channel1=connection.createChannel(); //管道

        channel1.queueDeclare(QUEUE_NAME,false,false,false,null); //管道设置
        System.out.println("waiting for message.");

        channel1.basicQos(1);
//        它需要为消息正文中的每个点伪造一秒钟的工作。它将处理传递的消息并执行任务


        DeliverCallback deliverCallback1=(consumerTag,delivery)->{
          String message =new String(delivery.getBody(),"UTF-8");
            System.out.println("Received message : "+message);
            try {
                try {
                    dowork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                System.out.println("Done");
                channel1.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        };
        boolean autoack=false;
        channel1.basicConsume(QUEUE_NAME,autoack,deliverCallback1,consumerTag->{});
    }
    private static void dowork(String task) throws  InterruptedException{
        for (char ch:task.toCharArray()){
            if (ch=='.') {
                Thread.sleep(1000);
            }
        }
    }
}
