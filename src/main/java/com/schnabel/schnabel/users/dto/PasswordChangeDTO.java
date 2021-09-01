package com.schnabel.schnabel.users.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PasswordChangeDTO {
    @NotBlank
    @Size(min = 8)
    private String newPassword;

    @NotBlank
    @Size(min = 8)
    private String repeatedPassword;

    public boolean isValid(){
        if(newPassword.equals(repeatedPassword)){
            return true;
        }
        return false;
    }
}
