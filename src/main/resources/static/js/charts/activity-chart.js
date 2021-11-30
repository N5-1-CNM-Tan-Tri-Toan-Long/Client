
var soTCTheoCN = $('#soTCTheoCN').text();
var soTCDaHoc = $('#soTCDaHoc').text();

var like = soTCTheoCN, unlike = soTCDaHoc;

var ratioLike = like / 100;
var ratioUnLike = unlike / 100;

var pie = d3.layout.pie()
    .value(function (d) { return d })
    .sort(null);

var w = 250, h = 250;

var outerRadius = (w / 2) - 10;
var innerRadius = outerRadius - 30;


// var color = d3.scale.ordinal()
//         .range(['#91f089']);

var arcLikeBG = d3.svg.arc()
    .innerRadius(innerRadius)
    .outerRadius(outerRadius)
    .startAngle(0)
    .endAngle(2 * Math.PI);


var arcLikeFG = d3.svg.arc()
    .innerRadius(innerRadius)
    .outerRadius(outerRadius)
    .cornerRadius(20)
    .startAngle(-.05);

var arcUnlikeBG = d3.svg.arc()
    .innerRadius(innerRadius - 30)
    .outerRadius(innerRadius)
    .startAngle(0)
    .endAngle(2 * Math.PI);


var arcUnlikeFG = d3.svg.arc()
    .innerRadius(innerRadius - 30)
    .outerRadius(innerRadius)
    .cornerRadius(20)
    .startAngle(-.05);

var svg = d3.select("#chart")
    .append("svg")
    .attr({
        width: w,
        height: h,
        class: 'shadow'
    }).append('g')
    .attr({
        transform: 'translate(' + w / 2 + ',' + h / 2 + ')'
    });

// var pathLikeBG=svg.append('path')
//         .attr({
//             d:arcLikeBG
//         })
//         .style({
//             fill:'#8aeba1'
//         });


var pathLikeFG = svg.append('path')
    .datum({ endAngle: 0 })
    .attr({
        d: arcLikeFG
    })
    .style({
        fill: '#3760e6'
    });


var pathUnlikeBG = svg.append('path')
    .attr({
        d: arcUnlikeBG
    })
    .style({
        fill: '#abebba',
    });


var pathUnlikeFG = svg.append('path')
    .datum({ endAngle: 0 })
    .attr({
        d: arcUnlikeFG
    })
    .style({
        fill: '#0dbd37'
    });


var arcTween = function (transition, newAngle) {
    transition.attrTween("d", function (d) {
        var interpolate = d3.interpolate(d.endAngle, newAngle);

        return function (t) {
            d.endAngle = interpolate(t);

            return arcLikeFG(d);
        };
    });
};

var arcTweenUnlike = function (transition, newAngle) {
    transition.attrTween("d", function (d) {
        var interpolate = d3.interpolate(d.endAngle, newAngle);

        return function (t) {
            d.endAngle = interpolate(t);
            return arcUnlikeFG(d);
        };
    });
};


var animate = function () {
    pathLikeFG.transition()
        .duration(750)
        .ease('cubic')
        .call(arcTween, ((2 * Math.PI)) * ratioLike);

    pathUnlikeFG.transition()
        .duration(750)
        .ease('quad')
        .call(arcTweenUnlike, ((2 * Math.PI)) * ratioUnLike);


};

setTimeout(animate, 0);

