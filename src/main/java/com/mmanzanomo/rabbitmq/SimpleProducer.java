package com.mmanzanomo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        String message = "Hello everyone!";

        // Open AMQ connection
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
            String queueName = "first-queue";
            // create queue
            channel.queueDeclare(queueName, false, false, false, null);
            // send message to exchange
            channel.basicPublish("", queueName, null, message.getBytes());

            // try-with-resources close resources at finish:
            //channel.close();
            //connection.close();
        }


    }
}
