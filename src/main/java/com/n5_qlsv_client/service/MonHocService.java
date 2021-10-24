package com.n5_qlsv_client.service;


import com.n5_qlsv_client.model.MonHoc;

import java.util.List;

public interface MonHocService {

    List<MonHoc> getAllMonHoc();

    List<MonHoc> getAllMonHocByPageAndSize(int pageIndex, int pageSize);

    void saveMonHoc(MonHoc monHoc);

    void deleteMonHocs(long maMonHoc);

    MonHoc findById(long maMonHoc);

    List<MonHoc> getAllMonHocNotInHocPhan();
}
