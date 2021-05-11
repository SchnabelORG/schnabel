package com.schnabel.schnabel.pswusagereport.repository;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsageReportNotificationRepository extends JpaRepository<UsageReportNotification, String>
{
    
}
