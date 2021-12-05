package com.n5_qlsv_client.service.impl;

import com.n5_qlsv_client.model.KetQuaHocTap;
import com.n5_qlsv_client.service.KetQuaHocTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Service
public class KetQuaHocTapServiceImpl implements KetQuaHocTapService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.ketquahoctap}")
    private String url;

    @Override
    public List<KetQuaHocTap> findKQHTByMaSV(String maSV) {
        ResponseEntity<List<KetQuaHocTap>> responseEntity
                = restTemplate.exchange(url + "/" + maSV + "/masv",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<KetQuaHocTap>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public List<KetQuaHocTap> findKQHYByMaLHP(Long maLHP) {
        ResponseEntity<List<KetQuaHocTap>> responseEntity
                = restTemplate.exchange(url + "/" + maLHP + "/kq",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<KetQuaHocTap>>() {
                });
        return responseEntity.getBody();
    }


}
