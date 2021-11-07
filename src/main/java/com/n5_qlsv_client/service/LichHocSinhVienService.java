package com.n5_qlsv_client.service;

import com.n5_qlsv_client.model.LichHocSinhVien;

import java.util.List;

public interface LichHocSinhVienService {
    List<LichHocSinhVien> getLichHocByMaSV(String maSV);
    void saveLHSV(LichHocSinhVien lichHocSinhVien);
}
