package com.chixing.rabbitdemo.publishandsubscribe.fanout;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 向多个消费者传递信息。此模式称为“发布/订阅”。
 *
 * 为了说明这种模式，我们将构建一个简单的日志记录系统。
 * 它将由两个程序组成 - 第一个程序将发出日志消息，第二个程序将接收并打印它们。
 *
 * 在我们的日志记录系统中，接收程序的每个运行副本都将获得消息。
 * 这样，我们将能够运行一个接收器并将日志定向到磁盘;同时，我们将能够运行另一个接收器并在屏幕上看到日志。
 *
 * 从本质上讲，已发布的日志消息将广播到所有接收方。
 */
public class EmitLog {
    private static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection=factory.newConnection();
             Channel channel2=connection.createChannel()){
            channel2.exchangeDeclare(EXCHANGE_NAME,"fanout");

            String message="fanout message okokok";
            channel2.basicPublish(EXCHANGE_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Send: "+message);
        }
    }
}
