package com.schnabel.schnabel.drugs.model.enums;

public enum DrugType {
    ANTIPYRETIC,
    ANALGESIC,
    ANTIMALARIAL,
    ANTIBIOTICS,
    ANTISEPTIC,
    MOOD_STABILIZERS,
    HORMONE_REPLACEMENTS,
    ORAL_CONTRACEPTIVES,
    STIMULANTS,
    TRANQUILIZERS,
    STATINS,
    OTHER;
    public static DrugType convert(String drugType){
        if(drugType.equals("ANTIPYRETIC")){
            return ANTIPYRETIC;
        } else if(drugType.equals("ANALGESIC")) {
            return ANALGESIC;
        } else if(drugType.equals("ANTIMALARIAL")) {
            return ANTIMALARIAL;
        } else if(drugType.equals("ANTIBIOTICS")) {
            return ANTIBIOTICS;
        } else if(drugType.equals("ANTISEPTIC")) {
            return ANTISEPTIC;
        } else if(drugType.equals("MOOD_STABILIZERS")) {
            return MOOD_STABILIZERS;
        } else if(drugType.equals("HORMONE_REPLACEMENTS")) {
            return HORMONE_REPLACEMENTS;
        } else if(drugType.equals("ORAL_CONTRACEPTIVES")) {
            return ORAL_CONTRACEPTIVES;
        } else if(drugType.equals("STIMULANTS")) {
            return STIMULANTS;
        } else if(drugType.equals("TRANQUILIZERS")) {
            return TRANQUILIZERS;
        } else if(drugType.equals("STATINS")) {
            return STATINS;
        } else {
            return OTHER;
        }
    }
}
