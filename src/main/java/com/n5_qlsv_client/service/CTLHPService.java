package com.n5_qlsv_client.service;



import com.n5_qlsv_client.model.ChiTietLopHocPhan;

import java.util.List;

public interface CTLHPService {

    List<ChiTietLopHocPhan> findByMaLopHocPhan(Long maLHP);

    ChiTietLopHocPhan findById(long ma_ctlhp);

}
