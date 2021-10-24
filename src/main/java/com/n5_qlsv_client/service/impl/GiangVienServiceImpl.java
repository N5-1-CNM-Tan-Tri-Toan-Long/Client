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
    public List<GiangVien> getAllGiangVien() {
        ResponseEntity<List<GiangVien>> responseEntity
                = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<GiangVien>>() {
                });
        List<GiangVien> giangVienList = responseEntity.getBody();
        return giangVienList;
    }

    @Override
    public List<GiangVien> getAllGiangVienByPageAndSize(int pageIndex, int pageSize) {
        ResponseEntity<List<GiangVien>> responseEntity
                = restTemplate.exchange(url + "?page=" + pageIndex + "&size=" + pageSize,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<GiangVien>>() {
                });
        List<GiangVien> giangVienList = responseEntity.getBody();
        return giangVienList;
    }

    @Override
    public void saveGiangVien(GiangVien giangVien) {
        long maGiangVien = giangVien.getMaGV();
        if(maGiangVien == 0){
            restTemplate.postForEntity(url, giangVien, String.class);
        }else
        {
            restTemplate.put(url + "/" + maGiangVien, giangVien);
        }
    }

    @Override
    public void deleteGiangViens(long maGiangVien) {
        restTemplate.delete(url + "/" + maGiangVien);
    }

    @Override
    public GiangVien findById(long maGiangVien) {
        GiangVien giangVien = restTemplate.getForObject(url + "/" + maGiangVien, GiangVien.class);
        return giangVien;
    }
}
