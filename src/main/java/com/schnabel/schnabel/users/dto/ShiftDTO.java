package com.schnabel.schnabel.users.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Shift DTO for JSON representation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("shift")
@Relation(collectionRelation = "shifts")
@JsonInclude(Include.NON_NULL)
public class ShiftDTO extends RepresentationModel<ShiftDTO>
{
    private LocalTime startTime;
    private LocalTime endTime;
}
