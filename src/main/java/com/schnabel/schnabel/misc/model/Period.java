package com.schnabel.schnabel.misc.model;

import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class Period 
{
    private LocalDate startTime;
    private LocalDate endTime;

    public Period()
    {
        this.startTime = LocalDate.of(2020, 07, 01);
        this.endTime = LocalDate.of(2021, 07, 01);
    }

    public Period(LocalDate startTime, LocalDate endTime)
    {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isPeriodValid()
    {
        return this.endTime.isAfter(this.startTime);
    }
}
