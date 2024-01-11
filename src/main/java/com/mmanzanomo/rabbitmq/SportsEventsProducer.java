package com.mmanzanomo.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class SportsEventsProducer {
    private static final String EXCHANGE = "sports-events";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory connectionFactory = new ConnectionFactory();

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);

            List<String> countries = Arrays.asList("es", "fr", "usa");
            List<String> sports = Arrays.asList("football", "tennis", "baseball");
            List<String> eventTypes = Arrays.asList("live", "news");

            int count = 0;
            while(true) {
                shuffle(countries, sports, eventTypes);
                String country = countries.get(0);
                String sport = sports.get(0);
                String eventType = eventTypes.get(0);

                String routingKey = country + "." + sport + "." + eventType;

                String message = "Event n_" + count++;
                System.out.println(message + ": " + routingKey);
                channel.basicPublish(EXCHANGE, routingKey, null, message.getBytes());
                Thread.sleep(1000);
            }
        }

    }

    private static void shuffle(List<String> countries, List<String> sports, List<String> eventType) {
        Collections.shuffle(countries);
        Collections.shuffle(sports);
        Collections.shuffle(eventType);
    }

}
