package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.service.SinhVienService;
import com.n5_qlsv_client.service.impl.MaHKTheoKQHT;
import com.n5_qlsv_client.model.KetQuaHocTap;
import com.n5_qlsv_client.service.HocKyService;
import com.n5_qlsv_client.service.KetQuaHocTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import java.util.List;


@Controller
@RequestMapping("/ket-qua-hoc-tap")
public class KetQuaHocTapController {

    @Autowired
    private KetQuaHocTapService ketQuaHocTapService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private MaHKTheoKQHT maHKTheoKQHT;
    @Autowired
    private SinhVienService sinhVienService;
    @GetMapping
    public String findKQHTByMaSV(Model model, HttpSession session){

        String maSV = (String) session.getAttribute("maSV");
        List<KetQuaHocTap> ketQuaHocTaps = ketQuaHocTapService.findKQHTByMaSV(maSV);
        model.addAttribute("TrangHienTai","Kết Quả Học Tập");
        model.addAttribute("KQHTs", ketQuaHocTaps);
        model.addAttribute("HK", hocKyService.getAllHocKys());
        model.addAttribute("HKSV", maHKTheoKQHT.getMaHKTheoKQHT(maSV));
        return "ket-qua-hoc-tap";
    }

    @GetMapping("/in-lich")
    public String inKetQuaHocTap(Model model, HttpSession session) {
        String maSV = (String) session.getAttribute("maSV");
        List<KetQuaHocTap> ketQuaHocTaps = ketQuaHocTapService.findKQHTByMaSV(maSV);
        model.addAttribute("TrangHienTai","Kết Quả Học Tập");
        model.addAttribute("KQHTs", ketQuaHocTaps);
        model.addAttribute("HK", hocKyService.getAllHocKys());
        model.addAttribute("HKSV", maHKTheoKQHT.getMaHKTheoKQHT(maSV));
        model.addAttribute("sinhvien", sinhVienService.findById(maSV));
        return "in-ket-qua-hoc-tap";
    }

}
