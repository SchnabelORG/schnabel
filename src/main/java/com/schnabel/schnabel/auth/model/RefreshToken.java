package com.schnabel.schnabel.auth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Refresh token used in refreshing a jwt session
 */
@Entity
@Table(name = "refresh_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RefreshToken implements IIdentifiable<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String token;

    public RefreshToken(String email, String token) {
        this.email = email;
        this.token = token;
    }

}
