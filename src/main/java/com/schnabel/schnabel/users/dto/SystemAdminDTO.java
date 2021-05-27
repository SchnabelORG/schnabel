package com.schnabel.schnabel.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.misc.model.Address;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("systemadmin")
@Relation(collectionRelation = "systemadmins")
@EqualsAndHashCode(callSuper = false)
public class SystemAdminDTO extends RepresentationModel<SystemAdminDTO> {
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Address address;
}
