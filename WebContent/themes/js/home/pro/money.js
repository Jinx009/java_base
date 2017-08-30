$(function(){
	draw_1();
	draw_2();
	draw_3();
	draw_4();
	draw_5();
	draw_6();
})
var myChart_1 = echarts.init(document.getElementById('draw_1'));
var myChart_2 = echarts.init(document.getElementById('draw_2'));
var myChart_3 = echarts.init(document.getElementById('draw_3'));
var myChart_4 = echarts.init(document.getElementById('draw_4'));
var myChart_5 = echarts.init(document.getElementById('draw_5'));
var myChart_6 = echarts.init(document.getElementById('draw_6'));
function draw_1(){
	var option = {
		    tooltip : {formatter: "理论营收(50万元)</br>实际营收(40万元)</br>占比: {c}%"},
		    toolbox: {feature: {restore: {},saveAsImage: {}}},
		    series: [{name: '创收占比',type: 'gauge',detail: {formatter:'{value}%'},data: [{value: 80, name: '\n营收占比'}]}]
		};
	 myChart_1.setOption(option, true);
}
function draw_2(){
	var option = {
		    tooltip : {formatter: "支付宝支付(20万元)</br>占比：{c}%"},
		    toolbox: {feature: {restore: {},saveAsImage: {}}},
		    series: [{name: '支付宝',type: 'gauge',detail: {formatter:'{value}%'}, data: [{value: 50, name: '\n支付宝'}]}]
		};
	 myChart_2.setOption(option, true);
}
function draw_3(){
	var option = {
		    tooltip : {formatter: "微信支付(10万元): {c}%"},
		    toolbox: {feature: {restore: {},saveAsImage: {}}},
		    series: [{name: '微信支付',type: 'gauge',detail: {formatter:'{value}%'},data: [{value: 25, name: '\n微信支付'}]}]
		};
	 myChart_3.setOption(option, true);
}
function draw_4(){
	var option = {
		    tooltip : {formatter: "银行卡支付(10万元): {c}%"},
		    toolbox: {feature: {restore: {},saveAsImage: {}}},
		    series: [{name: '银行卡支付',type: 'gauge',detail: {formatter:'{value}%'},data: [{value: 12.5, name: '\n银行卡'}]}]
		};
	 myChart_4.setOption(option, true);
}
function draw_5(){
	var option = {
		    tooltip : {formatter: "现金支付(1万元): {c}%"},
		    toolbox: {feature: {restore: {},saveAsImage: {}}},
		    series: [{name: '现金支付',type: 'gauge',detail: {formatter:'{value}%'},data: [{value: 2.5, name: '\n现金支付'}]}]
		};
	 myChart_5.setOption(option, true);
}
function draw_6(){
	var option = {
		    tooltip : {formatter: "逃单(4万元): {c}%"},
		    toolbox: {feature: {restore: {},saveAsImage: {}}},
		    series: [{name: '逃单',type: 'gauge',detail: {formatter:'{value}%'},data: [{value: 10, name: '\n逃单'}]}]
		};
	 myChart_6.setOption(option, true);
}
