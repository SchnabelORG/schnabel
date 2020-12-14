package com.schnabel.schnabel.pswusagereport.controller;

import java.util.List;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;
import com.schnabel.schnabel.pswusagereport.service.IUsageReportNotificationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsageReportNotificationController
{

    private IUsageReportNotificationService _notificationService;

    public UsageReportNotificationController(IUsageReportNotificationService notificationService)
    {
        _notificationService = notificationService;
    }

    @PostMapping("/pswapi/usagereportnotifications")
    public ResponseEntity<String> add(@RequestBody UsageReportNotification notification)
    {
        boolean success = _notificationService.add(notification);
        return success ?
            ResponseEntity.ok("Added")
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/pswapi/usagereportnotifications/{id}")
    public ResponseEntity<UsageReportNotification> get(@PathVariable int id)
    {
        UsageReportNotification notification = _notificationService.get(id);
        return notification == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(notification);
    }

    @GetMapping("/pswapi/usagereportnotifications")
    public ResponseEntity<List<UsageReportNotification>> getAll()
    {
        return ResponseEntity.ok(_notificationService.getAll());
    }

}
