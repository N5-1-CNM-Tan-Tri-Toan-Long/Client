package com.n5_qlsv_client.service;

import com.n5_qlsv_client.model.HocPhanKhung;

import java.util.List;

public interface HocPhanKhungService {

    List<HocPhanKhung> findAllByChuyenNganhAndHocKi(long maCN, int tthk);

    List<HocPhanKhung> findAllByChuyenNganh(long maCN);
}
