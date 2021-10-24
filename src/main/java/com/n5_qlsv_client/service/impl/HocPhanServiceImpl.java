package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.HocPhan;
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
    public List<HocPhan> getAllHocPhans() {
        ResponseEntity<List<HocPhan>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocPhan>>() {
                });
        List<HocPhan> hocPhanList = responseEntity.getBody();
        return hocPhanList;
    }

    @Override
    public List<HocPhan> getAllHocPhansByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<HocPhan>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<HocPhan>>() {
                });
        List<HocPhan> hocPhanList = responseEntity.getBody();
        return hocPhanList;
    }

    @Override
    public void saveHocPhan(HocPhan hocPhan) {
        long maHocPhan = hocPhan.getMaHocPhan();
        if(maHocPhan == 0){
            restTemplate.postForEntity(url, hocPhan, String.class);
        }else {
            restTemplate.put(url + "/" + maHocPhan, hocPhan);
        }
    }

    @Override
    public void deleteHocPhans(long maHocPhan) {
        restTemplate.delete(url + "/" + maHocPhan);
    }

    @Override
    public HocPhan findById(long maHocPhan) {
        HocPhan hocPhan = restTemplate.getForObject(url + "/" + maHocPhan, HocPhan.class);
        return hocPhan;
    }
}
