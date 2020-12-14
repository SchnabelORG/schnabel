package com.schnabel.schnabel.rabbitmq;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;
import com.schnabel.schnabel.pswusagereport.service.IUsageReportNotificationService;

import org.jboss.jandex.TypeTarget.Usage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener
{

    // TODO(Jovan): Remove to decouple
    private IUsageReportNotificationService _notificationService;

    @Autowired
    public RabbitMQListener(IUsageReportNotificationService notificationService)
    {
        _notificationService = notificationService;
    }

    @RabbitListener(queues = "${custom.rabbitmq.usageReportsQueue}")
    public void consumeMessage(UsageReportNotification message)
    {
        System.out.println("Received: " + message.toString());
        _notificationService.add(message);
	}

}
