package com.n5_qlsv_client.service;



import com.n5_qlsv_client.model.HocKy;

import java.util.List;

public interface HocKyService {

    List<HocKy> getAllHocKys();

    List<HocKy> getAllHocKysByPageAndSize(int pageIndex, int pageSize);

    HocKy findById(long id);

}
