package com.deploy.praktikum2.service;

import com.deploy.praktikum2.dto.KtpRequest;
import com.deploy.praktikum2.dto.KtpResponse;

import java.util.List;

public interface KtpService {

    KtpResponse createKtp(KtpRequest request);

    List<KtpResponse> getAllKtp();

    KtpResponse getKtpById(Integer id);

    KtpResponse updateKtp(Integer id, KtpRequest request);

    void deleteKtp(Integer id);
}
