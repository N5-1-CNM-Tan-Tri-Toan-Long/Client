package com.n5_qlsv_client.controller;

import com.n5_qlsv_client.model.*;
import com.n5_qlsv_client.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    private HocPhanKhungService hocPhanKhungService;

    private Set<HocPhanDaDangKy> listHocPhanDaDangKy;
    private Long maHocKy;

    @GetMapping
    public String hocKyLopHocPhan(Model model, Long maHK, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        SinhVien sinhVien = sinhVienService.findById(loginedUser.getUsername());
        model.addAttribute("tensinhvien", sinhVien.getTenSV());
        
        String maSV = loginedUser.getUsername();
        if (maHK != null) {
            listHocPhanDaDangKy = new HashSet<>();
            lichHocSinhVienService.getLichHocByMaSV(maSV).forEach(lichHocSinhVien -> {
                if (lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocKy().getMaHK() == maHK)
                    listHocPhanDaDangKy.add(new HocPhanDaDangKy(lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getMaLHP(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenLHP(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTenVietTat(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getTrangThai(),
                            convertToLocalDate(lichHocSinhVien.getNgayDangKyHP()),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCLT() +
                                    lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getSoTCTH(),
                            lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getHocPhan().getMaHocPhan()));
            });
            model.addAttribute("listHocPhanDaDK", listHocPhanDaDangKy);

            SinhVien sv = sinhVienService.findById(maSV);
            List<HocPhanKhung> listHocPhanKhung = hocPhanKhungService.findAllByChuyenNganh(sv.getChuyenNganh().getMaChuyenNganh());
            Set<HocPhan> listHP = new HashSet<>();
            hocPhanService.findHPByMaHK(maHK).forEach(hocPhan -> {
                if (checkHocPhanTrongList(hocPhan.getMaHocPhan(), listHocPhanDaDangKy) && checkHocPhanKhung(hocPhan.getMaHocPhan(), listHocPhanKhung))
                    listHP.add(hocPhan);
            });
            model.addAttribute("danhsachHP", listHP);
        }
        model.addAttribute("HKLHP", hocKyLHPS(maSV));
        maHocKy = maHK;
        model.addAttribute("maHK", maHocKy);
        return "dang-ky-hoc-phan";
    }

    private boolean checkHocPhanKhung(String maHocPhan, List<HocPhanKhung> listHocPhanKhung) {
        AtomicBoolean ketQua = new AtomicBoolean(false);
        listHocPhanKhung.forEach(hocPhanDaDangKy -> {
            if (hocPhanDaDangKy.getHocPhan().getMaHocPhan().equals(maHocPhan))
                ketQua.set(true);
        });
        return ketQua.get();
    }

    List<HocKyLHP> hocKyLHPS(String maSV) {
        //học kỳ học phần
        List<HocKyLHP> hocKyList = new ArrayList<>();
        SinhVien sv = sinhVienService.findById(maSV);
        int gioiHanHK = convertToLocalDate(sv.getNgayVaoTruong()).getYear() + 6;
        hocKyService.getAllHocKys().forEach(hocKy -> {
            if (convertToLocalDate(sv.getNgayVaoTruong()).getYear() <= hocKy.getNamBatDau() && gioiHanHK > hocKy.getNamBatDau())
                hocKyList.add(new HocKyLHP(hocKy.getMaHK(), hocKy.getMoTa(), hocKy.getNamBatDau(),
                        hocKy.getNamKetThuc(), hocKy.getThuTuHocKy()));
        });
        return hocKyList;
    }

    private boolean checkHocPhanTrongList(String maHP, Set<HocPhanDaDangKy> list) {
        AtomicBoolean ketQua = new AtomicBoolean(true);
        list.forEach(hocPhanDaDangKy -> {
            if (hocPhanDaDangKy.getMaHocPhan().equals(maHP))
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
            list.add(new CTLHPChoDangKy(chiTietLopHocPhan.getNhomTH(), chiTietLopHocPhan.getTietHoc(), chiTietLopHocPhan.getCoSo(),
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
    public String dangKyLopHocPhan(@RequestParam("maHocKy") long maHK, @RequestParam("maLHP") long maLHP,
                                   @RequestParam(name = "nhomTH", defaultValue = "0") int nhomTH, Principal principal) {

        if (!lopHocPhanService.findById(maLHP).getTrangThai().equalsIgnoreCase("Chờ sinh viên đăng ký"))
            return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;

        LopHocPhan lopHocPhan = lopHocPhanService.findById(maLHP);
        int toiDa = lopHocPhan.getSoLuongDangKyToiDa(), hienTai = lopHocPhan.getSoLuongDangKyHienTai();
        if (hienTai + 1 > toiDa)
            return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;

        User loginUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginUser.getUsername();
        SinhVien sinhVien = sinhVienService.findById(maSV);
        if (!checkHocPhanKhung(lopHocPhan.getHocPhan().getMaHocPhan(),
                hocPhanKhungService.findAllByChuyenNganh(sinhVien.getChuyenNganh().getMaChuyenNganh())))
            return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;

        LichHocSinhVien lichHocSinhVien = new LichHocSinhVien();
        lichHocSinhVien.setNgayDangKyHP(new Date());
        List<ChiTietLopHocPhan> cthhp = ctlhpService.findByMaLopHocPhan(maLHP);
        if (kiemTraLichTrung(cthhp, lichHocSinhVienService.getLichHocByMaSV(maSV), nhomTH).size() > 0)
            return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;


        lopHocPhan.setSoLuongDangKyHienTai(hienTai + 1);
        lopHocPhanService.saveLopHocPhan(lopHocPhan);

        cthhp.forEach(chiTietLopHocPhan -> {
            if (chiTietLopHocPhan.getNhomTH() == nhomTH || chiTietLopHocPhan.getNhomTH() == 0) {
                lichHocSinhVien.setChiTietLopHocPhan(chiTietLopHocPhan);
                lichHocSinhVien.setSinhVien(sinhVien);
                lichHocSinhVienService.saveLHSV(lichHocSinhVien);
            }
        });
        return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;
    }

    @GetMapping(value = "/xoa-dang-ky")
    public String deleteLHSV(@RequestParam("maLHP") long maLHP, @RequestParam("maHocKy") long maHK, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginedUser.getUsername();
        LopHocPhan lopHocPhan = lopHocPhanService.findById(maLHP);

        if (!lopHocPhan.getTrangThai().equalsIgnoreCase("Chờ sinh viên đăng ký") && !lopHocPhan.getTrangThai().equalsIgnoreCase("Chờ hủy lớp"))
            return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;

        int hienTai = lopHocPhan.getSoLuongDangKyHienTai();
        lopHocPhan.setSoLuongDangKyHienTai(Math.max(hienTai - 1, 0));

        lichHocSinhVienService.getLichHocByMaSV(maSV).forEach(lichHocSinhVien -> {
            if (lichHocSinhVien.getChiTietLopHocPhan().getLopHocPhan().getMaLHP() == lopHocPhan.getMaLHP()) {
                lichHocSinhVienService.deleteLHSV(lichHocSinhVien.getMaLHSV());
                lopHocPhanService.saveLopHocPhan(lopHocPhan);
            }
        });
        return "redirect:/hoc-phan/dang-ky-hoc-phan?maHK=" + maHK;
    }

    @PostMapping("/kiem-tra-trung")
    @ResponseBody
    public Set<HocPhanTrung> kiemTraHocPhanTrung(@RequestParam("maLHP") long maLHP, @RequestParam(name = "nhomTH",
            defaultValue = "0") int nhomTH, Principal principal) {
        User loginUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginUser.getUsername();
        Set<HocPhanTrung> hocPhanTrungs = new HashSet<>();
        hocPhanTrungs.add(new HocPhanTrung("Môn học không được phép đăng ký", "", "", "", ""));
        LopHocPhan lophocPhan = lopHocPhanService.findById(maLHP);
        if (!lophocPhan.getTrangThai().equalsIgnoreCase("Chờ sinh viên đăng ký") ||
                lophocPhan.getSoLuongDangKyHienTai() == lophocPhan.getSoLuongDangKyToiDa())
            return hocPhanTrungs;
        List<ChiTietLopHocPhan> listCTHP = ctlhpService.findByMaLopHocPhan(maLHP);
        List<LichHocSinhVien> listLH = lichHocSinhVienService.getLichHocByMaSV(maSV);
        return kiemTraLichTrung(listCTHP, listLH, nhomTH);
    }

    private Set<HocPhanTrung> kiemTraLichTrung(List<ChiTietLopHocPhan> listCTHP, List<LichHocSinhVien> listLH, int nhomTH) {
        Set<HocPhanTrung> hocPhanTrungs = new HashSet<>();
        listCTHP.forEach(chiTietLopHocPhan -> {
            listLH.forEach(lichHocSinhVien -> {
                ChiTietLopHocPhan cthp = lichHocSinhVien.getChiTietLopHocPhan();
                if (kiemTraNgayBatDau(chiTietLopHocPhan.getNgayBatDau(), cthp.getNgayBatDau(), cthp.getNgayKetThuc()))
                    if (chiTietLopHocPhan.getNhomTH() == nhomTH || chiTietLopHocPhan.getNhomTH() == 0) {
                        List<Integer> tietHoc1s = LichHocTheoTuanControler.extractNumbers(chiTietLopHocPhan.getTietHoc());
                        List<Integer> tietHoc2s = LichHocTheoTuanControler.extractNumbers(cthp.getTietHoc());
                        if (tietHoc1s.get(0).equals(tietHoc2s.get(0))) {
                            if (tietHoc1s.get(1) >= tietHoc2s.get(1) && tietHoc1s.get(1) <= tietHoc2s.get(2))
                                hocPhanTrungs.add(new HocPhanTrung(cthp.getLopHocPhan().getTenLHP(),
                                        cthp.getTietHoc(), cthp.getCoSo() + " " + cthp.getPhong(), cthp.getGiangVien().getTenGV(),
                                        convertToLocalDate(cthp.getNgayBatDau()) + " -> " + convertToLocalDate(cthp.getNgayKetThuc())));
                            else {
                                if (tietHoc1s.get(2) >= tietHoc2s.get(1) && tietHoc1s.get(2) <= tietHoc2s.get(2))
                                    hocPhanTrungs.add(new HocPhanTrung(cthp.getLopHocPhan().getTenLHP(),
                                            cthp.getTietHoc(), cthp.getCoSo() + " " + cthp.getPhong(),
                                            cthp.getGiangVien().getTenGV(),
                                            convertToLocalDate(cthp.getNgayBatDau()) + " -> " + convertToLocalDate(cthp.getNgayKetThuc())));
                            }
                        }
                    }
            });
        });
        return hocPhanTrungs;
    }

    private boolean kiemTraNgayBatDau(Date ngayhoc, Date ngayBatDau, Date ngayKetThuc) {
        return !ngayBatDau.after(ngayhoc) && !ngayKetThuc.before(ngayhoc);
    }

    @PostMapping("/xem-lop-hoc-phan")
    @ResponseBody
    public List<ThongTinLopHP> findThongTinLopByMaLHP(@RequestParam("maLHP") long maLHP, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginedUser.getUsername();
        List<ThongTinLopHP> thongTinLopHPS = new ArrayList<>();
        List<LichHocSinhVien> listLH = lichHocSinhVienService.getLichHocByMaSV(maSV);
        ctlhpService.findByMaLopHocPhan(maLHP).forEach(chiTietLopHocPhan -> {
            if (kiemTraCTHPtrongLH(chiTietLopHocPhan.getMaCTLHP(), listLH)) {
                String nhomTH = "Lý thuyết";
                if (chiTietLopHocPhan.getNhomTH() > 0)
                    nhomTH = chiTietLopHocPhan.getNhomTH() + "";
                thongTinLopHPS.add(new ThongTinLopHP(chiTietLopHocPhan.getTietHoc(), nhomTH,
                        chiTietLopHocPhan.getPhong(), chiTietLopHocPhan.getDayNha(), chiTietLopHocPhan.getCoSo(),
                        chiTietLopHocPhan.getGiangVien().getTenGV(),
                        convertToLocalDate(chiTietLopHocPhan.getNgayBatDau()) + " : " + convertToLocalDate(chiTietLopHocPhan.getNgayKetThuc())));
            }
        });
        return thongTinLopHPS;
    }

    private boolean kiemTraCTHPtrongLH(long maCTLHP, List<LichHocSinhVien> listLH) {
        for (LichHocSinhVien lichHocSinhVien : listLH) {
            if (lichHocSinhVien.getChiTietLopHocPhan().getMaCTLHP() == maCTLHP)
                return true;
        }
        return false;
    }

    @GetMapping("/in-lich")
    public String InLHPDaDK(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String maSV = loginedUser.getUsername();
        model.addAttribute("sinhvien", sinhVienService.findById(maSV));
        model.addAttribute("listHocPhanDaDK", listHocPhanDaDangKy);
        model.addAttribute("HKLHP", hocKyLHPS(maSV));
        model.addAttribute("maHK", maHocKy);
        return "in-lop-hoc-phan";
    }
}
