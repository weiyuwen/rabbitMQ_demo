package com.chixing.rabbitdemo.helloworld;

//In Send.java, we need some classes imported:
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

//Set up the class and name the queue:
public class Send {
    private final static String QUEUE_NAME="hello";

    public static void main(String[] args) throws Exception{
//        然后我们可以创建一个与服务器的连接：
        ConnectionFactory factory=new ConnectionFactory();
//      在这里，我们连接到本地计算机上的 RabbitMQ 节点 - 因此是本地主机。
//      如果我们想连接到另一台机器上的节点，我们只需在此处指定其主机名或IP地址即可。
        factory.setHost("localhost");

        try (Connection connection=factory.newConnection();
            Channel channel1=connection.createChannel()){
//                                  队列名,是否持久化,是否独有,是否删除,map<>对象
                channel1.queueDeclare(QUEUE_NAME,false,false,false,null);
                String message="Hello World"; //需要发送的信息
//                         空字符串这里表示的是一个默认的名称，在rabbitMQ中表示的是AMQP default
                channel1.basicPublish("",QUEUE_NAME,null,message.getBytes());
                System.out.println("had send message : "+message);
        }

    }
}
