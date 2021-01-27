package com.schnabel.schnabel.pharmacies.service;

import java.util.List;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;

public interface IPharmacyService
{
    boolean add(Pharmacy pharmacy);
    boolean remove(int id);
    boolean update(Pharmacy pharmacy);
    Pharmacy get(int id);
    List<Pharmacy> getAll();
}
