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
        List<HocKy> hocKyList = responseEntity.getBody();
        return hocKyList;
    }

    @Override
    public List<HocKy> getAllHocKysByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<HocKy>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocKy>>() {
                });
        List<HocKy> hocKyList = responseEntity.getBody();
        return hocKyList;
    }

    @Override
    public void saveHocKy(HocKy hocKy) {
        long id = hocKy.getMaHK();
        if(id == 0){
            restTemplate.postForEntity(url, hocKy, String.class);
        }else {
            restTemplate.put(url + "/" + id, hocKy);
        }
    }

    @Override
    public void deleteHocKy(long id) {
        restTemplate.delete(url + "/" + id);
    }

    @Override
    public HocKy findById(long id) {
        HocKy hocKy = restTemplate.getForObject(url + "/" + id, HocKy.class);
        return hocKy;
    }
}
