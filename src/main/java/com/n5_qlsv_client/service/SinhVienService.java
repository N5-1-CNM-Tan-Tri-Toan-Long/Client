package com.n5_qlsv_client.service;


import com.n5_qlsv_client.model.SinhVien;

public interface SinhVienService {

    SinhVien findById(String ma_sv);

    void saveSinhVien(SinhVien sinhVien);

}
