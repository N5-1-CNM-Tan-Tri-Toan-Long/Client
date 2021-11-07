package com.n5_qlsv_client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CTLHPChoDangKy {
    private String tietHoc, coSo, phongHoc, tenGiaoVien;
    private LocalDate ngayBatDau, ngayKetThuc;
}
