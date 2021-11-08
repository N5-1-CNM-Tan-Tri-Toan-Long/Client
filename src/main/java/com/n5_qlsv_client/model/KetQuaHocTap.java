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

    private Double diemTK1;

    private Double diemTK2;

    private Double diemTK3;

    private Double diemTH1;

    private Double diemTH2;

    private Double diemHe4;

    private Double diemHe10;

    private Double diemGK;

    private Double diemCK;

    private LopHocPhan lopHocPhan;

    private SinhVien sinhVien;
}