package com.schnabel.schnabel.pswregistration;

import java.util.List;

public interface IHospitalService {
    boolean add(Hospital hospital);
    boolean remove(String name);
    boolean update(Hospital hospital);
    Hospital get(String name);
    List<Hospital> getAll();

}