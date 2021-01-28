package com.schnabel.schnabel.pswusagereport.service;

import java.util.List;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;
import com.schnabel.schnabel.pswusagereport.repository.IUsageReportNotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsageReportNotificationService implements IUsageReportNotificationService
{

    private IUsageReportNotificationRepository notificationRepository;

    @Autowired
    public UsageReportNotificationService(IUsageReportNotificationRepository notificationRepository)
    {
        this.notificationRepository = notificationRepository;
    }

	@Override
    public boolean add(UsageReportNotification notification)
    {
        if(get(notification.getFilename()) != null) return false;
        notificationRepository.save(notification);
        return true;
	}

	@Override
    public boolean remove(String filename)
    {
        UsageReportNotification notification = get(filename);
        if(notification == null) return false;
        notificationRepository.delete(notification);
        return true;
	}

	@Override
    public boolean update(UsageReportNotification notification)
    {
        if(get(notification.getFilename()) == null) return false;
        notificationRepository.save(notification);
        return true;
	}

	@Override
    public UsageReportNotification get(String filename)
    {
        return notificationRepository.findById(filename).orElse(null);
	}

	@Override
    public Iterable<UsageReportNotification> getAll()
    {
        return notificationRepository.findAll();
	}

}
