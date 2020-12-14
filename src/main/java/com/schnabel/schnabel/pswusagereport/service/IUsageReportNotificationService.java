package com.schnabel.schnabel.pswusagereport.service;

import java.util.List;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;

public interface IUsageReportNotificationService
{
    boolean add(UsageReportNotification notification);
    boolean remove(String filename);
    boolean update(UsageReportNotification notification);
    UsageReportNotification get(String filename);
    List<UsageReportNotification> getAll();
}
