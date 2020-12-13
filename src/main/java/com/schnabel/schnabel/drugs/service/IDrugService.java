package com.schnabel.schnabel.drugs.service;

import java.util.List;

import com.schnabel.schnabel.drugs.model.Drug;

public interface IDrugService
{
    boolean add(Drug drug);
    boolean remove(int id);
    boolean update(Drug drug);
    Drug get(int id);
    List<Drug> getAll();
}
