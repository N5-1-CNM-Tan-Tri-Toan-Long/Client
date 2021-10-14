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
public class ChiTietLopHocPhan implements Serializable {

    private long maCTLHP;

    private String moTaLichHoc;

    private String moTa;

    private String phong;

    private String dayNha;

    private int nhomTH;

    private GiangVien giangVien;

    private LopHocPhan lopHocPhan;
}
