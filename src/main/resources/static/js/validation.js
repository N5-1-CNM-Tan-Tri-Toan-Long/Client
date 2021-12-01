function kiemtrastclt(){
    var regexstclt = /^[0-9]$/;
    var stclt = document.getElementById("txtstclt").value;
    if (regexstclt.test(stclt)) {
        document.getElementById('s1').innerHTML = "";
    } else {
        document.getElementById('s1').innerHTML = "Nhập số (0 đến 9) !!!";
    }
}

function kiemtrastcth(){
    var regexstcth = /^[0-9]$/;
    var stcth = document.getElementById("txtstcth").value;
    if (regexstcth.test(stcth)) {
        document.getElementById('s2').innerHTML = "";
    } else {
        document.getElementById('s2').innerHTML = "Nhập số (0 đến 9) !!!";
    }
}

function kiemtradtk1(){
    var regexdtk1 = /^(0|1|2|3|4|5|6|7|8|9|10)([.][0-9])?$/;
    var dtk1 = document.getElementById("txtdtk1").value;
    if (regexdtk1.test(dtk1)) {
        document.getElementById('s3').innerHTML = "";
    } else {
        document.getElementById('s3').innerHTML = "Điểm không đúng định dạng !!!";
    }
}