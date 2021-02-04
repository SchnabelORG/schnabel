package com.schnabel.schnabel.misc.exceptions;

public class PatientAlreadyExistsException extends Exception {
    public PatientAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
