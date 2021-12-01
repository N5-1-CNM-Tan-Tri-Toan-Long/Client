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
    var regexdtk1 = /^(\d+)([.]([\d]){0,2})?$/;
    var dtk1 = document.getElementById("txtdtk1").value;

    if (regexdtk1.test(dtk1)) {
        if (dtk1 > 10){
            document.getElementById('btnTinhDiem').disabled = true;
            document.getElementById('s3').innerText = "Phải nhập điểm từ 0.00 đến 10!";
        } else{
            document.getElementById('s3').innerText = "";
            document.getElementById('btnTinhDiem').disabled = false;
        }
    } else {
        document.getElementById('btnTinhDiem').disabled = true;
        document.getElementById('s3').innerText = "Phải nhập điểm từ 0.00 đến 10!";
    }
}
function kiemtradtk2(){
    var regexdtk1 = /^(\d+)([.]([\d]){0,2})?$/;
    var dtk1 = document.getElementById("txtdtk2").value;

    if (regexdtk1.test(dtk1)) {
        if (dtk1 > 10){
            document.getElementById('btnTinhDiem').disabled = true;
            document.getElementById('s4').innerText = "Phải nhập điểm từ 0.00 đến 10!";
        }
        else{
            document.getElementById('s4').innerText = "";
            document.getElementById('btnTinhDiem').disabled = false;
        }
    } else {
        document.getElementById('btnTinhDiem').disabled = true;
        document.getElementById('s4').innerText = "Phải nhập điểm từ 0.00 đến 10!";
    }
}
function kiemtradtk3(){
    var regexdtk1 = /^(\d+)([.]([\d]){0,2})?$/;
    var dtk1 = document.getElementById("txtdtk3").value;

    if (regexdtk1.test(dtk1)) {
        if (dtk1 > 10){
            document.getElementById('btnTinhDiem').disabled = true;
            document.getElementById('s5').innerText = "Phải nhập điểm từ 0.00 đến 10!";
        }
        else{
            document.getElementById('s5').innerText = "";
            document.getElementById('btnTinhDiem').disabled = false;
        }

    } else {
        document.getElementById('btnTinhDiem').disabled = true;
        document.getElementById('s5').innerText = "Phải nhập điểm từ 0.00 đến 10!";
    }
}
function kiemtradgk(){
    var regexdtk1 = /^(\d+)([.]([\d]){0,2})?$/;
    var dtk1 = document.getElementById("txtdgk").value;

    if (regexdtk1.test(dtk1)) {
        if (dtk1 > 10){
            document.getElementById('btnTinhDiem').disabled = true;
            document.getElementById('s6').innerText = "Phải nhập điểm từ 0.00 đến 10!";
        }
        else{
            document.getElementById('s6').innerText = "";
            document.getElementById('btnTinhDiem').disabled = false;
        }

    } else {
        document.getElementById('btnTinhDiem').disabled = true;
        document.getElementById('s6').innerText = "Phải nhập điểm từ 0.00 đến 10!";
    }
}
function kiemtradth1(){
    var regexdtk1 = /^(\d+)([.]([\d]){0,2})?$/;
    var dtk1 = document.getElementById("txtdth1").value;

    if (regexdtk1.test(dtk1)) {
        if (dtk1 > 10){
            document.getElementById('btnTinhDiem').disabled = true;
            document.getElementById('s7').innerText = "Phải nhập điểm từ 0.00 đến 10!";
        }
        else{
            document.getElementById('s7').innerText = "";
            document.getElementById('btnTinhDiem').disabled = false;
        }
    } else {
        document.getElementById('btnTinhDiem').disabled = true;
        document.getElementById('s7').innerText = "Phải nhập điểm từ 0.00 đến 10!";
    }
}
function kiemtradth2(){
    var regexdtk1 = /^(\d+)([.]([\d]){0,2})?$/;
    var dtk1 = document.getElementById("txtdth2").value;

    if (regexdtk1.test(dtk1)) {
        if (dtk1 > 10){
            document.getElementById('btnTinhDiem').disabled = true;
            document.getElementById('s8').innerText = "Phải nhập điểm từ 0.00 đến 10!";
        }
        else{
            document.getElementById('s8').innerText = "";
            document.getElementById('btnTinhDiem').disabled = false;
        }
    } else {
        document.getElementById('btnTinhDiem').disabled = true;
        document.getElementById('s8').innerText = "Phải nhập điểm từ 0.00 đến 10!";
    }
}
function kiemtradck(){
    var regexdtk1 = /^(\d+)([.]([\d]){0,2})?$/;
    var dtk1 = document.getElementById("txtdck").value;

    if (regexdtk1.test(dtk1)) {
        if (dtk1 > 10){
            document.getElementById('btnTinhDiem').disabled = true;
            document.getElementById('s9').innerText = "Phải nhập điểm từ 0.00 đến 10!";
        }
        else{
            document.getElementById('s9').innerText = "";
            document.getElementById('btnTinhDiem').disabled = false;
        }
    } else {
        document.getElementById('btnTinhDiem').disabled = true;
        document.getElementById('s9').innerText = "Phải nhập điểm từ 0.00 đến 10!";
    }
}