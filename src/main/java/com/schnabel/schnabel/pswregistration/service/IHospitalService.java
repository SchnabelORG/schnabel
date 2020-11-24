package com.schnabel.schnabel.pswregistration.service;

import java.util.List;

import com.schnabel.schnabel.pswregistration.model.Hospital;

public interface IHospitalService {
    boolean add(Hospital hospital);
    boolean remove(String apiKey);
    boolean update(Hospital hospital);
    Hospital get(String apiKey);
    List<Hospital> getAll();

}