$(function() {
	laydate.render({
		elem : '#date'
	});
})
var _d = '';
function _getData(_) {
	var date = $('#date').val();
	if (date == '') {
		layer.alert('请先选择日期！');
	} else {
		$
				.ajax({
					url : '/h/pro_goods/list?date='+date,
					dataType : 'json',
					type : 'post',
					success : function(res) {
						var _dd = [];
						for ( var i in res.data) {
							for(var j in res.data[i].place){
								res.data[i].place[j].createTime = toDateTime(res.data[i].place[j].createTime);
								_dd.push(res.data[i].place[j]);
							}
						}
						if ('' == _d) {
							_d = new Vue({
								el : '#data',
								data : {
									datas : _dd
								}
							})
						} else {
							_d.datas = _dd
						}
					}
				})
	}

}

function del(_e){
	var id = $(_e).attr('id');
	$.ajax({
		url : '/h/pro_goods/del?id='+id,
		dataType : 'json',
		type : 'post',
		success : function(res) {
			if(res.code=='200'){
				layer.alert('解除成功',function(){
					layer.closeAll(); 
					_getData(_);
				});
			}else{
				layer.alert(res.msg);
			}
		}
	})
}