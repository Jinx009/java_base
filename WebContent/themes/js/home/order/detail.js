$(function() {
	var _d1 = 80,_d2 = 120;
	var _d3 = 80,_d4 = 120;
	var _d5 = 80,_d6 = 120;
	$('#money').text(_d1);
	$('#money_t').text(_d2);
	$('#times').text(_d3);
	$('#times_t').text(_d4);
	$('#time').text(_d5);
	$('#time_t').text(_d6);
	draw_1(_d1,_d2);
	draw_2(_d3,_d4);
	draw_3(_d5,_d6);
	$('#datepicker').datepicker({
		autoclose : true,
		format : 'yyyy-mm-dd',
		lang : 'ch'
	});
	$('#datepicker1').datepicker({
		autoclose : true,
		format : 'yyyy-mm-dd',
		lang : 'ch'
	});
	$("#datepicker").val(new Date().Format("yyyy-MM-dd"));
	$("#datepicker1").val(new Date().Format("yyyy-MM-dd"));
})
var myChart_1 = echarts.init(document.getElementById('draw_1'));
var myChart_2 = echarts.init(document.getElementById('draw_2'));
var myChart_3 = echarts.init(document.getElementById('draw_3'));
function draw_1(_current,_total) {
	var _p = parseInt(_current/_total*100);
	var option = {
		tooltip : {
			formatter : "涉及金额：("+_current+"元)</br>总金额：("+_total+"元)</br>占比：{c}%"
		},
		toolbox : {
			feature : {
				restore : {},
				saveAsImage : {}
			}
		},
		series : [ {
			name : '金额占比',
			type : 'gauge',
			detail : {
				formatter : '{value}%'
			},
			data : [ {
				value : _p,
				name : '\n金额占比'
			} ]
		} ]
	};
	myChart_1.setOption(option, true);
}
function draw_2(_current,_total) {
	var _p = parseInt(_current/_total*100);
	var option = {
		tooltip : {
			formatter : "涉及次数：("+_current+")</br>总次数：("+_total+")</br>占比：{c}%"
		},
		toolbox : {
			feature : {
				restore : {},
				saveAsImage : {}
			}
		},
		series : [ {
			name : '次数占比',
			type : 'gauge',
			detail : {
				formatter : '{value}%'
			},
			data : [ {
				value : _p,
				name : '\n次数占比'
			} ]
		} ]
	};
	myChart_2.setOption(option, true);
}
function draw_3(_current,_total) {
	var _p = parseInt(_current/_total*100);
	var option = {
		tooltip : {
			formatter : "涉及分钟：("+_current+")</br>总分钟：("+_total+")</br>占比：{c}%"
		},
		toolbox : {
			feature : {
				restore : {},
				saveAsImage : {}
			}
		},
		series : [ {
			name : '时长占比',
			type : 'gauge',
			detail : {
				formatter : '{value}%'
			},
			data : [ {
				value : _p,
				name : '\n时长占比'
			} ]
		} ]
	};
	myChart_3.setOption(option, true);
}