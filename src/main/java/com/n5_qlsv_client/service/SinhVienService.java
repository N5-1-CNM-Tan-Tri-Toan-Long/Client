package com.n5_qlsv_client.service;


import com.n5_qlsv_client.model.SinhVien;

import java.util.List;

public interface SinhVienService {

    List<SinhVien> getAllSinhViens();

    List<SinhVien> getAllSinhViensByPageAndSize(int pageIndex, int pageSize);

    SinhVien findById(String ma_sv);
}
