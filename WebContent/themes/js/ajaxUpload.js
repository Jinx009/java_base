$(function() {
	loadForms();
})
var filePath = '';
/**
 * 初始加载表单
 */
function loadForms() {
	$('#form').ajaxForm({
		dataType:'json',
		success : function(res) {
			if ('200' == res.code) {
				filePath = res.data;
				$('#fileShow').val(filePath);
				layer.alert('上传成功！');
			}else{
				layer.alert(res.msg);
			}
		},complete : function(t) {}
	});
}

function _showUpload(){
	$('#file').click();
}
/**
 * 按钮提交事件
 */
function _upload() {
	$('#form').submit();
}
/**
 * 显示上传文件路径
 */
$('input[id=file]').change(function() {
	$('#fileShow').val($(this).val());
	_upload();
});