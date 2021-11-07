package com.n5_qlsv_client.controller;

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
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @Autowired
    private KetQuaHocTapService ketQuaHocTapService;

    private Logger logger = LoggerFactory.getLogger(DangKyHocPhanController.class);

    @GetMapping
    public String hocKyLopHocPhan(Model model, Long maHK) {

        if (maHK != null) {
            Set<HocPhanDaDangKy> list = new HashSet<>();
            lichHocSinhVienService.getLichHocByMaSV("18000001").forEach(lichHocSinhVien -> {
                if (lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocKy().getMaHK() == maHK)
                    list.add(new HocPhanDaDangKy(lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getMaLHP(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenLHP(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenVietTat(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getSoNhomTH() + "",
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTrangThai(),
                            convertToLocalDate(lichHocSinhVien.getNgayDangKyHP()),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCLT() +
                                    lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCTH(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getMaHocPhan()));
            });
            model.addAttribute("listHocPhanDaDK", list);

            List<HocPhan> listHP = new ArrayList<>();
            hocPhanService.findHPByMaHK(maHK).forEach(hocPhan -> {
                if (checkHocPhanTrongList(hocPhan.getMaHocPhan(),list))
                    listHP.add(hocPhan);
            });
            model.addAttribute("danhsachHP", listHP);
        }
        model.addAttribute("HKLHP", lopHocPhanService.getAllLopHocPhans());
        model.addAttribute("maHK", maHK);
        model.addAttribute("TrangHienTai", "Đăng Ký Học Phần");
        return "dang-ky-hoc-phan";
    }

    private boolean checkHocPhanTrongList(String maMonHoc, Set<HocPhanDaDangKy> list) {
        AtomicBoolean ketQua = new AtomicBoolean(true);
        list.forEach(hocPhanDaDangKy -> {
            if (hocPhanDaDangKy.getMaHocPhan().equals(maMonHoc))
                ketQua.set(false);
        });
        return ketQua.get();
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

    @PostMapping("/dang-ky")
    public String dangKyLopHocPhan(@RequestParam("maHocKy") long maHK, @RequestParam("maLHP") long maLHP) {
        SinhVien sinhVien = sinhVienService.findById("18000001");
        LopHocPhan lopHocPhan = lopHocPhanService.findById(maLHP);
        int toiDa = lopHocPhan.getSoLuongDangKyToiDa(), hienTai = lopHocPhan.getSoLuongDangKyHienTai();
        LichHocSinhVien lichHocSinhVien = new LichHocSinhVien();
        lichHocSinhVien.setNgayDangKyHP(new Date());

        if (hienTai + 1 >= toiDa)
            return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;
        lopHocPhan.setSoLuongDangKyHienTai(hienTai + 1);
        lopHocPhanService.saveLopHocPhan(lopHocPhan);

        ctlhpService.findByMaLopHocPhan(maLHP).forEach(chiTietLopHocPhan -> {
            lichHocSinhVien.setChiTietLopHocPhan(chiTietLopHocPhan);
            lichHocSinhVien.setSinhVien(sinhVien);
            lichHocSinhVienService.saveLHSV(lichHocSinhVien);
            KetQuaHocTap ktht = new KetQuaHocTap();
            ktht.setSinhVien(sinhVien);
            ktht.setLopHocPhan(lopHocPhan);
            ketQuaHocTapService.saveKetQuaHT(ktht);
        });
        return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;
    }

    @GetMapping(value = "/xoa-dang-ky")
    String deleteLHSV(@RequestParam("maLHP") long maLHP){
        String maSV = "18000001";
        LopHocPhan lopHocPhan = lopHocPhanService.findById(maLHP);
        int hienTai = lopHocPhan.getSoLuongDangKyHienTai();
        lopHocPhan.setSoLuongDangKyHienTai(hienTai - 1);
        lopHocPhanService.saveLopHocPhan(lopHocPhan);
        KetQuaHocTap ketQuaHocTap = ketQuaHocTapService.findKQHTByMaSVAndMaLHP(maSV, maLHP);

        lichHocSinhVienService.getLichHocByMaSV("18000001").forEach(lichHocSinhVien -> {
            if (lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getMaLHP() == lopHocPhan.getMaLHP()){
                lichHocSinhVienService.deleteLHSV(lichHocSinhVien.getMaLHSV());
                ketQuaHocTapService.deleteKQHT(ketQuaHocTap.getMaKQHT());
            }
        });
        return "redirect:/hoc-phan/dang-ky-hoc-phan";
    }
}
