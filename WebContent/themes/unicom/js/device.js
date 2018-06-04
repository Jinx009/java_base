$(function(){
	_getData();
	setInterval(function() {

		_getData();

	}, 5000);
})
/**
 * 获取文件内容
 * @param _fileName
 */
function _getData(){
	$.ajax({
		url:'/common/log/unicomFile',
		type:'get',
		dataType:'json',
		success:function(res){
			var _htmlStr = '';
			for(var i in res.data){
				if(res.data[i].indexOf('mac')<=0){
					_htmlStr += '<tr><td>'+res.data[i].split(',')[0]+'</td><td>'+res.data[i].split(',')[1]+'</td><td>'+
					res.data[i].split(',')[2]+'</td><td>'+res.data[i].split(',')[3]+'</td></tr>';
				}
			}
			console.log(_htmlStr);
			$('#data').html(_htmlStr);
		}
	})
}
