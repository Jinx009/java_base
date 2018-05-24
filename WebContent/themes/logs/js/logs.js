$(function(){
	_getData();
})
/**
 * 获取文件内容
 * @param _fileName
 */
function _getData(){
	var _fileName = window.location.href.split('=')[1];
	$('.file-list ').each(function(){
		if($(this).attr('id')==_fileName){
			$(this).css('color','red');
		}else{
			$(this).css('color','black');
		}
	})
	$.ajax({
		url:'/common/log/log?d='+_fileName,
		type:'get',
		dataType:'json',
		success:function(res){
			$('#data').html('<pre>'+res.data+'</pre>');
		}
	})
}
/**
 * 获取查询参数
 * @param name
 * @returns
 */
function _getQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}