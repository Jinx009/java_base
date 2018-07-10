function _save(){
	var _name = $('#name').val();
	var _mobilePhone = $('#mobilePhone').val();
	var _pwd = $('pwd').val();
	var _plateNumber = $('#plateNumber').val();
	var _params = 'name='+_name+'&mobilePhone='+_mobilePhone+'&pwd='+_pwd+'&plateNumber='+_plateNumber;
	if(_name==''||_mobilePhone==''||_pwd==''){
		layer.alert('请完善除车牌号外每一项选项！');
	}else if(!testNumber(_mobilePhone)){
		layer.alert('手机号码不合法！');
	}else{
		$.ajax({
			url:'/home/d/pro_driver/save',
			type:'post',
			data:_params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('添加成功！',function(){
						_open('l1','/home/p/pro_driver');
					})
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}
}
function testNumber(_num){
	var reg = new RegExp("^[0-9]*$");
	if(reg.test(_num)&&_num.length==11){
		return true;
	}
	return false;
}