package com.n5_qlsv_client.service.impl;


import com.n5_qlsv_client.model.HocKy;
import com.n5_qlsv_client.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HocKyServiceImpl implements HocKyService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.hocky}")
    private String url;

    @Override
    public List<HocKy> getAllHocKys() {
        ResponseEntity<List<HocKy>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocKy>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public HocKy findById(long id) {
        return restTemplate.getForObject(url + "/" + id, HocKy.class);
    }
}
