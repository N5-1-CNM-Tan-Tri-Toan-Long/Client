package com.n5_qlsv_client.service;




import com.n5_qlsv_client.model.HocPhan;

import java.util.List;

public interface HocPhanService {

    List<HocPhan> getAllHocPhans();

    List<HocPhan> getAllHocPhansByPageAndSize(int pageIndex, int pageSize);

    HocPhan findById(String maHocPhan);
}
