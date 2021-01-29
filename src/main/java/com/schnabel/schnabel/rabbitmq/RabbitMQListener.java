package com.schnabel.schnabel.rabbitmq;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;
import com.schnabel.schnabel.pswusagereport.service.IUsageReportNotificationService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty("${custom.rabbitmq.enabled}")
public class RabbitMQListener
{

    // TODO(Jovan): Remove to decouple
    private IUsageReportNotificationService notificationService;

    @Autowired
    public RabbitMQListener(IUsageReportNotificationService notificationService)
    {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${custom.rabbitmq.usageReportsQueue}")
    public void consumeMessage(UsageReportNotification message)
    {
        System.out.println("Received: " + message.toString());
        this.notificationService.add(message);
	}

}
