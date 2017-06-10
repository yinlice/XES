/**
 * Created by Admin on 2017/4/1.
 */
$(function(){
    //ajaxData();
    for(var i=0;i<50;i++){
        var tr='<tr> <td>Tanmay</td> <td>2014-1-2</td> <td>560001</td> <td>100000</td> </tr>';
        $(".mtk").append(tr);
    }
});
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
//所有公告或知识
function ajaxData(){
    //调取数据
    $.ajax({
        url:"",
        type:"",
        dataType:"",
        data:"index"+GetQueryString("index")
    }).success(function(data){
        var data=data,tr;
        //数据设置到页面
        for(var i=0;i<data.length;i++){
            tr+='<tr> <td>'+data[i].name+'</td> <td>'+data[i].time+'</td> <td>'+data[i].sw+'</td> <td>'+data[i].xw+'</td> </tr>'
            $(".mtk").html(tr);
        }
        $(".title").text(data.name);
        //data.count
    });
}