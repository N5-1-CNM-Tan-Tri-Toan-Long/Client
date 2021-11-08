package com.n5_qlsv_client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CTLHPChoDangKy {
    private Integer nhomTH;
    private String tietHoc, coSo, phongHoc, tenGiaoVien;
    private LocalDate ngayBatDau, ngayKetThuc;

}
