$(function(){
	_getData();
})
function _getData(){
	$.ajax({
		url:'/home/cloud/accessControl/log?p=1',
		dataType:'json',
		type:'post',
		success:function(res){
			for(var i in res.data.list){
				res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
			}
			if(res.data.list[0].openDoorPhotoList.indexOf(',') >= 0){
				$('#img').attr('src','http://'+res.data.list[0].photoHost+'/'+res.data.list[0].openDoorPhotoList.split(',')[0]);
			}else{
				$('#img').attr('src','http://'+res.data.list[0].photoHost+'/'+res.data.list[0].openDoorPhotoList);
			}
			$('#name').html(res.data.list[0].personnelName);
			var type = '进门';
			if(res.data.list[0].direction==2){
				type = '出门';
			}
			$('#way').html(type);
			$('#time').html(res.data.list[0].createTime);
			$('#deviceName').html(res.data.list[0].deviceName);
			new Vue({
				el:'#log',
				data:{
					logs:res.data.list
				}
			})
		}
	})
}