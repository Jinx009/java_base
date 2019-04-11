$(function() {
	_getData(0, 1);
	_getStreet();
})
var _d = '';
var _nowPage = 0, _max = 0;
/**
 * 获取相机列表
 * @param _type
 * @param _index
 */
function _getData(_type, _index) {
	var _data = {};
	_data.p = _getPage(_type, _index);
	if (_data.p != -1) {
		$
				.ajax({
					url : '/d/camera/list',
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
 * 保存相机
 */
function _save() {
	var _parkNumber = $('#parkNumber').val();
	var _streetId = $('#streetId').val();
	var _streetName = $('#streetId option:selected"').text();
	var _deviceType = $('#deviceType').val();
	var _params = 'parkNumber=' + _parkNumber + '&streetId=' + _streetId
			+ '&streetName=' + _streetName + '&deviceType=' + _deviceType;
	$.ajax({
		url : '/d/camera/add',
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
 * 跳转相机编辑页
 * @param _e
 */
function _edit(_e) {
	var _id = $(_e).attr('id').split('_edit')[1];
	location.href = '/p/camera/edit?id=' + _id;
}
/**
 * 删除相机
 * @param _e
 */
function _delete(_e) {
	var _id = $(_e).attr('id').split('_delete')[1];
	var _data = {};
	_data.id = _id;
	$.ajax({
		url : '/d/camera/del',
		dataType : 'json',
		data : JSON.stringify(_data),
		contentType : 'application/json;charSet=utf8',
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
/**
 * 获取街道信息
 */
function _getStreet() {
	$.ajax({
		url : '/d/street/all',
		dataType : 'json',
		type : 'post',
		success : function(res) {
			new Vue({
				el : '#streetId',
				data : {
					streets : res.data
				}
			})
		}
	})
}