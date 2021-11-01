package com.n5_qlsv_client.service.impl;


import com.n5_qlsv_client.model.ChuyenNganh;
import com.n5_qlsv_client.service.ChuyenNganhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ChuyenNganhServiceImpl implements ChuyenNganhService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.chuyennganh}")
    private String url;

    @Override
    public List<ChuyenNganh> getAllChuyenNganhs() {
        ResponseEntity<List<ChuyenNganh>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ChuyenNganh>>() {
                });
        List<ChuyenNganh> chuyenNganhList = responseEntity.getBody();
        return chuyenNganhList;
    }

    @Override
    public List<ChuyenNganh> getAllChuyenNganhsByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<ChuyenNganh>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ChuyenNganh>>() {
                });
        List<ChuyenNganh> chuyenNganhList = responseEntity.getBody();
        return chuyenNganhList;
    }

    @Override
    public ChuyenNganh findById(long id) {
        ChuyenNganh chuyenNganh = restTemplate.getForObject(url + "/" + id, ChuyenNganh.class);
        return chuyenNganh;
    }
}
