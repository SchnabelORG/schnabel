package com.schnabel.schnabel.pharmacies.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.schnabel.schnabel.misc.LocalDateAdapter;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.dto.ERecipeDTO;
import com.schnabel.schnabel.pharmacies.model.ERecipe;
import com.schnabel.schnabel.pharmacies.repository.IERecipeRepository;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ERecipeService extends JpaService<ERecipe, Long, IERecipeRepository> implements IERecipeService{

    private final Gson gson;

    public ERecipeService(IERecipeRepository repository) {
        super(repository);
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe()).create();
    }

    @Override
    public String scanQR(String filePath) {
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<EncodeHintType,
                                ErrorCorrectionLevel>();

        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.L);
        try {
            BinaryBitmap binaryBitmap
                    = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(
                            ImageIO.read(
                                    new FileInputStream(filePath)))));

            Result result
                    = new MultiFormatReader().decode(binaryBitmap);

            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ERecipeDTO readQR(String qrText) {
        ERecipeDTO eRecipe;
        eRecipe = gson.fromJson(qrText, ERecipeDTO.class);
        return eRecipe;
    }


}
