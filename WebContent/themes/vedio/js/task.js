$(function(){
    _getData();
})

function _getData(){
    $.ajax({
        url:'/d/vedioTask/list',
        type:'get',
        dataType:'json',
        success:function(res){
            new Vue({
                el:'#datas',
                data:{
                    datas:res.data
                }
            })
        }
    })

}

function _goVedio(_e){
	var src = '/themes/upload_files/vedio/'+$(_e).attr('src')+"/"+$(_e).attr('src')+'.mp4';
	window.open(src);
}

function _taskDetail(_e){
	var _id = $(_e).attr('id');
	setSessionStorage('taskDetailId',_id);
	location.href = '/themes/vedio/log_list.html';
}
function getSessionStorage(_key){
	if(window.sessionStorage){     
		return window.sessionStorage.getItem(_key);
	}
}
function setSessionStorage(_key,_value){
	if(window.sessionStorage){     
		var _r = window.sessionStorage.setItem(_key,_value);
		if(_r!=null&&_r!=''&&_r!=undefined){
			return _r;
		}else{
			return '';
		}
	}else{ 
		return '';
	}
}