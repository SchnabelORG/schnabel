package com.schnabel.schnabel.pswregistration.rabbitmq;

import com.schnabel.schnabel.pswregistration.model.SpecialOffer;
import com.schnabel.schnabel.pswregistration.service.ISpecialOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final RabbitMQSender sender;
    @Autowired
    private ISpecialOfferService specialOfferService;

    @Autowired
    public ScheduledTasks(RabbitMQSender sender)
    {
        this.sender = sender;
    }

    @Scheduled(fixedRate = 3000)
    public void sendMessage()
    {
        SpecialOffer specialOffer = specialOfferService.get(1);
        sender.send(specialOffer);
        System.out.println("Special offer sent: " + specialOffer.toString());
    }
}
