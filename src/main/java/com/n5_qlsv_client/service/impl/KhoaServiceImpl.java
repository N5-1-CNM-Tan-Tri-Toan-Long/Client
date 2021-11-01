package com.n5_qlsv_client.service.impl;


import com.n5_qlsv_client.model.Khoa;
import com.n5_qlsv_client.service.KhoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class KhoaServiceImpl implements KhoaService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.khoa}")
    private String url;

    @Override
    public List<Khoa> getAllKhoas() {
        ResponseEntity<List<Khoa>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Khoa>>() {
                });
        List<Khoa> khoaList = responseEntity.getBody();
        return khoaList;
    }

    @Override
    public List<Khoa> getAllKhoasByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<Khoa>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Khoa>>() {
                });
        List<Khoa> khoaList = responseEntity.getBody();
        return khoaList;
    }


    @Override
    public Khoa findById(long ma_khoa) {
        Khoa khoa = restTemplate.getForObject(url + "/" + ma_khoa, Khoa.class);
        return khoa;
    }
}
