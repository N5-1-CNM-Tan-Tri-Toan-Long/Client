package com.n5_qlsv_client.controller;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.n5_qlsv_client.model.*;
import com.n5_qlsv_client.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/hoc-phan/dang-ky-hoc-phan")
public class DangKyHocPhanController {

    @Autowired
    private LopHocPhanService lopHocPhanService;

    @Autowired
    private HocPhanService hocPhanService;

    @Autowired
    private CTLHPService ctlhpService;

    @Autowired
    private HocKyService hocKyService;

    @Autowired
    private LichHocSinhVienService lichHocSinhVienService;

    @Autowired
    private SinhVienService sinhVienService;

    private Logger logger = LoggerFactory.getLogger(DangKyHocPhanController.class);

    @GetMapping
    public String hocKyLopHocPhan(Model model, Long maHK) {
        if (maHK != null) {
            model.addAttribute("danhsachHP", hocPhanService.findHPByMaHK(maHK));
            model.addAttribute("HKLHP", lopHocPhanService.getAllLopHocPhans());

        } else {
            model.addAttribute("HKLHP", lopHocPhanService.getAllLopHocPhans());
        }

        model.addAttribute("maHK", maHK);
        model.addAttribute("TrangHienTai", "Đăng Ký Học Phần");
        return "dang-ky-hoc-phan";
    }

    @PostMapping("/lop-hoc-phan")
    @ResponseBody
    public List<LopHocPhanChoDangKy> lopHocPhan(@RequestParam("maHocPhan") String maHocPhan, @RequestParam("maHocKy") long maHocKy) {
        List<LopHocPhanChoDangKy> list = new ArrayList<>();
        lopHocPhanService.findByMaHocPhan(maHocPhan).forEach(lopHocPhan -> {
            if (lopHocPhan.getHocKy().getMaHK() == maHocKy)
                list.add(new LopHocPhanChoDangKy(lopHocPhan.getMaLHP(), lopHocPhan.getTenLHP(),
                        lopHocPhan.getTrangThai(), lopHocPhan.getTenVietTat(),
                        lopHocPhan.getSoLuongDangKyHienTai(), lopHocPhan.getSoLuongDangKyToiDa()));
        });
        return list;
    }

    @PostMapping("/chi-tiet-lop-hoc-phan")
    @ResponseBody
    public List<CTLHPChoDangKy> findByMaLopHocPhan(@RequestParam("maLHP") long id) {
        List<CTLHPChoDangKy> list = new ArrayList<>();
        ctlhpService.findByMaLopHocPhan(id).forEach(chiTietLopHocPhan -> {
            list.add(new CTLHPChoDangKy(chiTietLopHocPhan.getTietHoc(), chiTietLopHocPhan.getCoSo(),
                    chiTietLopHocPhan.getPhong(), chiTietLopHocPhan.getGiangVien().getTenGV(),
                    convertToLocalDate(chiTietLopHocPhan.getNgayBatDau()),
                    convertToLocalDate(chiTietLopHocPhan.getNgayKetThuc())));
        });
        return list;
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @PostMapping("/dang-ky-lop-hoc-phan")
    @ResponseBody
    public List<LopHocPhanDaDK> lopHocPhanDaDK(@RequestParam("maCTLHP") long id,@RequestParam("maHocKy") long maHocKy){
        String maSV = "18000001";
        ChiTietLopHocPhan chiTietLopHocPhan = ctlhpService.findById(id);
        LichHocSinhVien lichHocSinhVien = new LichHocSinhVien();
        lichHocSinhVien.setSinhVien(sinhVienService.findById(maSV));
        lichHocSinhVien.setChiTietLopHocPhan(chiTietLopHocPhan);
        lichHocSinhVienService.saveLHSV(lichHocSinhVien);
        //xử lý data
        List<LopHocPhanDaDK> list = new ArrayList<>();

        lichHocSinhVienService.getLichHocByMaSV(maSV).forEach(LHPDaDK ->{
            if(LHPDaDK.getChiTietLopHocPhan().getLopHocPhan().getHocKy().getMaHK() == maHocKy)
            list.add(new LopHocPhanDaDK(LHPDaDK.getChiTietLopHocPhan().getLopHocPhan().getMaLHP(),
                    LHPDaDK.getChiTietLopHocPhan().getLopHocPhan().getTenLHP(),
                    LHPDaDK.getChiTietLopHocPhan().getLopHocPhan().getTenVietTat(),
                    "Đăng ký mới", LHPDaDK.getChiTietLopHocPhan().getLopHocPhan().getTrangThai(),
                    LHPDaDK.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCLT()+
                    LHPDaDK.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCTH(),
                    LHPDaDK.getChiTietLopHocPhan().getLopHocPhan().getSoNhomTH(),
                    111111, LocalDate.now()));
        });
        return list;
    }

}
