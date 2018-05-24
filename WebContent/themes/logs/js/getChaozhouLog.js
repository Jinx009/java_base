$(function(){
	_getData();
})
/**
 * 获取文件清单
 */
function _getData(){
	var _path = _getQueryString('d');
	$.ajax({
		url:'/common/log/chaozhouLogs?d='+_path,
		type:'get',
		dataType:'json',
		success:function(res){
			if(res.data!=null){
				var _htmlStr = '<a href="#" class="list-group-item active">链接列表</a>';
				for(var i in res.data){
//					res.data[i] = res.data[i].split('/')[1];
//					var _fileName = '/'+_path+'/'+res.data[i];
					_htmlStr += '<a  onclick=_getFile("'+res.data[i]+'") id="'+res.data[i]+'" class="list-group-item file-list" style="color:red;cursor:pointer;" >'+res.data[i]+'</a>';
				}
				$('#files').html(_htmlStr);
				_getFile(res.data[0]);
			}else{
				layer.alert(res.msg);
			}
		}
	})
}
/**
 * 获取文件内容
 * @param _fileName
 */
function _getFile(_fileName){
	$('.file-list ').each(function(){
		if($(this).attr('id')==_fileName){
			$(this).css('color','red');
		}else{
			$(this).css('color','black');
		}
	})
	$.ajax({
		url:'/common/log/chaozhouFile?d='+_fileName,
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