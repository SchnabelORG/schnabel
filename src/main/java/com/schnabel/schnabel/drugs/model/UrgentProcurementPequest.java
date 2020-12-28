package com.schnabel.schnabel.drugs.model;

public class UrgentProcurementPequest {
    private String medicationName;
    private String medicationDosage;
    private int medicationQuantity;

    public UrgentProcurementPequest() {
    }

    public UrgentProcurementPequest(String name, String dosage, int quant) {
        medicationName = name;
        medicationDosage = dosage;
        medicationQuantity = quant;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getMedicationDosage() {
        return medicationDosage;
    }

    public void setMedicationDosage(String medicationDosage) {
        this.medicationDosage = medicationDosage;
    }

    public int getQuantity() {
        return medicationQuantity;
    }

    public void setQuantity(int quantity) {
        this.medicationQuantity = quantity;
    }
}
