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
		_beginTime = time.split(' - ')[0];//+' 00:00:00';
		_endTime = time.split(' - ')[1];//+' 23:59:59';
	}else{
		$('#datepicker').val(_beginTime+' - '+_endTime);
	}
	var params = 'beginTime='+_beginTime+'&endTime='+_endTime;
//	var params = 'beginTime=2018-01-01&endTime=2018-01-25&storeOrganId=10040';
	$.ajax({
		url:'/home/d/mofang/order/statistics',
		type:'post',
		data:params,
		dataType:'json',
		success:function(res){
			_draw_1(res.data);
			_draw_2(res.data);
			_draw_3(res.data);
		}
	})
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
		        data: ['已支付', '不需支付','逃单']
		    },
		    series : [
		        {
		            type: 'pie',
		            radius : '65%',
		            center: ['50%', '50%'],
		            selectedMode: 'single',
		            data:[
		                {value:_d.payed.priceAmount, name: '已支付:'+_d.payed.priceAmount+'元'},
//		                {value:_d.inpark.priceAmount, name: '在停:'+_d.inpark.priceAmount},
		                {value:_d.notPay.priceAmount, name: '不需支付:'+_d.notPay.priceAmount+'元'},
		                {value:_d.unpay.priceAmount, name: '逃单:'+_d.unpay.priceAmount+'元'}
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
		        data: ['已支付','不需支付','逃单']
		    },
		    series : [
		        {
		            type: 'pie',
		            radius : '65%',
		            center: ['50%', '50%'],
		            selectedMode: 'single',
		            data:[
			                {value:_d.payed.countAmount, name: '已支付:'+_d.payed.countAmount+'次'},
//			                {value:_d.inpark.priceAmount, name: '在停:'+_d.inpark.priceAmount},
			                {value:_d.notPay.countAmount, name: '不需支付:'+_d.notPay.countAmount+'次'},
			                {value:_d.unpay.countAmount, name: '逃单:'+_d.unpay.countAmount+'次'}
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
		        data: ['已支付','不需支付','逃单']
		    },
		    series : [
		        {
		            type: 'pie',
		            radius : '65%',
		            center: ['50%', '50%'],
		            selectedMode: 'single',
		            data:[
		                    {value:_d.payed.minuteAmount, name: '已支付:'+_d.payed.minuteAmount+'分钟'},
//			                {value:_d.inpark.priceAmount, name: '在停:'+_d.inpark.priceAmount},
			                {value:_d.notPay.minuteAmount, name: '不需支付:'+_d.notPay.minuteAmount+'分钟'},
			                {value:_d.unpay.minuteAmount, name: '逃单:'+_d.unpay.minuteAmount+'分钟'}
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





