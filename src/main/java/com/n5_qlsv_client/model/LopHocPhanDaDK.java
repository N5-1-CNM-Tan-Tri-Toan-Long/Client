package com.n5_qlsv_client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LopHocPhanDaDK {

    private long maLHP;
    private String tenLHP, lopHocDK, trangThaiDK, trangThaiLHP;
    private int soTC, nhomTH;
    private double hocPhi;
    private LocalDate ngayDK;

}
