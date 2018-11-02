$(function(){
	_i = showLoad();
	_getUser();
})
var _i = -1,_data = '',_nowUser = "";
var  _nowPage = 0,_max = 0,_search = '';
function _getData(_type,_index){
	if(-1==_i){
		_i = showLoad();
	}
	var userId = $('#account').val();
	var page = _getPage(_type,_index);
	if(_nowUser!=userId){
		page = 1;
	}
	if(page!=-1){
		$.ajax({
			url:'/home/d/mofang/sign_log?companyId=10001&page='+page+'&storeOrganId=10002&userId='+userId,
			type:'post',
			dataType:'json',
			success:function(res){
				_nowUser = userId;
				closeLoad(_i);
				if('200'==res.code){
					if(res.data!=null){
						var totalPage = parseInt(res.data.data.pageInfo.pages);
						$('#_end').bind('click',function(){
							_getData(0,totalPage);
						})
						_max = totalPage;
						var _d = {};
						_d.data = res.data.data.operationLogs;
						for(var i in _d.data){
							_d.data[i].name = _getName(_d.data[i].userId);
						}
						_d.currentPage = page;
						_d.max = _max;
						if(''==_data){
							_data = new Vue({
				   				  el: '#logs',
				   				  data:{logs:_d}
				    		})
						}else{
							_data.logs = _d;
						}
					}else{
						layer.alert('暂无数据！');
					}
				}else if('8001'==res.code){
					layer.alert('您无权限操作此项！');
				}else{
					layer.alert(res.msg);
				}
			}
		})
	}else{
		closeLoad(_i);
	}
}

function _getName(_userId){
	for(var i in _userArr){
		if(_userArr[i].userId===_userId){
			return _userArr[i].name;
		}
	}
}

var _userArr = [];
function _getUser(){
	$.ajax({
		url:'/home/d/mofang/user?companyOrganId=10002',
		type:'post',
		dataType:'json',
		success:function(res){
			closeLoad(_i);
			if(res.data!=null){
				_userArr = res.data.data.users;
				new Vue({
	   				  el: '#account',
	   				  data:{accounts:res.data.data.users}
	    		})
				_getData(0,1);
			}
		}
	})
}