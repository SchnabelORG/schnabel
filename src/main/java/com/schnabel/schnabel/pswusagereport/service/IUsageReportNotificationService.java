package com.schnabel.schnabel.pswusagereport.service;

import java.util.List;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;

public interface IUsageReportNotificationService
{
    boolean add(UsageReportNotification notification);
    boolean remove(int id);
    boolean update(UsageReportNotification notification);
    UsageReportNotification get(int id);
    List<UsageReportNotification> getAll();
}
