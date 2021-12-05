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
    public Khoa findById(long ma_khoa) {
        return restTemplate.getForObject(url + "/" + ma_khoa, Khoa.class);
    }
}
