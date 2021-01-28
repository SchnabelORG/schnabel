package com.schnabel.schnabel.rabbitmq;

import com.schnabel.schnabel.specialoffer.model.SpecialOffer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty("${custom.rabbitmq.enabled}")
public class RabbitMQSender
{

    private final AmqpTemplate _amqpTemplate;

    @Value("${custom.rabbitmq.exchange}")
    private String _exchange;
    @Value("${custom.rabbitmq.specialOffersKey}")
    private String _routingKey;

    @Autowired
    public RabbitMQSender(AmqpTemplate rabbitTemplate)
    {
        this._amqpTemplate = rabbitTemplate;
    }
    
    // TODO(Jovan): Parameterize for exchange and routing key??
    public void send(SpecialOffer message)
    {
        _amqpTemplate.convertAndSend(_exchange, _routingKey, message);
    }
}
