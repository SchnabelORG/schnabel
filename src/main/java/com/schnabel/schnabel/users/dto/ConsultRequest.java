package com.schnabel.schnabel.users.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schnabel.schnabel.misc.UnixToLocalDateTimeConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultRequest {
    
    private Long pharmacistId;
    @JsonDeserialize(using = UnixToLocalDateTimeConverter.class)
    private LocalDateTime start;
}
