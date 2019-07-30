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
