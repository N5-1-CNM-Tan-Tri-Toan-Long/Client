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

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/thong-tin-sinh-vien")
public class ThongTinSinhVienController {

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping
    public String findSinhVienById(Model model, HttpSession session){
        String ma = (String) session.getAttribute("maSV");
        SinhVien sinhVien = sinhVienService.findById(ma);
        model.addAttribute("sinhvien", sinhVien);
        model.addAttribute("TrangHienTai","Thông Tin Sinh Viên");
        return "thong-tin-sinh-vien";
    }
}
