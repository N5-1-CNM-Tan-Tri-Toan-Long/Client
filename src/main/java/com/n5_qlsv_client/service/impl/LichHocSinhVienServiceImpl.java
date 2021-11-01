package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.LichHocSinhVien;
import com.n5_qlsv_client.service.LichHocSinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LichHocSinhVienServiceImpl implements LichHocSinhVienService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.lichhocsv}")
    private String url;

    @Override
    public List<LichHocSinhVien> getLichHocByMaSV(String maSV) {
        ResponseEntity<List<LichHocSinhVien>> responseEntity = restTemplate.exchange(url+'/'+maSV, HttpMethod.GET, null, new ParameterizedTypeReference<List<LichHocSinhVien>>() {
        });
        return responseEntity.getBody();
    }
}
