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
    public List<LopHocPhan> getAllLopHocPhans() {
        ResponseEntity<List<LopHocPhan>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<LopHocPhan>>() {
                });
        List<LopHocPhan> lopHocPhanList = responseEntity.getBody();
        return lopHocPhanList;
    }

    @Override
    public List<LopHocPhan> getAllLopHocPhansByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<LopHocPhan>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<LopHocPhan>>() {
                });
        List<LopHocPhan> lopHocPhanList = responseEntity.getBody();
        return lopHocPhanList;
    }

    @Override
    public void saveLopHocPhan(LopHocPhan lopHocPhan) {
        long maLopHocPhan = lopHocPhan.getMaLHP();
        if(maLopHocPhan == 0){
            restTemplate.postForEntity(url, lopHocPhan, String.class);
        }else {
            restTemplate.put(url + "/" + maLopHocPhan, lopHocPhan);
        }
    }

    @Override
    public void deleteLopHocPhan(long ma_lhp) {
        restTemplate.delete(url + "/" + ma_lhp);
    }

    @Override
    public LopHocPhan findById(long ma_lhp) {
        LopHocPhan lopHocPhan = restTemplate.getForObject(url + "/" + ma_lhp, LopHocPhan.class);
        return lopHocPhan;
    }

    @Override
    public List<LopHocPhan> findLHPByMaHK(long maHK) {
        ResponseEntity<List<LopHocPhan>> responseEntity
                = restTemplate.exchange(url + "/" + maHK + "/mahk",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<LopHocPhan>>() {
                });
        List<LopHocPhan> lopHocPhanList = responseEntity.getBody();
        return lopHocPhanList;
    }
}
