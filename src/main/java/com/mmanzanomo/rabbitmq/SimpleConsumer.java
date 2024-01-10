package com.mmanzanomo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class SimpleConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // Open connection and stabish channel
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        String queueName = "first-queue";
        channel.queueDeclare(queueName, false, false, false, null);
        // Create subscrition to queue using
        channel.basicConsume(queueName, true,
                (consumerTag, message) -> {
                    String messageBody = new String(message.getBody(), Charset.defaultCharset());

                    System.out.println("Message: " + messageBody);
                    System.out.println("Exchange: " + message.getEnvelope().getExchange());
                    System.out.println("Routing key: " + message.getEnvelope().getRoutingKey());
                    System.out.println("Delivery tag: " + message.getEnvelope().getDeliveryTag());
                },
                consumerTag -> {
                    System.out.println("Consumer: " + consumerTag + " cancelled.");
                });

    }
}
