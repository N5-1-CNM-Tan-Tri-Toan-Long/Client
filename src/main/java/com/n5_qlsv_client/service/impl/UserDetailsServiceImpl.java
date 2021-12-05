package com.n5_qlsv_client.service.impl;


import com.n5_qlsv_client.model.SinhVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.url.sinhvien}")
    private String url;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        SinhVien sinhVien = restTemplate.getForObject(url + "/" + s, SinhVien.class);

        if (sinhVien == null) {
            throw new UsernameNotFoundException("Sinh vien " + sinhVien + " was not found in the database");
        }

        String role = restTemplate.getForObject(url + "/" + s + "/role", String.class);

        List<GrantedAuthority> grantList = new ArrayList<>();

        if (role != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            grantList.add(authority);
        }

        return new User(String.valueOf(sinhVien.getMaSV()), sinhVien.getPassword(), grantList);
    }
}
