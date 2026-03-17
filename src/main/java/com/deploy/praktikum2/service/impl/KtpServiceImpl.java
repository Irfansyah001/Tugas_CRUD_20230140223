package com.deploy.praktikum2.service.impl;

import com.deploy.praktikum2.dto.KtpRequest;
import com.deploy.praktikum2.dto.KtpResponse;
import com.deploy.praktikum2.exception.DuplicateResourceException;
import com.deploy.praktikum2.exception.ResourceNotFoundException;
import com.deploy.praktikum2.mapper.KtpMapper;
import com.deploy.praktikum2.model.Ktp;
import com.deploy.praktikum2.repository.KtpRepository;
import com.deploy.praktikum2.service.KtpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KtpServiceImpl implements KtpService {

    private final KtpRepository ktpRepository;

    public KtpServiceImpl(KtpRepository ktpRepository) {
        this.ktpRepository = ktpRepository;
    }

    @Override
    public KtpResponse createKtp(KtpRequest request) {
        if (ktpRepository.existsByNomorKtp(request.getNomorKtp())) {
            throw new DuplicateResourceException("KTP", "nomorKtp", request.getNomorKtp());
        }
        Ktp ktp = KtpMapper.toEntity(request);
        Ktp savedKtp = ktpRepository.save(ktp);
        return KtpMapper.toResponse(savedKtp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KtpResponse> getAllKtp() {
        List<Ktp> ktpList = ktpRepository.findAll();
        return KtpMapper.toResponseList(ktpList);
    }

    @Override
    @Transactional(readOnly = true)
    public KtpResponse getKtpById(Integer id) {
        Ktp ktp = ktpRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KTP", id));
        return KtpMapper.toResponse(ktp);
    }

    @Override
    public KtpResponse updateKtp(Integer id, KtpRequest request) {
        Ktp existingKtp = ktpRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KTP", id));

        ktpRepository.findByNomorKtp(request.getNomorKtp()).ifPresent(found -> {
            if (!found.getId().equals(id)) {
                throw new DuplicateResourceException("KTP", "nomorKtp", request.getNomorKtp());
            }
        });

        existingKtp.setNomorKtp(request.getNomorKtp());
        existingKtp.setNamaLengkap(request.getNamaLengkap());
        existingKtp.setAlamat(request.getAlamat());
        existingKtp.setTanggalLahir(request.getTanggalLahir());
        existingKtp.setJenisKelamin(request.getJenisKelamin());

        Ktp updatedKtp = ktpRepository.save(existingKtp);
        return KtpMapper.toResponse(updatedKtp);
    }

    @Override
    public void deleteKtp(Integer id) {
        Ktp ktp = ktpRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KTP", id));
        ktpRepository.delete(ktp);
    }
}
