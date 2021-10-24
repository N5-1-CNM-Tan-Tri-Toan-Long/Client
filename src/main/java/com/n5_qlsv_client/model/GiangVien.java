package com.n5_qlsv_client.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiangVien {

    private long maGV;

    private String tenGV;

    private boolean gioiTinh;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySinh;

    private String email;

    private Khoa khoa;

    private List<ChiTietLopHocPhan> chiTietLopHocPhanList;
}
