# KTP CRUD Application

Aplikasi manajemen data KTP (Kartu Tanda Penduduk) Indonesia berbasis web full-stack dengan Spring Boot REST API backend dan frontend HTML/CSS/JavaScript/jQuery.

---

## Technology Stack

| Layer      | Technology                           |
|------------|--------------------------------------|
| Backend    | Spring Boot 3.2.5, Java 17           |
| Database   | MySQL 8.x                            |
| ORM        | Spring Data JPA / Hibernate 6.4      |
| Validasi   | Spring Boot Validation (Jakarta)     |
| Frontend   | HTML5, CSS3, JavaScript ES6          |
| AJAX       | jQuery 3.7.1                         |
| Build Tool | Apache Maven 3.6+                    |
| Env Config | spring-dotenv 4.0.0                  |

---

## Prerequisites

- **Java 17+** — [Download](https://adoptium.net/)
- **Apache Maven 3.6+** — [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.x** — [Download](https://dev.mysql.com/downloads/)

---

## Database Setup

1. Start MySQL server.
2. Buat schema database:

```sql
CREATE DATABASE IF NOT EXISTS spring;
```

3. Konfigurasi koneksi ada di file `.env` di root project:

```env
DB_URL=jdbc:mysql://localhost:3306/spring
DB_USERNAME=root
DB_PASSWORD=your_password
```

> Tabel `ktp` akan dibuat otomatis oleh Hibernate saat aplikasi pertama kali dijalankan (`ddl-auto=update`).

---

## Cara Menjalankan

```bash
mvn spring-boot:run
```

Kemudian buka browser dan akses:

```
http://localhost:8080
```

---

## Project Structure

```
praktikum2/
├── .env                                        # Kredensial database (tidak di-push ke Git)
├── .env.example                                # Template .env untuk referensi
├── pom.xml                                     # Maven dependencies & build config
├── README.md
└── src/
    ├── main/
    │   ├── java/com/deploy/praktikum2/
    │   │   ├── Praktikum2Application.java       # Entry point @SpringBootApplication
    │   │   ├── controller/
    │   │   │   └── KtpController.java           # REST Controller — 5 endpoint CRUD
    │   │   ├── dto/
    │   │   │   ├── KtpRequest.java              # Request payload (dengan validasi)
    │   │   │   └── KtpResponse.java             # Response payload
    │   │   ├── exception/
    │   │   │   ├── DuplicateResourceException.java   # Exception 409 Conflict
    │   │   │   ├── GlobalExceptionHandler.java       # @RestControllerAdvice
    │   │   │   └── ResourceNotFoundException.java    # Exception 404 Not Found
    │   │   ├── mapper/
    │   │   │   └── KtpMapper.java               # Konversi Entity ↔ DTO
    │   │   ├── model/
    │   │   │   └── Ktp.java                     # JPA Entity → tabel `ktp`
    │   │   ├── repository/
    │   │   │   └── KtpRepository.java           # Spring Data JPA Repository
    │   │   ├── service/
    │   │   │   ├── KtpService.java              # Service interface
    │   │   │   └── impl/
    │   │   │       └── KtpServiceImpl.java      # Implementasi business logic
    │   │   └── util/
    │   │       └── ApiResponse.java             # Generic response wrapper
    │   └── resources/
    │       ├── application.properties           # Konfigurasi server & database
    │       └── static/
    │           ├── index.html                   # Halaman utama SPA
    │           ├── style.css                    # Styling
    │           └── script.js                    # jQuery AJAX CRUD
    └── test/
        └── java/com/deploy/praktikum2/
            └── Praktikum2ApplicationTests.java
```

---

## Struktur Database

### Tabel: `ktp`

| Kolom           | Tipe MySQL      | Constraint                  |
|-----------------|-----------------|-----------------------------|
| `id`            | INT             | PRIMARY KEY, AUTO_INCREMENT |
| `nomor_ktp`     | VARCHAR(255)    | NOT NULL, UNIQUE            |
| `nama_lengkap`  | VARCHAR(255)    | NOT NULL                    |
| `alamat`        | VARCHAR(255)    | NOT NULL                    |
| `tanggal_lahir` | DATE            | NOT NULL                    |
| `jenis_kelamin` | VARCHAR(255)    | NOT NULL                    |

---

## API Documentation

### Base URL

```
http://localhost:8080/ktp
```

### Format Response Umum (`ApiResponse<T>`)

Semua endpoint mengembalikan response dengan struktur berikut:

```json
{
  "status":  <integer: HTTP status code>,
  "message": <string: pesan deskriptif>,
  "data":    <object | array | null>
}
```

---

### 1. Tambah Data KTP

```
POST /ktp
```

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "nomorKtp":    "3201010101010001",
  "namaLengkap": "Budi Santoso",
  "alamat":      "Jl. Merdeka No. 1, Jakarta",
  "tanggalLahir": "1990-01-15",
  "jenisKelamin": "Laki-laki"
}
```

| Field          | Tipe     | Wajib | Keterangan                        |
|----------------|----------|-------|-----------------------------------|
| `nomorKtp`     | String   | Ya    | Harus unik, tidak boleh kosong    |
| `namaLengkap`  | String   | Ya    | Tidak boleh kosong                |
| `alamat`       | String   | Ya    | Tidak boleh kosong                |
| `tanggalLahir` | String   | Ya    | Format: `yyyy-MM-dd`              |
| `jenisKelamin` | String   | Ya    | `"Laki-laki"` atau `"Perempuan"`  |

**Response Sukses — `201 Created`:**
```json
{
  "status": 201,
  "message": "Data KTP berhasil ditambahkan",
  "data": {
    "id": 1,
    "nomorKtp": "3201010101010001",
    "namaLengkap": "Budi Santoso",
    "alamat": "Jl. Merdeka No. 1, Jakarta",
    "tanggalLahir": "1990-01-15",
    "jenisKelamin": "Laki-laki"
  }
}
```

**Response Error — `409 Conflict` (nomorKtp sudah terdaftar):**
```json
{
  "status": 409,
  "message": "KTP dengan nomorKtp '3201010101010001' sudah ada",
  "data": null
}
```

**Response Error — `400 Bad Request` (validasi gagal):**
```json
{
  "status": 400,
  "message": "Validasi gagal. Periksa kembali data yang diinputkan.",
  "data": {
    "nomorKtp": "Nomor KTP tidak boleh kosong",
    "namaLengkap": "Nama lengkap tidak boleh kosong",
    "tanggalLahir": "Tanggal lahir tidak boleh kosong"
  }
}
```

---

### 2. Ambil Semua Data KTP

```
GET /ktp
```

**Request Body:** Tidak ada

**Response Sukses — `200 OK` (data tersedia):**
```json
{
  "status": 200,
  "message": "Data KTP berhasil diambil",
  "data": [
    {
      "id": 1,
      "nomorKtp": "3201010101010001",
      "namaLengkap": "Budi Santoso",
      "alamat": "Jl. Merdeka No. 1, Jakarta",
      "tanggalLahir": "1990-01-15",
      "jenisKelamin": "Laki-laki"
    },
    {
      "id": 2,
      "nomorKtp": "3274015502980002",
      "namaLengkap": "Siti Rahayu",
      "alamat": "Jl. Sudirman No. 45, Bandung",
      "tanggalLahir": "1998-02-15",
      "jenisKelamin": "Perempuan"
    }
  ]
}
```

**Response Sukses — `200 OK` (data kosong):**
```json
{
  "status": 200,
  "message": "Data KTP berhasil diambil",
  "data": []
}
```

---

### 3. Ambil Data KTP Berdasarkan ID

```
GET /ktp/{id}
```

**Path Variable:**

| Parameter | Tipe    | Keterangan        |
|-----------|---------|-------------------|
| `id`      | Integer | ID record KTP     |

**Request Body:** Tidak ada

**Response Sukses — `200 OK`:**
```json
{
  "status": 200,
  "message": "Data KTP berhasil diambil",
  "data": {
    "id": 1,
    "nomorKtp": "3201010101010001",
    "namaLengkap": "Budi Santoso",
    "alamat": "Jl. Merdeka No. 1, Jakarta",
    "tanggalLahir": "1990-01-15",
    "jenisKelamin": "Laki-laki"
  }
}
```

**Response Error — `404 Not Found` (ID tidak ditemukan):**
```json
{
  "status": 404,
  "message": "KTP dengan id 99 tidak ditemukan",
  "data": null
}
```

---

### 4. Perbarui Data KTP

```
PUT /ktp/{id}
```

**Path Variable:**

| Parameter | Tipe    | Keterangan              |
|-----------|---------|-------------------------|
| `id`      | Integer | ID record yang diperbarui|

**Headers:**
```
Content-Type: application/json
```

**Request Body:** (sama seperti POST)
```json
{
  "nomorKtp":    "3201010101010001",
  "namaLengkap": "Budi Santoso Updated",
  "alamat":      "Jl. Kebangsaan No. 10, Jakarta",
  "tanggalLahir": "1990-01-15",
  "jenisKelamin": "Laki-laki"
}
```

**Response Sukses — `200 OK`:**
```json
{
  "status": 200,
  "message": "Data KTP berhasil diperbarui",
  "data": {
    "id": 1,
    "nomorKtp": "3201010101010001",
    "namaLengkap": "Budi Santoso Updated",
    "alamat": "Jl. Kebangsaan No. 10, Jakarta",
    "tanggalLahir": "1990-01-15",
    "jenisKelamin": "Laki-laki"
  }
}
```

**Response Error — `404 Not Found` (ID tidak ditemukan):**
```json
{
  "status": 404,
  "message": "KTP dengan id 99 tidak ditemukan",
  "data": null
}
```

**Response Error — `409 Conflict` (nomorKtp milik data lain):**
```json
{
  "status": 409,
  "message": "KTP dengan nomorKtp '3274015502980002' sudah ada",
  "data": null
}
```

**Response Error — `400 Bad Request` (validasi gagal):**
```json
{
  "status": 400,
  "message": "Validasi gagal. Periksa kembali data yang diinputkan.",
  "data": {
    "alamat": "Alamat tidak boleh kosong"
  }
}
```

---

### 5. Hapus Data KTP

```
DELETE /ktp/{id}
```

**Path Variable:**

| Parameter | Tipe    | Keterangan          |
|-----------|---------|---------------------|
| `id`      | Integer | ID record yang dihapus|

**Request Body:** Tidak ada

**Response Sukses — `200 OK`:**
```json
{
  "status": 200,
  "message": "Data KTP berhasil dihapus",
  "data": null
}
```

**Response Error — `404 Not Found` (ID tidak ditemukan):**
```json
{
  "status": 404,
  "message": "KTP dengan id 99 tidak ditemukan",
  "data": null
}
```

---

### Ringkasan Semua Endpoint

| Method   | Endpoint      | Deskripsi                    | HTTP Sukses | HTTP Error         |
|----------|---------------|------------------------------|-------------|--------------------|
| `POST`   | `/ktp`        | Tambah data KTP baru         | `201`       | `400`, `409`       |
| `GET`    | `/ktp`        | Ambil semua data KTP         | `200`       | `500`              |
| `GET`    | `/ktp/{id}`   | Ambil data KTP berdasarkan ID| `200`       | `404`              |
| `PUT`    | `/ktp/{id}`   | Perbarui data KTP            | `200`       | `400`, `404`, `409`|
| `DELETE` | `/ktp/{id}`   | Hapus data KTP               | `200`       | `404`              |

### Daftar HTTP Status Code

| Status | Arti                  | Kondisi                                              |
|--------|-----------------------|------------------------------------------------------|
| `200`  | OK                    | GET / PUT / DELETE berhasil                          |
| `201`  | Created               | POST berhasil membuat data baru                      |
| `400`  | Bad Request           | Field wajib kosong atau format tidak valid           |
| `404`  | Not Found             | ID yang diminta tidak ditemukan di database          |
| `409`  | Conflict              | nomorKtp sudah terdaftar di data lain                |
| `500`  | Internal Server Error | Kesalahan tak terduga di sisi server                 |

---

## Screenshots

---

### 1. Tampilan Utama — Form Input KTP

> Tampilan halaman utama dengan form input data KTP dalam kondisi kosong (mode tambah data baru).

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/d50ea295-f0ef-43e7-8026-7fc74ad5fcb0" />

---

### 2. Tabel Data KTP

> Tabel yang menampilkan seluruh data KTP yang sudah tersimpan di database, lengkap dengan tombol Edit dan Hapus.

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/80ec549b-3756-443b-b811-a0e944f9abb6" />

---

### 3. Notifikasi Sukses

> Notifikasi hijau yang muncul di bagian atas halaman setelah operasi tambah, edit, atau hapus data berhasil.

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/8508e372-9329-442d-9710-b52b22a0e1f6" />

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/51b37d1b-324d-4bfb-bdc2-2f2860e67147" />

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/7128c643-7211-4a46-9062-3f4652f30343" />

---

### 4. Notifikasi Error

> Notifikasi merah yang muncul ketika terjadi kesalahan, misalnya Nomor KTP sudah terdaftar atau field wajib dikosongkan.

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/24b988e2-6ba7-4d6c-abe4-185f4af981ff" />

---

### 5. Mode Edit

> Form dalam kondisi mode edit: field terisi data yang dipilih, tombol submit berubah menjadi "Update", dan tombol "Batal" muncul.

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/80dbc002-9c1b-483c-873c-8dd8310cd959" />

---

### 6. Konfirmasi Hapus

> Dialog konfirmasi browser yang muncul sebelum data dihapus untuk mencegah penghapusan tidak disengaja.

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/32ff4c7e-5a50-4495-b5c4-243c31da7a2f" />

---
