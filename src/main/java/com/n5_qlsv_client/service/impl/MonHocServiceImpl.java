package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.MonHoc;
import com.n5_qlsv_client.service.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MonHocServiceImpl implements MonHocService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.url.monhoc}")
    private String url;

    @Override
    public MonHoc findById(long maMonHoc) {
        MonHoc monHoc = restTemplate.getForObject(url + "/" + maMonHoc, MonHoc.class);
        return monHoc;
    }


}
