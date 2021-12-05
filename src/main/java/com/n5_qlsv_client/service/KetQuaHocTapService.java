package com.n5_qlsv_client.service;

import com.n5_qlsv_client.model.KetQuaHocTap;

import java.util.List;
import java.util.Set;

public interface KetQuaHocTapService {

    List<KetQuaHocTap> findKQHTByMaSV(String maSV);

    List<KetQuaHocTap> findKQHYByMaLHP(Long maLHP);

}
