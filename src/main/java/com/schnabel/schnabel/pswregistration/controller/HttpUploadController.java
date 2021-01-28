package com.schnabel.schnabel.pswregistration.controller;

import com.schnabel.schnabel.pswregistration.amazon.AmazonClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class HttpUploadController
{
    private AmazonClient amazonClient;

    @Autowired
    public HttpUploadController(AmazonClient amazonClient)
    {
        this.amazonClient = amazonClient;
    }

    @PostMapping("pswupload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file)
    {
        return this.amazonClient.uploadFile(file);
    }

}
