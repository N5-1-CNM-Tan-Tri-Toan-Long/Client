package com.n5_qlsv_client.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Khoa {

    private long maKhoa;

    private String tenKhoa;

    private String moTa;

    private List<SinhVien> sinhVienList;

    private List<GiangVien> giangVienList;

    private List<ChuyenNganh> chuyenNganhList;
}
