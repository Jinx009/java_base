$(function() {
	_getData(0, 1);
})
var _d = '';
var _nowPage = 0, _max = 0;
/**
 * 获取列表
 * @param _type
 * @param _index
 */
function _getData(_type, _index) {
	var _data = {};
	_data.p = _getPage(_type, _index);
	if (_data.p != -1) {
		$
				.ajax({
					url : '/d/street/list',
					dataType : 'json',
					data : 'p='+_data.p,
					type : 'post',
					success : function(res) {
						$('#_end').bind('click', function() {
							_getData(0, res.data.page.pages);
						})
						_max = res.data.page.pages;
						for ( var i in res.data.list) {
							res.data.list[i].createTime = toDateTime(res.data.list[i].createTime);
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
					}
				})
	}
}
/**
 * 保存
 */
function _save() {
	var _parkNumber = $('#parkNumber').val();
	var _parkTotal = $('#parkTotal').val();
	var _name = $('#name').val();
	var _address = $('#address').val();
	var _params = 'streetNumber=' + _parkNumber + '&parkTotal=' + _parkTotal
			+ '&name=' + _name + '&address=' + _address;
	$.ajax({
		url : '/d/street/save',
		dataType : 'json',
		type : 'post',
		data : _params,
		success : function(res) {
			if ('200' == res.code) {
				layer.alert('新建成功！', function() {
					location.reload();
				})
			} else {
				layer.alert(res.msg);
			}
		}
	})
}
/**
 * 跳转编辑页
 * @param _e
 */
function _edit(_e) {
	var _id = $(_e).attr('id').split('_edit')[1];
	location.href = '/p/street/edit?id=' + _id;
}
/**
 * 删除街道
 * @param _e
 */
function _delete(_e) {
	var _id = $(_e).attr('id').split('_delete')[1];
	$.ajax({
		url : '/d/street/del',
		dataType : 'json',
		data : 'id='+_id,
		type : 'post',
		success : function(res) {
			if ('200' == res.code) {
				layer.alert('删除成功！', function() {
					location.reload();
				})
			} else {
				layer.alert(res.msg);
			}
		}
	})
}
