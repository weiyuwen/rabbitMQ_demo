package com.chixing.rabbitdemo.workqueues;



import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

//Set up the class and name the queue:
public class NewTask {
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
//                这是因为我们已经定义了一个名为 hello 的队列，该队列不持久。
//                RabbitMQ 不允许您使用不同的参数重新定义现有队列，并且会向任何尝试执行此操作的程序返回错误。
//                但是有一个快速的解决方法 - 让我们声明一个具有不同名称的队列


//                允许从命令行发送任意消息。该程序会将任务安排到我们的工作队列中:
//                String message=String.join("hellohello11111",args);
//                jion（拼接符号，需要拼接的信息1，拼接信息2....）:拼接
                String message1="1111111111";//需要发送的信息

//                           空字符串这里表示的是一个默认的名称，在rabbitMQ中表示的是AMQP default
                channel1.basicPublish("",QUEUE_NAME,null,message1.getBytes()); //对管道进行初始化
                System.out.println("had send new task message : "+message1);
        }

    }
}
