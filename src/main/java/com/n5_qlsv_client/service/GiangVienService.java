package com.n5_qlsv_client.service;


import com.n5_qlsv_client.model.GiangVien;

import java.util.List;

public interface GiangVienService {

    List<GiangVien> getAllGiangVien();

    List<GiangVien> getAllGiangVienByPageAndSize(int pageIndex, int pageSize);

    GiangVien findById(long maGiangVien);

}
