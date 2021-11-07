package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.ChiTietLopHocPhan;
import com.n5_qlsv_client.model.LichHocSinhVien;
import com.n5_qlsv_client.service.LichHocSinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LichHocSinhVienServiceImpl implements LichHocSinhVienService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.lichhocsv}")
    private String url;

    @Override
    public List<LichHocSinhVien> getLichHocByMaSV(String maSV) {
        ResponseEntity<List<LichHocSinhVien>> responseEntity = restTemplate.exchange(url + "/masv/" + maSV, HttpMethod.GET, null, new ParameterizedTypeReference<List<LichHocSinhVien>>() {
        });
        return responseEntity.getBody();
    }

    @Override
    public void saveLHSV(LichHocSinhVien lichHocSinhVien) {
        long ma_LHSV = lichHocSinhVien.getMaLHSV();
        if(ma_LHSV == 0){
            restTemplate.postForEntity(url, lichHocSinhVien, String.class);
        }else {
            restTemplate.put(url + "/" + ma_LHSV, lichHocSinhVien);
        }
    }

    @Override
    public void deleteLHSV(Long maLHSV) {
        restTemplate.delete(url + "/" + maLHSV);
    }


}
