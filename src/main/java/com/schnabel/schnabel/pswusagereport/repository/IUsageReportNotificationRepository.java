package com.schnabel.schnabel.pswusagereport.repository;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsageReportNotificationRepository extends CrudRepository<UsageReportNotification, Integer>
{
    
}
