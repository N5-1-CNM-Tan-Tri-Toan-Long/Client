package com.n5_qlsv_client.service;




import com.n5_qlsv_client.model.ChuyenNganh;

import java.util.List;

public interface ChuyenNganhService {

    List<ChuyenNganh> getAllChuyenNganhs();
    
    ChuyenNganh findById(long id);

}
