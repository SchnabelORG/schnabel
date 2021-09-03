package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.pharmacies.dto.ERecipeDTO;
import com.schnabel.schnabel.pharmacies.dto.UploadFileResponse;
import com.schnabel.schnabel.pharmacies.service.FileStorageService;
import com.schnabel.schnabel.pharmacies.service.IERecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/erecipe")
public class ERecipeController {

    private final IERecipeService service;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    public ERecipeController(IERecipeService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping("/uploadFile")
    public ResponseEntity<ERecipeDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String qrText = service.scanQR(fileName);
        if(qrText.isBlank() || qrText.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.readQR(qrText));
    }

}
