package com.schnabel.schnabel.grades.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.users.dto.PatientDTO;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("complaint")
@Relation(collectionRelation = "complaints")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ComplaintDTO extends RepresentationModel<ComplaintDTO> {
    private Long id;
    private String complaintText;
    private String Response;
}
