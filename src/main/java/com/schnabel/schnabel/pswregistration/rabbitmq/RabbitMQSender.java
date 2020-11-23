package com.schnabel.schnabel.pswregistration.rabbitmq;

import com.schnabel.schnabel.pswregistration.model.SpecialOffer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSender(AmqpTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("psw-exchange")
    String exchange;
    @Value("psw-key")
    private String routingKey;
    
    public void send(SpecialOffer message)
    {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
