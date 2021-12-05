package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.GiangVien;
import com.n5_qlsv_client.service.GiangVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GiangVienServiceImpl implements GiangVienService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.giangvien}")
    private String url;

    @Override
    public GiangVien findById(long maGiangVien) {
        return restTemplate.getForObject(url + "/" + maGiangVien, GiangVien.class);
    }
}
