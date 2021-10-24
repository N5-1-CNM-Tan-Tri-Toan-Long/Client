package com.n5_qlsv_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SinhVienController {

    @GetMapping
    public String homePage(Model model){

        model.addAttribute("TrangHienTai","");
        return "index";
    }

//    @GetMapping("/thong-tin-sinh-vien")
//    public String ThongTinSinhVien(Model model){
//
//        model.addAttribute("TrangHienTai","Thong Tin Sinh Vien");
//        return "thong-tin-sinh-vien";
//    }

    @GetMapping("/thong-tin-ngan-hang")
    public String ThongTinNganHang(Model model){

        model.addAttribute("TrangHienTai","Thong Tin Ngan Hang");
        return "thong-tin-ngan-hang";
    }

    @GetMapping("/ket-qua-hoc-tap")
    public String KetQuaHocTap(Model model){

        model.addAttribute("TrangHienTai","Ket Qua Hoc Tap");
        return "ket-qua-hoc-tap";
    }

    @GetMapping("/lich-theo-tuan")
    public String LichTheoTuan(Model model){

        model.addAttribute("TrangHienTai","Lịch Theo Tuần");
        return "lich-theo-tuan";
    }

    @GetMapping("/lop-hoc")
    public String LopHoc(Model model){

        model.addAttribute("TrangHienTai","Lớp Học");
        return "lop-hoc";
    }
}
