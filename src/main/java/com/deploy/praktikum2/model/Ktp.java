package com.deploy.praktikum2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ktp")
public class Ktp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nomorKtp;

    @Column(nullable = false)
    private String namaLengkap;

    @Column(nullable = false)
    private String alamat;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate tanggalLahir;

    @Column(nullable = false)
    private String jenisKelamin;

    public Ktp() {}

    public Ktp(Integer id, String nomorKtp, String namaLengkap, String alamat, LocalDate tanggalLahir, String jenisKelamin) {
        this.id = id;
        this.nomorKtp = nomorKtp;
        this.namaLengkap = namaLengkap;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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
