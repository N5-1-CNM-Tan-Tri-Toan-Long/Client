package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.KetQuaHocTap;
import com.n5_qlsv_client.service.KetQuaHocTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ket-qua-hoc-tap")
public class KetQuaHocTapController {

    @Autowired
    private KetQuaHocTapService ketQuaHocTapService;

    @GetMapping
    public String findKQHTByMaSV(Model model){
        List<KetQuaHocTap> ketQuaHocTaps = ketQuaHocTapService.findKQHTByMaSV("18000001");
        model.addAttribute("TrangHienTai","Ket Qua Hoc Tap");
        model.addAttribute("KQHTs", ketQuaHocTaps);
        return "ket-qua-hoc-tap";
    }
}
