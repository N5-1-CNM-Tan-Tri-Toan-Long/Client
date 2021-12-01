package com.n5_qlsv_client.service;

import com.n5_qlsv_client.model.KetQuaHocTap;

import java.util.List;
import java.util.Set;

public interface KetQuaHocTapService {

    List<KetQuaHocTap> findKQHTByMaSV(String maSV);

    Set<Long> findMaHKByMaSV(String maSV);

    KetQuaHocTap saveKetQuaHT(KetQuaHocTap ketQuaHocTap);

    void deleteKQHT(Long maKQHT);

    KetQuaHocTap findKQHTByMaSVAndMaLHP(String maSV, Long maLHP);

    List<KetQuaHocTap> findKQHYByMaLHP(Long maLHP);

}
