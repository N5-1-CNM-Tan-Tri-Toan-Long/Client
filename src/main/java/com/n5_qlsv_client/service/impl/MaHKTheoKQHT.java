package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.KetQuaHocTap;

import com.n5_qlsv_client.service.KetQuaHocTapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class MaHKTheoKQHT {

    @Autowired
    private KetQuaHocTapService ketQuaHocTapService;
    public List<Long> getMaHKTheoKQHT(String maSV){
        List<Long> hks = new ArrayList<>();
        List<KetQuaHocTap> ketQuaHocTaps = ketQuaHocTapService.findKQHTByMaSV(maSV);
        for(KetQuaHocTap kqht : ketQuaHocTaps){
            long mahk = kqht.getLopHocPhan().getHocKy().getMaHK();
            hks.add(mahk);
        }
        ArrayList<Long> arrHK = new ArrayList<>();
        for (Long hk : hks) {
            if (!arrHK.contains(hk)) {
                arrHK.add(hk);
            }
        }
        hks.clear();
        hks.addAll(arrHK);
        hks.sort(((o1, o2) -> (int) (o1 - o2)));
        return hks;
    }
}
