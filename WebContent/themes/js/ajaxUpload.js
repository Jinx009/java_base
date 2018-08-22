$(function() {
	loadForms();
})
function _showUpload(){
	$('#file').click();
}
/**
 * 初始加载表单
 */
function showLoad(){  
    return layer.msg('努力导入中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '200px', time:100000}) ;  
}  
function closeLoad(index){  
    layer.close(index);  
} 
function loadForms() {
	$('#form').ajaxForm({
		dataType:'json',
		success : function(res) {
			closeLoad(_i);
			if ('200' == res.code) {
				layer.alert('导入成功！',function(){
					location.reload();
				})
			}else{
				layer.alert(res.msg);
			}
		},complete : function(t) {}
	});
}
/**
 * 按钮提交事件
 */
var _i = '';
function _upload() {
	_i = showLoad();
	$('#form').submit();
}
/**
 * 显示上传文件路径
 */
$('input[id=file]').change(function() {
	$('#fileShow').val($(this).val());
	_upload();
});