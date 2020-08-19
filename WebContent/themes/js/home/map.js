$(function(){
	_getData();
})
function _getData(){
		$.ajax({
			url:'/iot/device/map',
			dataType:'json',
			type:'post',
			success:function(res){
					_d = new Vue({
						el:'#datas',
						data:{
							datas:res.data
						}
					})
			}
		})
}

