(function ($) {
	"use strict";
	/*----------------------------------------*/
	/*  1.  Bar Chart
	/*----------------------------------------*/

	/*----------------------------------------*/
	/*  2.  Bar Chart vertical
	/*----------------------------------------*/
	var ctx = document.getElementById("barchart1");
	var barchart1 = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: ["Toán cao cấp A1", "Lập trình hướng đối tượng", "Toán rời rạc", "Phát triển ứng dụng", "Tâm lý đại cương", "Lập trình WWW (Java)"],
			datasets: [{
				label: 'Điểm của bạn',
				data: [6, 8, 7.8, 8.5, 7, 6.5],
				borderWidth: 1,
				yAxisID: "y-axis-1",
				backgroundColor: [
					'rgba(255, 99, 132, 0.2)',
					'rgba(255, 99, 132, 0.2)',
					'rgba(255, 99, 132, 0.2)',
					'rgba(255, 99, 132, 0.2)',
					'rgba(255, 99, 132, 0.2)',
					'rgba(255, 99, 132, 0.2)',
				],
				borderColor: [
					'rgba(255,99,132,1)',
					'rgba(255,99,132,1)',
					'rgba(255,99,132,1)',
					'rgba(255,99,132,1)',
					'rgba(255,99,132,1)',
					'rgba(255,99,132,1)',
				],
			},
				{
					type: "line",
					label: "Điểm trung bình lớp học phần",
					data: [7, 7, 8, 6, 8, 5],
					fill: false,
					backgroundColor: [
						'rgba(238, 130, 238, 0.2)'
					],
					borderColor: [
						'rgba(238, 130, 238, 1)'
					],
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
				intersect: true
			},
			scales: {
				yAxes: [{
					type: "linear",
					display: false,
					position: "left",
					id: "y-axis-1",

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