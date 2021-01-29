package com.schnabel.schnabel.pswusagereport.controller;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;
import com.schnabel.schnabel.pswusagereport.service.IUsageReportNotificationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UsageReportNotificationController
{
    private IUsageReportNotificationService notificationService;

    public UsageReportNotificationController(IUsageReportNotificationService notificationService)
    {
        this.notificationService = notificationService;
    }

    @PostMapping("/pswapi/usagereportnotifications")
    public ResponseEntity<String> add(@RequestBody UsageReportNotification notification)
    {
        boolean success = notificationService.add(notification);
        return success ?
            ResponseEntity.ok("Added")
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/pswapi/usagereportnotifications/{filename}")
    public ResponseEntity<UsageReportNotification> get(@PathVariable String filename)
    {
        UsageReportNotification notification = notificationService.get(filename);
        return notification == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(notification);
    }

    @GetMapping("/pswapi/usagereportnotifications")
    public ResponseEntity<Iterable<UsageReportNotification>> getAll()
    {
        return ResponseEntity.ok(notificationService.getAll());
    }

}
