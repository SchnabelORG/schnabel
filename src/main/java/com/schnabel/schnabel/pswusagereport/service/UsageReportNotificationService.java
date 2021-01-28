package com.schnabel.schnabel.pswusagereport.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;
import com.schnabel.schnabel.pswusagereport.repository.IUsageReportNotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsageReportNotificationService extends CrudService<UsageReportNotification, String>
    implements IUsageReportNotificationService
{
    @Autowired
    public UsageReportNotificationService(IUsageReportNotificationRepository notificationRepository)
    {
        super(notificationRepository);
    }
}
