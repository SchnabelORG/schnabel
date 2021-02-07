package com.schnabel.schnabel.drugs.service;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.misc.interfaces.ICrudService;

/**
 * Drug service interface
 */
public interface IDrugService extends ICrudService<Drug, Long>
{
    boolean getByName(String name);
}
