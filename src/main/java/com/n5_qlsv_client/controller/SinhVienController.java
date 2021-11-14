package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.KetQuaHocTap;
import com.n5_qlsv_client.model.SinhVien;
import com.n5_qlsv_client.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {
    @Autowired
    private SinhVienService sinhVienService;


    @GetMapping("/thong-tin-ngan-hang")
    public String ThongTinNganHang(Model model){
        model.addAttribute("TrangHienTai","Thong Tin Ngan Hang");
        return "thong-tin-ngan-hang";
    }


    @GetMapping("/lop-hoc")
    public String LopHoc(Model model){

        model.addAttribute("TrangHienTai","Lớp Học");
        return "lop-hoc";
    }
}
