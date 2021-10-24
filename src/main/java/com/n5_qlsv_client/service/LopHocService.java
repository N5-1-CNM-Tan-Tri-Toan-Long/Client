package com.n5_qlsv_client.service;


import com.n5_qlsv_client.model.LopHoc;

import java.util.List;

public interface LopHocService {

    List<LopHoc> getAllLopHocs();

    List<LopHoc> getAllLopHocsByPageAndSize(int pageIndex, int pageSize);

    void saveLopHoc(LopHoc lopHoc);

    void deleteLopHocs(long maLopHoc);

    LopHoc findById(long maLopHoc);
}
