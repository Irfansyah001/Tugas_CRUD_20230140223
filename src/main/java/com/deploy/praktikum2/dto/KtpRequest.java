package com.deploy.praktikum2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class KtpRequest {

    @NotBlank(message = "Nomor KTP tidak boleh kosong")
    private String nomorKtp;

    @NotBlank(message = "Nama lengkap tidak boleh kosong")
    private String namaLengkap;

    @NotBlank(message = "Alamat tidak boleh kosong")
    private String alamat;

    @NotNull(message = "Tanggal lahir tidak boleh kosong")
    private LocalDate tanggalLahir;

    @NotBlank(message = "Jenis kelamin tidak boleh kosong")
    private String jenisKelamin;

    public KtpRequest() {}

    public KtpRequest(String nomorKtp, String namaLengkap, String alamat, LocalDate tanggalLahir, String jenisKelamin) {
        this.nomorKtp = nomorKtp;
        this.namaLengkap = namaLengkap;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
    }

    public String getNomorKtp() { return nomorKtp; }
    public void setNomorKtp(String nomorKtp) { this.nomorKtp = nomorKtp; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
}
