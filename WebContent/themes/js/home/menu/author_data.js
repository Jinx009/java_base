var parentMenuList = [],sonMenuList = [];
function getData(){
	getRoles();
	getResources();
}
/**
 * 获取所有权限
 */
function getRoles(){
	$.ajax({
		url:'/home/d/role',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(res.data!=null){
					new Vue({
		   				  el: '.roles',
		   				  data:{roles:res.data}
		    		})
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
}
/**
 * 获取所有资源
 */
function getResources(){
	$.ajax({
		url:'/home/d/data',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(res.data!=null){
					var obj = {id:0,name:'数据权限',list:[]};
					parentMenuList.push(obj);
					for(var i in res.data){
						parentMenuList[0].list.push(res.data[i]);
					}
					console.log(parentMenuList)
					new Vue({
		   				  el: '.resource',
		   				  data:{resources:parentMenuList}
		    		})
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
}
function check(doc){
	var checked = $(doc).prop('checked');
	var id = $(doc).attr('id');
	if(checked){
		$('.check_'+id+' input[name="check_box"]').each(function(){
			$(this).prop('checked',true);
		})
	}else{
		$('.check_'+id+' input[name="check_box"]').each(function(){
			$(this).prop('checked',false);
		})
	}
}