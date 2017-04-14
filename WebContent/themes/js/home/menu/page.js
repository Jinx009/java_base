var parentMenuList = [],sonMenuList = [];
function getData(){
	$.ajax({
		url:'/home/d/page',
		type:'post',
		dataType:'json',
		success:function(res){
			if('200'==res.code){
				if(res.data!=null){
					for(var i in res.data){
						if(res.data[i].parentId == 0){
							var obj = {};
							obj.id = res.data[i].id;
							obj.name = res.data[i].name;
							obj.list = [];
							parentMenuList.push(obj);
						}else{
							sonMenuList.push(res.data[i]);
						}
					}
					for(var i in sonMenuList){
						for(var j in parentMenuList){
							if(sonMenuList[i].parentId==parentMenuList[j].id){
								parentMenuList[j].list.push(sonMenuList[i]);
							}
						}
					}
					new Vue({
		   				  el: '.content',
		   				  data:{datas:parentMenuList}
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