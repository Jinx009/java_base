$(function() {
	_getData(0, 1);
})
var _d = '';
var _nowPage = 0, _max = 0;
var _bd = [];
function _getData(_type, _index) {
	var _data = {};
	_data.p = _getPage(_type, _index);
	if (_data.p != -1) {
		$
				.ajax({
					url : '/h/pro_price/all?p=' + _data.p,
					dataType : 'json',
					type : 'post',
					success : function(res) {
						$('#_end').bind('click', function() {
							_getData(0, res.data.page.pages);
						})
						_max = res.data.page.pages;
						_bd = res.data.list;
						for ( var i in res.data.list) {
							res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
						}
						if ('' == _d) {
							_d = new Vue({
								el : '#data',
								data : {
									datas : res.data
								}
							})
						} else {
							_d.datas = res.data
						}
					}
				})
	}
}

function _edit(_e){
	var id = $(_e).attr('id');
	var name = $('#name_'+id).val();
	var price = $('#price_'+id).val();
	var params = 'name='+name+'&price='+price+'&id='+id;
	$.ajax({
		url:'/h/pro_price/update',
		type:'post',
		dataType:'json',
		data:params,
		success:function(res){
			if('200'==res.code){
				layer.alert('修改成功！',function(){
					location.reload();
				});
			}else{
				layer.alert('修改失败！');
			}
		}
	})
}