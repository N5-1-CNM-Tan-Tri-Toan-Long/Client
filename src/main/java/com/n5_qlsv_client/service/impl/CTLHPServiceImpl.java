package com.n5_qlsv_client.service.impl;


import com.n5_qlsv_client.model.ChiTietLopHocPhan;
import com.n5_qlsv_client.service.CTLHPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CTLHPServiceImpl implements CTLHPService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.lophocphan}")
    private String urlLHP;

    @Value("${app.url.ctlhp}")
    private String urlCTLHP;

    @Override
    public List<ChiTietLopHocPhan> findByMaLopHocPhan(Long maLHP) {
        ResponseEntity<List<ChiTietLopHocPhan>> responseEntity
                = restTemplate.exchange(urlCTLHP + "/malophocphan/" + maLHP, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ChiTietLopHocPhan>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public void saveCTLHP(ChiTietLopHocPhan chiTietLopHocPhan, Long maLHP) {
        long maCTLHP = chiTietLopHocPhan.getMaCTLHP();
        if (maCTLHP == 0) {
            restTemplate.postForEntity(urlLHP + "/" + maLHP + "/CTLHP", chiTietLopHocPhan, String.class);
        } else {
            restTemplate.put(urlCTLHP + "/" + maCTLHP, chiTietLopHocPhan);
        }
    }

    @Override
    public void deleteCTLHP(long ma_ctlhp) {
        restTemplate.delete(urlCTLHP + "/" + ma_ctlhp);
    }

    @Override
    public ChiTietLopHocPhan findById(long ma_ctlhp) {
        ChiTietLopHocPhan chiTietLopHocPhan = restTemplate.getForObject(urlCTLHP + "/" + ma_ctlhp,
                ChiTietLopHocPhan.class);
        return chiTietLopHocPhan;
    }
}
