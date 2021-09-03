package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.dto.ERecipeDTO;
import com.schnabel.schnabel.pharmacies.model.ERecipe;

public interface IERecipeService extends IJpaService<ERecipe, Long> {

    String scanQR(String filePath);
    ERecipeDTO readQR(String qrText);


}
