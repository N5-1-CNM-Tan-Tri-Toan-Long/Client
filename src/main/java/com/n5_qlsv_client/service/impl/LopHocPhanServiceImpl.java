package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.LopHocPhan;
import com.n5_qlsv_client.service.LopHocPhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LopHocPhanServiceImpl implements LopHocPhanService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.lophocphan}")
    private String url;

    @Override
    public synchronized void saveLopHocPhan(LopHocPhan lopHocPhan) {
        long maLopHocPhan = lopHocPhan.getMaLHP();
        if (maLopHocPhan == 0) {
            restTemplate.postForEntity(url, lopHocPhan, String.class);
        } else {
            restTemplate.put(url + "/" + maLopHocPhan, lopHocPhan);
        }
    }

    @Override
    public LopHocPhan findById(long ma_lhp) {
        return restTemplate.getForObject(url + "/" + ma_lhp, LopHocPhan.class);
    }

    @Override
    public List<LopHocPhan> findByMaHocPhan(String maHP) {
        ResponseEntity<List<LopHocPhan>> responseEntity
                = restTemplate.exchange(url + "/mahocphan/" + maHP,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<LopHocPhan>>() {
                });
        return responseEntity.getBody();
    }
}
