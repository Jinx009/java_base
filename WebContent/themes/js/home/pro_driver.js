$(function(){
	_getData(0,1);
})
var _d = '';
var  _nowPage = 0,_max = 0;
var _bd = [];
function _getData(_type,_index){
	var _data = {};
	_data.p = _getPage(_type,_index);
	if(_data.p!=-1){
		$.ajax({
			url:'/home/d/pro_driver/list?p='+_data.p,
			dataType:'json',
			type:'post',
			success:function(res){
				$('#_end').bind('click',function(){
					_getData(0,res.data.page.pages);
				})
				_max = res.data.page.pages;
				_bd = res.data.list;
				for(var i in res.data.list){
					res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
					res.data.list[i].index = i;
				}
				if(''==_d){
					_d = new Vue({
						el:'#data',
						data:{
							datas:res.data
						}
					})
				}else{
					_d.datas = res.data
				}
			}
		})
	}
}
var _editId = '';
function _edit(_e){
	var _id = $(_e).attr('id');
	var _index = parseInt($(_e).attr('index'));
	_editId = _id;
	$('#_name').val(_bd[_index].name);
	$('#_mobilePhone').val(_bd[_index].mobilePhone);
	$('#_plateNumber').val(_bd[_index].plateNumber);
	_showNew();
}

function _update(){
	var _name = $('#_name').val();
	var _mobilePhone = $('#_mobilePhone').val();
	var _pwd = $('_pwd').val();
	var _plateNumber = $('#_plateNumber').val();
	var _params = 'name='+_name+'&mobilePhone='+_mobilePhone+'&pwd='+_pwd+'&plateNumber='+_plateNumber+'&id='+_editId;
	if(_name==''||_mobilePhone==''||_pwd==''){
		layer.alert('请完善除车牌号外每一项选项！');
	}else if(!testNumber(_mobilePhone)){
		layer.alert('手机号码不合法！');
	}else{
		$.ajax({
			url:'/home/d/pro_driver/update',
			type:'post',
			data:_params,
			dataType:'json',
			success:function(res){
				if('200'==res.code){
					layer.alert('修改成功！',function(){
						location.reload();
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
