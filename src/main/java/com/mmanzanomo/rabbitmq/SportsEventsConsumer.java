package com.mmanzanomo.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class SportsEventsConsumer {
    private static final String EXCHANGE = "sports-events";
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();

        String routingkey = "es.#";

        channel.queueBind(queueName, EXCHANGE, routingkey);
        // Create subscrition to queue using
        channel.basicConsume(queueName, true,
                (consumerTag, message) -> {
                    String messageBody = new String(message.getBody(), Charset.defaultCharset());

                    System.out.println("Message: " + messageBody);
                    System.out.println(" - Routing key: " + message.getEnvelope().getRoutingKey());
                },
                consumerTag -> {
                    System.out.println("Consumer: " + consumerTag + " cancelled.");
                });
    }
}
