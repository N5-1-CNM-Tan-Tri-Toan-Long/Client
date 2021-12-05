package com.n5_qlsv_client.service;

import com.n5_qlsv_client.model.LopHocPhan;

import java.util.List;

public interface LopHocPhanService {

    void saveLopHocPhan(LopHocPhan lopHocPhan);

    LopHocPhan findById(long ma_lhp);

    List<LopHocPhan> findByMaHocPhan(String maHP);
}
