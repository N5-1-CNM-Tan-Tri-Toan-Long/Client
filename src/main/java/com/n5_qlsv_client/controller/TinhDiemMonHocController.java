package com.n5_qlsv_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tinh-diem-mon-hoc")
public class TinhDiemMonHocController {

    @GetMapping
    public String tinhDiemMonHoc(Model model){
        model.addAttribute("TrangHienTai", "Tính điểm môn học");
        return "tinh-diem-mon-hoc";
    }

    @PostMapping ("/tinh-diem")
    public String tinhDiem(Model model, @RequestParam(value = "TCLT", defaultValue = "0") int TCLT, @RequestParam(value = "TCTH", defaultValue = "0") int TCTH,
                           @RequestParam(value = "DiemTK1", defaultValue = "") Double diemTK1, @RequestParam(value = "DiemTK2", defaultValue = "") Double diemTK2,
                           @RequestParam(value = "DiemTK3", defaultValue = "") Double diemTK3, @RequestParam(value = "DiemGK", defaultValue = "") Double diemGK,
                           @RequestParam(value = "DiemTH1", defaultValue = "") Double diemTH1, @RequestParam(value = "DiemTH2", defaultValue = "") Double diemTH2,
                           @RequestParam(value = "DiemCK", defaultValue = "") Double diemCK){

        Double tk = null;
        if (diemTK1 != null){
            if(diemTK2 != null && diemTK3 != null){
                tk = (diemTK1 + diemTK2 + diemTK3)/3;
            }else if(diemTK2 != null){
                tk = (diemTK1 + diemTK2)/2;
            }else {
                tk = diemTK1;
            }
        }else {
            tk = null;
        }
        double gk = diemGK;
        double ck = diemCK;
        double diemHe10 = ((tk*20) + (gk * 30) + (ck * 50 ))/100;
        int soTCLT = TCLT;
        int soTCTH = TCTH;
        int soTC = soTCLT + soTCTH;
        double diemTongKet = 0;
        if(diemTH1 == null && diemTH2 == null){ // môn không có thực hành
            double diemHe10LT = (double) Math.round(diemHe10 * 100) / 100;
            diemTongKet = (double) Math.round(diemHe10LT * 10) / 10;
            model.addAttribute("diemTongKet", diemTongKet);
        }else { // môn có thực hành
            if(diemTH1 != null){
                if(diemTH2 == null){
                    double diemMonTH = ((diemHe10 * soTCLT) + (diemTH1 * soTCTH)) / soTC;
                    double diemMotMonTH = (double) Math.round(diemMonTH * 100) / 100;
                    diemTongKet = (double) Math.round(diemMotMonTH * 10) / 10;
                    model.addAttribute("diemTongKet", diemTongKet);
                }else {
                    double diemTBTH = (diemTH1 + diemTH2)/2;
                    double diemMonTH = ((diemHe10 * soTCLT) + (diemTBTH * soTCTH)) / soTC;
                    double diemHaiMonTH = (double) Math.round(diemMonTH * 100) / 100;
                    diemTongKet = (double) Math.round(diemHaiMonTH * 10) / 10;
                    model.addAttribute("diemTongKet", diemTongKet);
                }
            }
        }
        //điểm hệ số 4
        if(diemTongKet >= 9){
            model.addAttribute("diemHe4", 4.0);
            model.addAttribute("diemChu", "A+");
            model.addAttribute("xepLoai", "Xuất sắc");
        }else if(diemTongKet > 8.4 && diemTongKet <= 8.9){
            model.addAttribute("diemHe4", 3.8);
            model.addAttribute("diemChu", "A");
            model.addAttribute("xepLoai", "Giỏi");
        }else if(diemTongKet > 7.9 && diemTongKet <= 8.4){
            model.addAttribute("diemHe4", 3.5);
            model.addAttribute("diemChu", "B+");
            model.addAttribute("xepLoai", "Khá");
        }else if(diemTongKet > 6.9 && diemTongKet <= 7.9){
            model.addAttribute("diemHe4", 3.0);
            model.addAttribute("diemChu", "B");
            model.addAttribute("xepLoai", "Khá");
        }else if(diemTongKet > 5.9 && diemTongKet <= 6.9){
            model.addAttribute("diemHe4", 2.5);
            model.addAttribute("diemChu", "C+");
            model.addAttribute("xepLoai", "Trung Bình");
        }else if(diemTongKet > 5.4 && diemTongKet <= 5.9){
            model.addAttribute("diemHe4", 2.0);
            model.addAttribute("diemChu", "C");
            model.addAttribute("xepLoai", "Trung Bình");
        }else if(diemTongKet > 4.9 && diemTongKet <= 5.4){
            model.addAttribute("diemHe4", 1.5);
            model.addAttribute("diemChu", "D+");
            model.addAttribute("xepLoai", "Trung Bình Yếu");
        }
        else if(diemTongKet > 3.9 && diemTongKet <= 4.9){
            model.addAttribute("diemHe4", 1.0);
            model.addAttribute("diemChu", "D");
            model.addAttribute("xepLoai", "Trung Bình Yếu");
        }else {
            model.addAttribute("diemHe4", 0.0);
            model.addAttribute("diemChu", "F");
            model.addAttribute("xepLoai", "Kém");
        }
        return "tinh-diem-mon-hoc";
    }

}
