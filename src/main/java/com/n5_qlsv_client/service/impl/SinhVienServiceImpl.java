package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.sinhvien}")
    private String url;

    @Override
    public SinhVien findById(String ma_sv) {
        SinhVien sinhVien = restTemplate.getForObject(url + "/" + ma_sv, SinhVien.class);
        return sinhVien;
    }

    @Override
    public void saveSinhVien(SinhVien sinhVien) {
        String ma_sv = sinhVien.getMaSV();
        if (ma_sv == "") {
        }else {
            restTemplate.put(url + "/" + ma_sv, sinhVien);
        }
    }
}
