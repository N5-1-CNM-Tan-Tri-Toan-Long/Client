package com.n5_qlsv_client.service;

import com.n5_qlsv_client.model.LichHocSinhVien;
import com.n5_qlsv_client.model.SinhVien;

import java.util.List;

public interface LichHocSinhVienService {
    List<LichHocSinhVien> getLichHocByMaSV(long maSV);
}