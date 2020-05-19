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
						for ( var i in res.data) {
							res.data[i].createTime = toDateTime(res.data[i].createTime);
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
