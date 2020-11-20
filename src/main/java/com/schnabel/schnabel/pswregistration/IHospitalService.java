package com.schnabel.schnabel.pswregistration;

import java.util.List;

public interface IHospitalService {
    boolean add(Hospital hospital);
    boolean remove(String apiKey);
    boolean update(Hospital hospital);
    Hospital get(String apiKey);
    List<Hospital> getAll();

}