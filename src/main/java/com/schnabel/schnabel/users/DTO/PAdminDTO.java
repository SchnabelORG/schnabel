package com.schnabel.schnabel.users.DTO;

import com.schnabel.schnabel.misc.model.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PAdminDTO {
    private String name;
    private String surname;
    private String email;
    private Address address;
}
