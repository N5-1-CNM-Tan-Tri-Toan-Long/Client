package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.LopHoc;
import com.n5_qlsv_client.service.LopHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LopHocServiceImpl implements LopHocService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.lophoc}")
    private String url;

    @Override
    public LopHoc findById(long maLopHoc) {
        LopHoc lopHoc = restTemplate.getForObject(url + "/" + maLopHoc, LopHoc.class);
        return lopHoc;
    }
}
