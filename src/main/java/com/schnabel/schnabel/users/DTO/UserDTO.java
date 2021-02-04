package com.schnabel.schnabel.users.DTO;

import com.schnabel.schnabel.misc.model.Address;
import lombok.*;

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
