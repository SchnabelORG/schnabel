package com.schnabel.schnabel.email.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Activation implements IIdentifiable<String> {
    @Id
    private String id;
    private String email;

    public Activation(String email) {
        this.email = email;
        this.id = generateToken();
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
