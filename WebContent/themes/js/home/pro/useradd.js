$(function(){
	$('#datepicker').datepicker({
	      autoclose: true,
	      format: 'yyyy-mm-dd',
	      lang: 'ch'
    });
    $("#datepicker").val(new Date().Format("yyyy-MM-dd"));
    draw_1();
})
var myChart_1 = echarts.init(document.getElementById('draw_1'));
function draw_1(){
	var option = {
	    color: ['#3398DB'],
	    tooltip : {trigger: 'axis',axisPointer : {type : 'shadow'}},
	    grid: {left: '3%',right: '4%',bottom: '3%',containLabel: true},
	    xAxis : [{
	            type : 'category',
	            data : ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
	            axisTick: {alignWithLabel: true}
	        }],
	    yAxis : [{type : 'value'}],
	    series : [{
	            name:'增长用户',
	            type:'bar',
	            barWidth: '60%',
	            data:[88, 120, 200, 334, 390, 330, 220]
	        }
	    ]
	};
	myChart_1.setOption(option, true);
}