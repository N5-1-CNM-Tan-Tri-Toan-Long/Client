package com.n5_qlsv_client.service;




import com.n5_qlsv_client.model.HocPhan;
import com.n5_qlsv_client.model.LopHocPhan;

import java.util.List;

public interface HocPhanService {

    HocPhan findById(String maHocPhan);

    List<HocPhan> findHPByMaHK(long maHK);
}
