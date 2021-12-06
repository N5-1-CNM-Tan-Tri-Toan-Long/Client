package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.HocPhanKhungService;
import com.n5_qlsv_client.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/chuong-trinh-khung")
public class HocPhanKhungController {

    @Autowired
    private HocPhanKhungService hocPhanKhungService;

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String hocPhanKhung(Model model, Principal principal){

        //Lấy mã sinh viên thông qua login principal
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginedUser.getUsername();

        SinhVien sinhVien = sinhVienService.findById(maSV);
        model.addAttribute("tensinhvien", sinhVien.getTenSV());

        model.addAttribute("hpks1",
                hocPhanKhungService.findAllByChuyenNganhAndHocKi(sinhVien.getChuyenNganh().getMaChuyenNganh(), 1));

        model.addAttribute("hpks2",
                hocPhanKhungService.findAllByChuyenNganhAndHocKi(sinhVien.getChuyenNganh().getMaChuyenNganh(), 2));

        model.addAttribute("hpks3",
                hocPhanKhungService.findAllByChuyenNganhAndHocKi(sinhVien.getChuyenNganh().getMaChuyenNganh(), 3));

        model.addAttribute("hpks4",
                hocPhanKhungService.findAllByChuyenNganhAndHocKi(sinhVien.getChuyenNganh().getMaChuyenNganh(), 4));

        model.addAttribute("hpks5",
                hocPhanKhungService.findAllByChuyenNganhAndHocKi(sinhVien.getChuyenNganh().getMaChuyenNganh(), 5));

        model.addAttribute("hpks6",
                hocPhanKhungService.findAllByChuyenNganhAndHocKi(sinhVien.getChuyenNganh().getMaChuyenNganh(), 6));

        model.addAttribute("hpks7",
                hocPhanKhungService.findAllByChuyenNganhAndHocKi(sinhVien.getChuyenNganh().getMaChuyenNganh(), 7));

        model.addAttribute("hpks8",
                hocPhanKhungService.findAllByChuyenNganhAndHocKi(sinhVien.getChuyenNganh().getMaChuyenNganh(), 8));

        return "hoc-phan-khung";
    }

}
