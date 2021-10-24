package com.n5_qlsv_client.service;



import com.n5_qlsv_client.model.HocKy;

import java.util.List;

public interface HocKyService {

    List<HocKy> getAllHocKys();

    List<HocKy> getAllHocKysByPageAndSize(int pageIndex, int pageSize);

    void saveHocKy(HocKy hocKy);

    void deleteHocKy(long id);

    HocKy findById(long id);

}
