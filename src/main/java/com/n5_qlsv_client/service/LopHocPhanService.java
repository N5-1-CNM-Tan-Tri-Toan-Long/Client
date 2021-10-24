package com.n5_qlsv_client.service;



import com.n5_qlsv_client.model.LopHocPhan;

import java.util.List;

public interface LopHocPhanService {

    List<LopHocPhan> getAllLopHocPhans();

    List<LopHocPhan> getAllLopHocPhansByPageAndSize(int pageIndex, int pageSize);

    void saveLopHocPhan(LopHocPhan lopHocPhan);

    void deleteLopHocPhan(long ma_lhp);

    LopHocPhan findById(long ma_lhp);

}
