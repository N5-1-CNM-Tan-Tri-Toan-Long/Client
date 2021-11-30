package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.HocPhanKhung;
import com.n5_qlsv_client.service.HocPhanKhungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HocPhanKhungServiceImpl implements HocPhanKhungService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.hocphankhung}")
    private String url;

    @Override
    public List<HocPhanKhung> findAllByChuyenNganhAndHocKi(long maCN, int tthk) {
        ResponseEntity<List<HocPhanKhung>> responseEntity
                = restTemplate.exchange(url + "/chuyenNganh/" + maCN + "/thuTuHocKi/" + tthk,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocPhanKhung>>() {
                });
        List<HocPhanKhung> hocPhanKhungList = responseEntity.getBody();
        return hocPhanKhungList;
    }
}
