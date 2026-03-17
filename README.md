# KTP CRUD Application

Aplikasi manajemen data KTP (Kartu Tanda Penduduk) Indonesia berbasis web dengan Spring Boot backend dan frontend HTML/CSS/JavaScript/jQuery.

---

## Technology Stack

| Layer     | Technology                          |
|-----------|-------------------------------------|
| Backend   | Spring Boot 3.2.5, Java 17          |
| Database  | MySQL 8.x                           |
| ORM       | Spring Data JPA / Hibernate         |
| Validation| Spring Boot Validation (Jakarta)    |
| Frontend  | HTML5, CSS3, JavaScript (ES6)       |
| AJAX      | jQuery 3.7.1                        |
| Build     | Apache Maven                        |

---

## Prerequisites

- **Java 17+** — [Download](https://adoptium.net/)
- **Apache Maven 3.6+** — [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.x** — [Download](https://dev.mysql.com/downloads/)

---

## Database Setup

1. Start your MySQL server.
2. Create the database schema:

```sql
CREATE DATABASE IF NOT EXISTS spring;
```

3. The application uses root user with an empty password by default. To change this, edit `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

> The `ktp` table will be created automatically by Hibernate on first run (`ddl-auto=update`).

---

## Running the Application

```bash
mvn spring-boot:run
```

Then open your browser and navigate to:

```
http://localhost:8080
```

---

## API Documentation

### Base URL: `http://localhost:8080/ktp`

| Method | Endpoint      | Description               | Request Body              | Response                        |
|--------|---------------|---------------------------|---------------------------|---------------------------------|
| POST   | `/ktp`        | Create a new KTP record   | `KtpRequest` (JSON)       | `ApiResponse<KtpResponse>` 201  |
| GET    | `/ktp`        | Get all KTP records       | —                         | `ApiResponse<List<KtpResponse>>` 200 |
| GET    | `/ktp/{id}`   | Get a KTP record by ID    | —                         | `ApiResponse<KtpResponse>` 200  |
| PUT    | `/ktp/{id}`   | Update a KTP record by ID | `KtpRequest` (JSON)       | `ApiResponse<KtpResponse>` 200  |
| DELETE | `/ktp/{id}`   | Delete a KTP record by ID | —                         | `ApiResponse<null>` 200         |

### KtpRequest Body (POST / PUT)

```json
{
  "nomorKtp": "3201010101010001",
  "namaLengkap": "Budi Santoso",
  "alamat": "Jl. Merdeka No. 1, Jakarta",
  "tanggalLahir": "1990-01-01",
  "jenisKelamin": "Laki-laki"
}
```

### KtpResponse

```json
{
  "status": 200,
  "message": "Data KTP berhasil diambil",
  "data": {
    "id": 1,
    "nomorKtp": "3201010101010001",
    "namaLengkap": "Budi Santoso",
    "alamat": "Jl. Merdeka No. 1, Jakarta",
    "tanggalLahir": "1990-01-01",
    "jenisKelamin": "Laki-laki"
  }
}
```

### Error Responses

| HTTP Status | Scenario                        |
|-------------|---------------------------------|
| 400         | Validation failed               |
| 404         | KTP record not found            |
| 409         | Duplicate nomorKtp              |
| 500         | Internal server error           |

---

## Project Structure

```
src/
├── main/
│   ├── java/com/example/ktpcrud/
│   │   ├── KtpCrudApplication.java       # Spring Boot entry point
│   │   ├── model/
│   │   │   └── Ktp.java                  # JPA Entity mapped to table `ktp`
│   │   ├── dto/
│   │   │   ├── KtpRequest.java           # Incoming request payload (with validation)
│   │   │   └── KtpResponse.java          # Outgoing response payload
│   │   ├── repository/
│   │   │   └── KtpRepository.java        # Spring Data JPA repository interface
│   │   ├── service/
│   │   │   ├── KtpService.java           # Service interface
│   │   │   └── impl/
│   │   │       └── KtpServiceImpl.java   # Business logic implementation
│   │   ├── mapper/
│   │   │   └── KtpMapper.java            # Manual mapper: Entity <-> DTO
│   │   ├── controller/
│   │   │   └── KtpController.java        # REST controller (5 endpoints)
│   │   ├── util/
│   │   │   └── ApiResponse.java          # Generic API response wrapper
│   │   └── exception/
│   │       ├── ResourceNotFoundException.java    # 404 exception
│   │       ├── DuplicateResourceException.java   # 409 exception
│   │       └── GlobalExceptionHandler.java       # @RestControllerAdvice handler
│   └── resources/
│       ├── application.properties        # Database & server config
│       └── static/
│           ├── index.html                # Single-page frontend UI
│           ├── style.css                 # Styling
│           └── script.js                 # jQuery AJAX CRUD logic
└── test/
    └── java/com/example/ktpcrud/
        └── KtpCrudApplicationTests.java  # Spring Boot context test
```

---

## Screenshots

### Form Input KTP
> _Screenshot: Form untuk menambahkan atau mengedit data KTP_

### Tabel Data KTP
> _Screenshot: Tabel yang menampilkan seluruh data KTP_

### Notifikasi Sukses
> _Screenshot: Notifikasi hijau yang muncul saat operasi berhasil_

### Notifikasi Error
> _Screenshot: Notifikasi merah yang muncul saat terjadi kesalahan_

### Edit Mode
> _Screenshot: Form dalam mode edit dengan tombol "Update" dan "Batal"_

### Konfirmasi Hapus
> _Screenshot: Dialog konfirmasi sebelum menghapus data_
