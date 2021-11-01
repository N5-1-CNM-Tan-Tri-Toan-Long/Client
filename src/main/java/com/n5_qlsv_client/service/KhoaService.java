package com.n5_qlsv_client.service;


import com.n5_qlsv_client.model.Khoa;

import java.util.List;

public interface KhoaService {

    List<Khoa> getAllKhoas();

    List<Khoa> getAllKhoasByPageAndSize(int pageIndex, int pageSize);

    Khoa findById(long ma_khoa);
}