package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.users.controller.SystemAdminController;
import com.schnabel.schnabel.users.model.SystemAdmin;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class SystemAdminDTOAssembler extends RepresentationModelAssemblerSupport<SystemAdmin, SystemAdminDTO> {

    public SystemAdminDTOAssembler()
    {
        super(SystemAdminController.class, SystemAdminDTO.class);
    }

    @Override
    public SystemAdminDTO toModel(SystemAdmin entity) {
        SystemAdminDTO dto = instantiateModel(entity);
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setAddress(entity.getAddress());
        return dto;
    }
}
