package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.ChuyenNganhService;
import com.n5_qlsv_client.service.KhoaService;
import com.n5_qlsv_client.service.LopHocService;
import com.n5_qlsv_client.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thong-tin-sinh-vien")
public class ThongTinSinhVienController {

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String findSinhVienById(Model model){
        SinhVien sinhVien = sinhVienService.findById(3);
        model.addAttribute("sinhvien", sinhVien);
        return "thong-tin-sinh-vien";
    }
}
