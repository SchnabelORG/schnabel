package com.schnabel.schnabel.pswusagereport.service;

import java.util.List;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;
import com.schnabel.schnabel.pswusagereport.repository.IUsageReportNotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsageReportNotificationService implements IUsageReportNotificationService
{

    private IUsageReportNotificationRepository _notificationRepository;

    @Autowired
    public UsageReportNotificationService(IUsageReportNotificationRepository notificationRepository)
    {
        _notificationRepository = notificationRepository;
    }

	@Override
    public boolean add(UsageReportNotification notification)
    {
        if(get(notification.getId()) != null) return false;
        _notificationRepository.save(notification);
        return true;
	}

	@Override
    public boolean remove(int id)
    {
        UsageReportNotification notification = get(id);
        if(notification == null) return false;
        _notificationRepository.delete(notification);
        return true;
	}

	@Override
    public boolean update(UsageReportNotification notification)
    {
        if(get(notification.getId()) == null) return false;
        _notificationRepository.save(notification);
        return true;
	}

	@Override
    public UsageReportNotification get(int id)
    {
        return _notificationRepository.findById(id).orElse(null);
	}

	@Override
    public List<UsageReportNotification> getAll()
    {
        return (List<UsageReportNotification>) _notificationRepository.findAll();
	}

}
