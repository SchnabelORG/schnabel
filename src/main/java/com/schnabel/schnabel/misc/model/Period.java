package com.schnabel.schnabel.misc.model;

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

    /**
     * Check whether endTime is before startTime
     * @return
     */
    public boolean isPeriodValid()
    {
        return this.endTime.isAfter(this.startTime);
    }

    /**
     * Checks whether <b>time</b> falls in Period
     * @param time Time to check
     * @return true if it's between else false
     */
    public boolean isBetween(LocalDateTime time)
    {
        return time.isAfter(this.startTime)
            && time.isBefore(this.endTime);
    }

    /**
     * Checks whether the <b>period</b> overlaps
     * @param period Period to check
     * @return true if it overlaps else false
     */
    public boolean isOverlapping(Period period)
    {
        return isBetween(period.getStartTime())
            || isBetween(period.getEndTime());
    }
}
