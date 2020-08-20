function _getData(){
	var parkName = $('#parkName').val();
	if(parkName==''){
		layer.alert('请输入mac或者位置！');
	}else{
		var params ='parkName='+parkName+'&type='+$('#type').val();
		$.ajax({
			url:'/iot/device/lostList',
			type:'post',
			data:params,
			dataType:'json',
			success:function(res){
				if(res.data!=null){
					$('#datas').show();
					for(var i in res.data){
						res.data[i].num = parseInt(i)+1;
						res.data[i].updateTime = toDateTime(res.data[i].updateTime);
						res.data[i].dataTime = toDateTime(res.data[i].dataTime);
					}
					if ('' == _d) {
						_d = new Vue({
							el : '#datas',
							data : {
								datas : res.data
							}
						})
					} else {
						_d.datas = res.data
					}
				}else{
					layer.alert('查无数据！');
				}
			}
		})
	}
}
var _d = '';
function update(_e){
	var id = $(_e).attr('id');
	var parkName = $('#p'+id).val();
	var params = 'id='+id+'&parkName='+parkName;
	$.ajax({
		url:'/iot/device/updateParkName',
		type:'post',
		data:params,
		dataType:'json',
		success:function(res){
			if(res.code==200){
				layer.alert('修改成功！');
			}else{
				layer.alert(res.msg);
			}
		}
	})
}





