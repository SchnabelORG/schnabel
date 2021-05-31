package com.schnabel.schnabel.misc.model;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Period 
{
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;


    public boolean isPeriodValid()
    {
        return this.endTime.isAfter(this.startTime);
    }

    public long getDurationMinutes() {
        return Duration.between(this.startTime, this.endTime).toMinutes();
    }
}
