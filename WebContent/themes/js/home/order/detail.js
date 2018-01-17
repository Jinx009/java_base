$(function() {
	_getData();
//	laydate.render({
//	  elem: '#datepicker'
//	  ,type: 'datetime'
//	  ,range: true
//	}); 
	laydate.render({
		elem : '#datepicker',
		range : true
	});
})
var myChart = echarts.init(document.getElementById('draw_1'));
var myChart2 = echarts.init(document.getElementById('draw_2'));
var myChart3 = echarts.init(document.getElementById('draw_3'));
function _getData(){
	var time = $('#datepicker').val();
	var timestamp = (new Date()).valueOf();
	var time2 = timestamp-60*60*24*1000;
	var _beginTime = toDate(time2),_endTime = toDate(timestamp);
	if(time!=null&&time!=''){
		_beginTime = time.split(' - ')[0];
		_endTime = time.split(' - ')[1];
	}
	_draw_1('');
	_draw_2('');
	_draw_3('');
//	var params = 'beginTime='+_beginTime+'&endTime='+_endTime;
//	$.ajax({
//		url:'/home/d/mofang/order/statistics',
//		type:'post',
//		data:params,
//		dataType:'json',
//		success:function(res){
//			_draw(res.data);
//		}
//	})
}
function _darw(_d){
	_draw_1();
}
function _draw_1(_d){
	option = {
		    title: {
		        text: '',
		        subtext: '',
		        left: 'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        // orient: 'vertical',
		        // top: 'middle',
		        bottom: 10,
		        left: 'center',
		        data: ['已支付', '在停','不需支付','逃单']
		    },
		    series : [
		        {
		            type: 'pie',
		            radius : '65%',
		            center: ['50%', '50%'],
		            selectedMode: 'single',
		            data:[
		          
		                {value:535, name: '已支付'},
		                {value:510, name: '在停'},
		                {value:634, name: '不需支付'},
		                {value:735, name: '逃单'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
	   myChart.setOption(option,true);
}
function _draw_2(_d){
	option1 = {
		    title: {
		        text: '',
		        subtext: '',
		        left: 'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        // orient: 'vertical',
		        // top: 'middle',
		        bottom: 10,
		        left: 'center',
		        data: ['已支付', '在停','不需支付','逃单']
		    },
		    series : [
		        {
		            type: 'pie',
		            radius : '65%',
		            center: ['50%', '50%'],
		            selectedMode: 'single',
		            data:[
		          
		                {value:535, name: '已支付'},
		                {value:510, name: '在停'},
		                {value:634, name: '不需支付'},
		                {value:735, name: '逃单'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
	   myChart2.setOption(option1,true);
}
function _draw_3(_d){
	option2 = {
		    title: {
		        text: '',
		        subtext: '',
		        left: 'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        // orient: 'vertical',
		        // top: 'middle',
		        bottom: 10,
		        left: 'center',
		        data: ['已支付', '在停','不需支付','逃单']
		    },
		    series : [
		        {
		            type: 'pie',
		            radius : '65%',
		            center: ['50%', '50%'],
		            selectedMode: 'single',
		            data:[
		          
		                {value:535, name: '已支付'},
		                {value:510, name: '在停'},
		                {value:634, name: '不需支付'},
		                {value:735, name: '逃单'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
	   myChart3.setOption(option,true);
}
var option = {};
var option1 = {};
var option2 = {};





