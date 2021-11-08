package com.n5_qlsv_client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HocPhanDaDangKy {
    private long maLopHocPhan;
    private String tenLopHocPhan, lopHocDuKien, trangThai;
    private LocalDate ngayDangKy;
    private int soTC;
    private String maHocPhan;
}
