package com.schnabel.schnabel.registration.dto;

import com.schnabel.schnabel.misc.model.Address;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String surname;
    private String password;
    private String matchingPassword;
    private String email;
    private Address address;
    private LocalDate dateOfBirth;

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", matchingPassword='" + matchingPassword + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
