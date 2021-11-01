package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.sinhvien}")
    private String url;

    @Override
    public List<SinhVien> getAllSinhViens() {
        ResponseEntity<List<SinhVien>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SinhVien>>() {
                });
        List<SinhVien> sinhVienList = responseEntity.getBody();
        return sinhVienList;
    }

    @Override
    public List<SinhVien> getAllSinhViensByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<SinhVien>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SinhVien>>() {
                });
        List<SinhVien> sinhVienList = responseEntity.getBody();
        return sinhVienList;
    }

    @Override
    public SinhVien findById(String ma_sv) {
        SinhVien sinhVien = restTemplate.getForObject(url + "/" + ma_sv, SinhVien.class);
        return sinhVien;
    }
}
