package com.deploy.praktikum2.mapper;

import com.deploy.praktikum2.dto.KtpRequest;
import com.deploy.praktikum2.dto.KtpResponse;
import com.deploy.praktikum2.model.Ktp;

import java.util.List;
import java.util.stream.Collectors;

public class KtpMapper {

    public static Ktp toEntity(KtpRequest dto) {
        Ktp ktp = new Ktp();
        ktp.setNomorKtp(dto.getNomorKtp());
        ktp.setNamaLengkap(dto.getNamaLengkap());
        ktp.setAlamat(dto.getAlamat());
        ktp.setTanggalLahir(dto.getTanggalLahir());
        ktp.setJenisKelamin(dto.getJenisKelamin());
        return ktp;
    }

    public static KtpResponse toResponse(Ktp entity) {
        KtpResponse response = new KtpResponse();
        response.setId(entity.getId());
        response.setNomorKtp(entity.getNomorKtp());
        response.setNamaLengkap(entity.getNamaLengkap());
        response.setAlamat(entity.getAlamat());
        response.setTanggalLahir(entity.getTanggalLahir());
        response.setJenisKelamin(entity.getJenisKelamin());
        return response;
    }

    public static List<KtpResponse> toResponseList(List<Ktp> entities) {
        return entities.stream()
                .map(KtpMapper::toResponse)
                .collect(Collectors.toList());
    }
}
