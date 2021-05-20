package com.schnabel.schnabel.pswusagereport.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NotificationDTO {
    private String filename;
    private String endpoint;
    private String message;

    @Override
    public String toString()
    {
        return "Endpoint: " + this.endpoint + " Message: " + this.message + " Filename: " + this.filename;
    }
}
