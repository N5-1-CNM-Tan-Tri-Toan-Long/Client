package com.n5_qlsv_client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LichHocTheoTienDo {
    private String maLHP, tenHP, soTC, thu, tiet, phong, nhom, dateBD, dateKT, maGV, tenGV;
}
