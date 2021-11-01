package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.MonHoc;
import com.n5_qlsv_client.service.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MonHocServiceImpl implements MonHocService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.url.monhoc}")
    private String url;

    @Override
    public List<MonHoc> getAllMonHoc() {
        ResponseEntity<List<MonHoc>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MonHoc>>() {
                });
        List<MonHoc> monHocList = responseEntity.getBody();
        return monHocList;
    }

    @Override
    public List<MonHoc> getAllMonHocByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<MonHoc>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MonHoc>>() {
                });
        List<MonHoc> monHocList = responseEntity.getBody();
        return monHocList;
    }

    @Override
    public MonHoc findById(long maMonHoc) {
        MonHoc monHoc = restTemplate.getForObject(url + "/" + maMonHoc, MonHoc.class);
        return monHoc;
    }


}
