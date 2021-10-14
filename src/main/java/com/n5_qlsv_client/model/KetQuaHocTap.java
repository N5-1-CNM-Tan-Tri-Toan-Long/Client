package com.n5_qlsv_client.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KetQuaHocTap implements Serializable {

    private long maKQHT;

    private String moTa;

    private String xepLoai;

    private String diemChu;

    private double diemTK1;

    private double diemTK2;

    private double diemTK3;

    private double diemTH;

    private double diemHe4;

    private double diemHe10;

    private double diemGK;

    private double diemCK;

    private LopHocPhan lopHocPhan;

    private SinhVien sinhVien;
}