package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.HocPhan;
import com.n5_qlsv_client.model.LopHocPhan;
import com.n5_qlsv_client.service.HocPhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HocPhanServiceImpl implements HocPhanService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.hocphan}")
    private String url;

    @Override
    public HocPhan findById(String maHocPhan) {
        return restTemplate.getForObject(url + "/" + maHocPhan, HocPhan.class);
    }

    @Override
    public List<HocPhan> findHPByMaHK(long maHK) {
        ResponseEntity<List<HocPhan>> responseEntity
                = restTemplate.exchange(url + "/" + maHK + "/mahk",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocPhan>>() {
                });
        return responseEntity.getBody();
    }
}
