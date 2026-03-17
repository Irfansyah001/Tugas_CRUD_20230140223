const API_BASE_URL = 'http://localhost:8080/ktp';

$(document).ready(function () {
    loadKtpData();

    // Form submit handler
    $('#ktpForm').on('submit', function (e) {
        e.preventDefault();
        saveKtp();
    });

    // Cancel button handler
    $('#cancelBtn').on('click', function () {
        resetForm();
    });

    // Edit button (event delegation)
    $('#ktpTableBody').on('click', '.btn-edit', function () {
        const id = $(this).data('id');
        loadKtpForEdit(id);
    });

    // Delete button (event delegation)
    $('#ktpTableBody').on('click', '.btn-delete', function () {
        const id = $(this).data('id');
        confirmDelete(id);
    });
});

// ===== Load & Display Data =====
function loadKtpData() {
    $.ajax({
        url: API_BASE_URL,
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            renderTable(response.data);
        },
        error: function (xhr) {
            const msg = parseError(xhr, 'Gagal memuat data KTP.');
            showNotification(msg, 'error');
            $('#ktpTableBody').html('<tr><td colspan="7" class="empty-row">Gagal memuat data.</td></tr>');
        }
    });
}

function renderTable(data) {
    const tbody = $('#ktpTableBody');
    tbody.empty();

    if (!data || data.length === 0) {
        tbody.html('<tr><td colspan="7" class="empty-row">Belum ada data KTP.</td></tr>');
        return;
    }

    $.each(data, function (index, ktp) {
        const badgeClass = ktp.jenisKelamin === 'Laki-laki' ? 'badge-laki' : 'badge-perempuan';
        const row = `
            <tr>
                <td>${index + 1}</td>
                <td>${escapeHtml(ktp.nomorKtp)}</td>
                <td>${escapeHtml(ktp.namaLengkap)}</td>
                <td>${escapeHtml(ktp.alamat)}</td>
                <td>${formatDate(ktp.tanggalLahir)}</td>
                <td><span class="badge ${badgeClass}">${escapeHtml(ktp.jenisKelamin)}</span></td>
                <td>
                    <button class="btn-edit" data-id="${ktp.id}">Edit</button>
                    <button class="btn-delete" data-id="${ktp.id}">Hapus</button>
                </td>
            </tr>
        `;
        tbody.append(row);
    });
}

// ===== Save (Create or Update) =====
function saveKtp() {
    const id = $('#ktpId').val();
    const payload = {
        nomorKtp: $('#nomorKtp').val().trim(),
        namaLengkap: $('#namaLengkap').val().trim(),
        alamat: $('#alamat').val().trim(),
        tanggalLahir: $('#tanggalLahir').val(),
        jenisKelamin: $('#jenisKelamin').val()
    };

    const isUpdate = id !== '';
    const url = isUpdate ? `${API_BASE_URL}/${id}` : API_BASE_URL;
    const method = isUpdate ? 'PUT' : 'POST';

    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(payload),
        success: function (response) {
            const msg = isUpdate ? 'Data KTP berhasil diperbarui.' : 'Data KTP berhasil ditambahkan.';
            showNotification(msg, 'success');
            resetForm();
            loadKtpData();
        },
        error: function (xhr) {
            const msg = parseError(xhr, 'Gagal menyimpan data KTP.');
            showNotification(msg, 'error');
        }
    });
}

// ===== Load Data for Edit =====
function loadKtpForEdit(id) {
    $.ajax({
        url: `${API_BASE_URL}/${id}`,
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            const ktp = response.data;
            $('#ktpId').val(ktp.id);
            $('#nomorKtp').val(ktp.nomorKtp);
            $('#namaLengkap').val(ktp.namaLengkap);
            $('#alamat').val(ktp.alamat);
            $('#tanggalLahir').val(ktp.tanggalLahir); // ISO format yyyy-MM-dd
            $('#jenisKelamin').val(ktp.jenisKelamin);

            $('#submitBtnText').text('Update');
            $('#cancelBtn').show();

            // Scroll to form
            $('html, body').animate({ scrollTop: $('.form-card').offset().top - 20 }, 400);
        },
        error: function (xhr) {
            const msg = parseError(xhr, 'Gagal memuat data untuk edit.');
            showNotification(msg, 'error');
        }
    });
}

// ===== Confirm & Delete =====
function confirmDelete(id) {
    if (confirm('Apakah Anda yakin ingin menghapus data ini?')) {
        $.ajax({
            url: `${API_BASE_URL}/${id}`,
            type: 'DELETE',
            dataType: 'json',
            success: function (response) {
                showNotification('Data KTP berhasil dihapus.', 'success');
                loadKtpData();
            },
            error: function (xhr) {
                const msg = parseError(xhr, 'Gagal menghapus data KTP.');
                showNotification(msg, 'error');
            }
        });
    }
}

// ===== Reset Form =====
function resetForm() {
    $('#ktpForm')[0].reset();
    $('#ktpId').val('');
    $('#submitBtnText').text('Simpan');
    $('#cancelBtn').hide();
}

// ===== Notification =====
function showNotification(message, type) {
    const $notif = $('#notification');
    $notif.removeClass('success error').addClass(type).text(message).fadeIn(300);
    setTimeout(function () {
        $notif.fadeOut(400);
    }, 3000);
}

// ===== Helpers =====
function formatDate(dateStr) {
    if (!dateStr) return '-';
    const parts = dateStr.split('-'); // [yyyy, MM, dd]
    if (parts.length !== 3) return dateStr;
    return `${parts[2]}-${parts[1]}-${parts[0]}`; // dd-MM-yyyy
}

function escapeHtml(str) {
    if (!str) return '';
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

function parseError(xhr, defaultMsg) {
    try {
        const resp = JSON.parse(xhr.responseText);
        if (resp.message) return resp.message;
        if (resp.data && typeof resp.data === 'object') {
            // Validation errors: field -> message map
            const messages = Object.values(resp.data).join(', ');
            return messages || defaultMsg;
        }
    } catch (e) {
        // ignore parse error
    }
    return defaultMsg;
}
