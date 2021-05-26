package com.schnabel.schnabel.grades.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("complaintresposne")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ComplaintRespsonseDTO {
    private Long id;
    private String response;
}
