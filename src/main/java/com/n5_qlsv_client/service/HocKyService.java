package com.n5_qlsv_client.service;



import com.n5_qlsv_client.model.HocKy;

import java.util.List;

public interface HocKyService {

    List<HocKy> getAllHocKys();

    HocKy findById(long id);

}
