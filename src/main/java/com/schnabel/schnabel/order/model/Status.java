package com.schnabel.schnabel.order.model;

public enum Status
{
    REJECTED("R"), ACCEPTED("A"), PENDING("P");

    private String status;

    private Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
