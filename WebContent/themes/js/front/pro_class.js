$(function(){
	_getData();
})
function _getData(){
	var _userId = getLocalStorage('userId');
	$.ajax({
		url:'/d/class/getStatus',
		type:'post',
		data:'userId='+_userId,
		dataType:'json',
		success:function(res){
			var _htmlStr = '';
			if('200'==res.code){
				for(var i in res.data){
					if(res.data[i].desc==='1'){
						_htmlStr+= ' <p><a onclick="_save('+res.data[i].id+')" class="button button-danger">'+res.data[i].classDate +' ['+res.data[i].name+'] '+res.data[i].time+'</a></p>';
					}else{
						_htmlStr+= ' <p><a  class="button disabled">'+res.data[i].classDate +' ['+res.data[i].name+'] '+res.data[i].time+'</a></p>';
					}
				}
			}
			$('.content-block').html(_htmlStr);
		}
	})
}