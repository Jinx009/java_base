$(function(){
	_i = showLoad();
	getData();
})
var _i = -1;
var uuid_array = new Array();
function getData(){
	if(-1==_i){
		_i = showLoad();
	}
	$.ajax({
		url:'/home/d/device',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if('200'==res.code){
				if(res.data!=null){
					for(var i in res.data.result){
						if(res.data.result[i].bluetooth==null||''==res.data.result[i].bluetooth){
							res.data.result[i].bluetooth = '';
						}else{
							uuid_array.push(res.data.result[i].bluetooth);
						}
					}
					getCar();
					new Vue({
		   				  el: '#device',
		   				  data:{devices:res.data.result}
		    		})
				}else{
					layer.alert('暂无数据！');
				}
			}else if('8001'==res.code){
				layer.alert('您无权限操作此项！');
			}else{
//				layer.alert(res.msg);
				layer.msg('以下测试数据');
				res = {"code":"200","msg":"请求成功","data":{"prePage":1,"nextPage":2,"pageRequest":"1||asc","inverseOrder":"desc","pageSize":15,"orderBy":null,"hasNext":true,"totalCount":200,"orderBySetted":false,"hasPre":false,"result":[{"panId":"5152","zMag":"-272","latitude":null,"locationDescription":"停车","available":1,"description":"2018","xMag":"116","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe00005c","locationTag":"停车","frequency":"11","mode":"6","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":44,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 09:59:10","yMag":"-455","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.51","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 09:59:10","areaId":5,"expireTime":"2017-06-15 09:59:10","locationParentId":null,"bluetooth":"2222222","createTime":"2016-09-19 21:52:17","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"257"},{"panId":"5152","zMag":"-169","latitude":null,"locationDescription":"停车","available":0,"description":"2010","xMag":"-40","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe000062","locationTag":"停车","frequency":"11","mode":"11","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":45,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 11:05:49","yMag":"-430","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.48","chargePolicyId":1,"areaDescription":"停车A","updateTime":"2017-06-15 11:05:49","connected":1,"inTime":"2017-06-15 10:13:37","areaId":5,"expireTime":"2017-06-15 10:13:37","locationParentId":null,"bluetooth":"22222224","createTime":"2016-09-19 21:52:17","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"30"},{"panId":"5152","zMag":"-289","latitude":null,"locationDescription":"停车","available":0,"description":"2008","xMag":"1","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe000065","locationTag":"停车","frequency":"11","mode":"11","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":46,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 11:47:31","yMag":"-331","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.50","chargePolicyId":1,"areaDescription":"停车A","updateTime":"2017-06-15 11:47:31","connected":1,"inTime":"2017-06-15 10:29:47","areaId":5,"expireTime":"2017-06-15 10:29:47","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:52:17","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"37"},{"panId":"5152","zMag":"-390","latitude":null,"locationDescription":"停车","available":1,"description":"2009","xMag":"-56","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe000071","locationTag":"停车","frequency":"11","mode":"2","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":47,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 10:43:41","yMag":"-296","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.49","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 10:43:41","areaId":5,"expireTime":"2017-06-15 10:43:41","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:52:17","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"61"},{"panId":"5152","zMag":"-232","latitude":null,"locationDescription":"停车","available":1,"description":"2013","xMag":"-56","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe00007a","locationTag":"停车","frequency":"11","mode":"2","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":48,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 10:10:33","yMag":"-346","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.47","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 10:10:33","areaId":5,"expireTime":"2017-06-15 10:10:33","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:52:41","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"68"},{"panId":"5152","zMag":"-303","latitude":null,"locationDescription":"停车","available":1,"description":"2035","xMag":"-16","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe00008f","locationTag":"停车","frequency":"11","mode":"2","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":49,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 09:46:27","yMag":"220","parentMac":"040000fffe000011","locationName":"停车","batteryVoltage":"3.50","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 09:46:27","areaId":5,"expireTime":"2017-06-15 09:46:27","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:52:41","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"52"},{"panId":"5152","zMag":"-351","latitude":null,"locationDescription":"停车","available":1,"description":"2007","xMag":"-96","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe000098","locationTag":"停车","frequency":"11","mode":"2","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":50,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 09:43:18","yMag":"-317","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.48","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 09:43:18","areaId":5,"expireTime":"2017-06-15 09:43:18","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:52:41","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"147"},{"panId":"5152","zMag":"-445","latitude":null,"locationDescription":"停车","available":1,"description":"2020","xMag":"-50","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe0000ae","locationTag":"停车","frequency":"11","mode":"2","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":51,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 09:35:52","yMag":"-340","parentMac":"0004e2ea3551","locationName":"停车","batteryVoltage":"3.47","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 09:35:52","areaId":5,"expireTime":"2017-06-15 09:35:52","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:52:41","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"219"},{"panId":"5152","zMag":"-105","latitude":null,"locationDescription":"停车","available":1,"description":"2023","xMag":"145","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe0000c4","locationTag":"停车","frequency":"11","mode":"2","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":52,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 08:52:03","yMag":"37","parentMac":"0004e2ea3551","locationName":"停车","batteryVoltage":"3.46","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 08:52:03","areaId":5,"expireTime":"2017-06-15 08:52:03","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:53:21","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"408"},{"panId":"5152","zMag":"-410","latitude":null,"locationDescription":"停车","available":0,"description":"2024","xMag":"-61","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe0000df","locationTag":"停车","frequency":"11","mode":"16","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":53,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 13:16:36","yMag":"159","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.47","chargePolicyId":1,"areaDescription":"停车A","updateTime":"2017-06-15 13:16:36","connected":1,"inTime":"2017-06-15 10:25:34","areaId":5,"expireTime":"2017-06-15 10:25:34","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:53:21","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"132"},{"panId":"5152","zMag":"-293","latitude":null,"locationDescription":"停车","available":1,"description":"2014","xMag":"-68","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe0000e1","locationTag":"停车","frequency":"11","mode":"2","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":54,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 14:27:02","yMag":"-360","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.48","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 14:27:02","areaId":5,"expireTime":"2017-06-15 14:27:02","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:53:21","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"210"},{"panId":"5152","zMag":"-287","latitude":null,"locationDescription":"停车","available":1,"description":"2021","xMag":"-131","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe0000f7","locationTag":"停车","frequency":"11","mode":"2","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":55,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 09:35:49","yMag":"-349","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.48","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 09:35:49","areaId":5,"expireTime":"2017-06-15 09:35:49","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:53:43","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"22"},{"panId":"5152","zMag":"-431","latitude":null,"locationDescription":"停车","available":1,"description":"2017","xMag":"-144","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe00010f","locationTag":"停车","frequency":"11","mode":"2","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":56,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 09:16:35","yMag":"-311","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.48","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 09:16:35","areaId":5,"expireTime":"2017-06-15 09:16:35","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:53:43","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"261"},{"panId":"5152","zMag":"-396","latitude":null,"locationDescription":"停车","available":1,"description":"2011","xMag":"7","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe00013f","locationTag":"停车","frequency":"11","mode":"2","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":57,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 10:01:02","yMag":"-298","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.50","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 10:01:02","areaId":5,"expireTime":"2017-06-15 10:01:02","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:54:13","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"178"},{"panId":"5152","zMag":"-100","latitude":null,"locationDescription":"停车","available":1,"description":"2012","xMag":"4","type":"0","areaTag":"1","heartBeatInterval":"30","routerMac":"0004e2ea3551","mac":"020000fffe000141","locationTag":"停车","frequency":"11","mode":"11","uid":"","areaName":"停车A","locationId":14,"locationLevel":1,"areaLevel":1,"hardwareVersion":null,"id":58,"firmwareVersion":"791","addr":null,"channelId":"2","longitude":null,"lastSeenTime":"2017-06-15 13:49:22","yMag":"-343","parentMac":"040000fffe000012","locationName":"停车","batteryVoltage":"3.48","chargePolicyId":1,"areaDescription":"停车A","updateTime":null,"connected":1,"inTime":"2017-06-15 13:49:22","areaId":5,"expireTime":"2017-06-15 13:49:22","locationParentId":null,"bluetooth":"","createTime":"2016-09-19 21:54:13","noticeFlag":3,"currentMemberId":null,"paid":3,"baseEnergy":"76"}],"pageNo":1,"autoCount":false,"totalPages":14,"first":0,"order":"asc"}};
				for(var i in res.data.result){
					if(res.data.result[i].bluetooth==null||''==res.data.result[i].bluetooth){
						res.data.result[i].bluetooth = '';
					}else{
						uuid_array.push(res.data.result[i].bluetooth);
					}
				}
				getCar();
				new Vue({
	   				  el: '#device',
	   				  data:{devices:res.data.result}
	    		})
			}
		}
	})
}
var _index = 0;
function getCar(){
	if(_index<uuid_array.length){
		$.ajax({
			url:'/home/d/carNo?v='+uuid_array[_index],
			type:'post',
			data:'uuid='+uuid_array[_index],
			dataType:'json',
			ansyc:true,
			success:function(res){
				if('200'==res.code&&res.data!=null){
					setCarNo(uuid_array[_index],res.data);
					_index++;
					getCar();
				}
			}
		})
	}
}
function setCarNo(a,b){
	$('#car'+a).html(b);
}