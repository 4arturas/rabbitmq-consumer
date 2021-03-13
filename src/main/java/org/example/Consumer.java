package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer
{
    private final static String QUEUE_NAME = "hello";

    private static String RABBITQM_RELAY_HOST;
    private static int RABBITQM_RELAY_PORT;

    public static void main(String[] args) throws Exception
    {

        RABBITQM_RELAY_HOST = System.getenv("RABBITQM_RELAY_HOST");
        RABBITQM_RELAY_PORT = Integer.parseInt(System.getenv("RABBITQM_RELAY_PORT"));

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( RABBITQM_RELAY_HOST );
        factory.setPort( RABBITQM_RELAY_PORT );
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) ->
        {
            String message = new String(delivery.getBody(), "UTF-8");
            Integer nMessage = Integer.valueOf( message );
            System.out.println(" [x] Received '" + message + "' ThreadId: " + Thread.currentThread().getId() );
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
