package com.schnabel.schnabel.misc;

import java.beans.PropertyEditorSupport;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * One init controller binder to rule them all
 */
@ControllerAdvice
public class InitControllerAdvice {
    
    @InitBinder
    public void initBinder(final WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String timestamp) throws IllegalArgumentException {
                setValue(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(timestamp)), TimeZone.getDefault().toZoneId()));
            }
        });
    }
}
