package com.deploy.praktikum2.controller;

import com.deploy.praktikum2.dto.KtpRequest;
import com.deploy.praktikum2.dto.KtpResponse;
import com.deploy.praktikum2.service.KtpService;
import com.deploy.praktikum2.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ktp")
@CrossOrigin(origins = "*")
public class KtpController {

    private final KtpService ktpService;

    public KtpController(KtpService ktpService) {
        this.ktpService = ktpService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<KtpResponse>> createKtp(@Valid @RequestBody KtpRequest request) {
        KtpResponse data = ktpService.createKtp(request);
        ApiResponse<KtpResponse> response = ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Data KTP berhasil ditambahkan",
                data
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<KtpResponse>>> getAllKtp() {
        List<KtpResponse> data = ktpService.getAllKtp();
        ApiResponse<List<KtpResponse>> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Data KTP berhasil diambil",
                data
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KtpResponse>> getKtpById(@PathVariable Integer id) {
        KtpResponse data = ktpService.getKtpById(id);
        ApiResponse<KtpResponse> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Data KTP berhasil diambil",
                data
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KtpResponse>> updateKtp(
            @PathVariable Integer id,
            @Valid @RequestBody KtpRequest request) {
        KtpResponse data = ktpService.updateKtp(id, request);
        ApiResponse<KtpResponse> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Data KTP berhasil diperbarui",
                data
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteKtp(@PathVariable Integer id) {
        ktpService.deleteKtp(id);
        ApiResponse<Object> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Data KTP berhasil dihapus",
                null
        );
        return ResponseEntity.ok(response);
    }
}
