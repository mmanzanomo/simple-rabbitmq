package com.mmanzanomo.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EventsProducer {
    private static final String EXCHANGE = "events";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory connectionFactory = new ConnectionFactory();

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT);

            int count = 0;
            while(true) {
                String message = "Event n: " + count++;
                channel.basicPublish(EXCHANGE, "", null, message.getBytes());
                Thread.sleep(1000);
            }
        }

    }
}
