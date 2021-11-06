package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.service.impl.MaHKTheoKQHT;
import com.n5_qlsv_client.model.KetQuaHocTap;
import com.n5_qlsv_client.service.HocKyService;
import com.n5_qlsv_client.service.KetQuaHocTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/ket-qua-hoc-tap")
public class KetQuaHocTapController {

    @Autowired
    private KetQuaHocTapService ketQuaHocTapService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private MaHKTheoKQHT maHKTheoKQHT;
    @GetMapping
    public String findKQHTByMaSV(Model model){
        String maSV = "18000001";
        List<KetQuaHocTap> ketQuaHocTaps = ketQuaHocTapService.findKQHTByMaSV(maSV);
        model.addAttribute("TrangHienTai","Ket Qua Hoc Tap");
        model.addAttribute("KQHTs", ketQuaHocTaps);
        model.addAttribute("HK", hocKyService.getAllHocKys());
        model.addAttribute("HKSV", maHKTheoKQHT.getMaHKTheoKQHT(maSV));
        return "ket-qua-hoc-tap";
    }

    @GetMapping("/in-lich")
    public String inKetQuaHocTap(Model model) {
        String maSV = "18000001";
        List<KetQuaHocTap> ketQuaHocTaps = ketQuaHocTapService.findKQHTByMaSV(maSV);
        model.addAttribute("TrangHienTai","Ket Qua Hoc Tap");
        model.addAttribute("KQHTs", ketQuaHocTaps);
        model.addAttribute("HK", hocKyService.getAllHocKys());
        model.addAttribute("HKSV", maHKTheoKQHT.getMaHKTheoKQHT(maSV));
        return "in-ket-qua-hoc-tap";
    }

}
