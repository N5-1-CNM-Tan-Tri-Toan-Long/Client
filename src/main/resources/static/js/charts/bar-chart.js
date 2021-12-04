function bieudo(tenHP, diemcuatoi, diemtrungbinhhocphan) {
    (function ($) {
        "use strict";
        var ctx = document.getElementById("barchart1");
        var barchart1 = new Chart(ctx, {
            base:true,
            type: 'bar',
            data: {
                labels: tenHP,
                datasets: [{
                    label: 'Điểm của bạn',
                    data: diemcuatoi,
                    borderWidth: 1,
                    yAxisID: "y-axis-1",
                    backgroundColor: 'rgba(250,108,81, 0.2)',
                    borderColor: 'rgba(250,108,81,1)',
                },
                    {
                        type: "line",
                        label: "Điểm trung bình lớp học phần",
                        data: diemtrungbinhhocphan,
                        fill: false,
                        backgroundColor: 'rgba(253,205,86, 0.2)',
                        borderColor: 'rgba(253,205,86, 1)',
                    }
                ]
            },
            options: {
                responsive: true,

                // title:{
                // 	display:true,
                // 	text:"Bar Chart Multi Axis"
                // },
                tooltips: {
                    mode: 'index',
                    intersect: false
                },
                scales: {
                    xAxes:[{
                        ticks: {
                            display:false
                        }
                    }],
                    yAxes: [{
                        type: "linear",
                        display: false,
                        position: "left",
                        id: "y-axis-1",
                        ticks: {
                            beginAtZero:true
                        }

                    }, {
                        type: "linear",
                        display: false,
                        position: "right",
                        id: "y-axis-2",

                        gridLines: {
                            drawOnChartArea: false
                        }
                    }],
                }
            }
        });


    })(jQuery);
}

$(document).ready(function () {
    $('#cboChonHocKyKQHT').on('change', function () {
        var maHK;
        maHK = $(this).val();
        $.ajax({
            url: "/BieuDoKQHT",
            type: 'POST',
            data: jQuery.param({maHK: maHK}),
            success: function (data) {
                const tenHP = [];
                const diemCuaToi = [];
                const diemTB = [];
                for (var i = 0; i < data.length; i++) {
                    tenHP.push(data[i].tenHP);
                    diemCuaToi.push(data[i].diemCuaToi);
                    diemTB.push(data[i].diemTrungBinhMon);
                }
                bieudo(tenHP, diemCuaToi, diemTB);
                bieudo.replace();
            }
        });
    });
});
