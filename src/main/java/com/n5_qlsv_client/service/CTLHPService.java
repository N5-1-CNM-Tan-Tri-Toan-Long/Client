package com.n5_qlsv_client.service;



import com.n5_qlsv_client.model.ChiTietLopHocPhan;

import java.util.List;

public interface CTLHPService {

    List<ChiTietLopHocPhan> getAllCTLHPsByLHP(Long idLHP);

    void saveCTLHP(ChiTietLopHocPhan chiTietLopHocPhan, Long maLHP);

    void deleteCTLHP(long ma_ctlhp);

    ChiTietLopHocPhan findById(long ma_ctlhp);

}
