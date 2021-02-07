package com.schnabel.schnabel.users.model;


import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "systemadmins")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SystemAdmin implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Setter(AccessLevel.PROTECTED)
    @Column(nullable = false)
    private String username;
    @Setter(AccessLevel.PROTECTED)
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Embedded
    private Address address;

    @Override
    public Long getId() {
        return this.id;
    }
}
