/**
 * Created by Admin on 2017/4/6.
 */
$(function(){
    //***************根据不同身份显示不同消息*************
    var IDCard="ds",tr;
    if(IDCard=="审核人"){
        for(var i=0;i<30;i++){
            tr+='<tr> ' +
                '<td>医生医生</td> ' +
                '<td>2017-3-2 22:22:22</td> ' +
                '<td>张三</td> <td>审核</td> ' +
                '<td  class="Reason">dwfew</td> ' +
                '<td> ' +
                '<button type="button" class="btn btn-primary Agree">同意</button> ' +
                '<button type="button" class="btn btn-primary Reject">驳回</button> ' +
                '<button type="button" class="btn btn-primary Edit">编辑</button> ' +
                '</td> ' +
                '</tr>';
        }
    }else{
        for(var i=0;i<20;i++){
            tr+='<tr> ' +
                '<td>医生医生</td> ' +
                '<td>2017-3-2 22:22:22</td> ' +
                '<td>张三</td> <td>成功</td> ' +
                '<td></td> ' +
                '<td> ' +
                '<button type="button" class="btn btn-primary submit">提交</button> ' +
                '<button type="button" class="btn btn-primary delete">删除</button> ' +
                '<button type="button" class="btn btn-primary Edit">编辑</button> ' +
                '</td> ' +
                '</tr>';
        }
    }
    $(".mtk").append(tr);
    //************审核人操作*********************
    //***********同意按钮****************
    $(".Agree").bind("click",function(){

    });
    //***********驳回按钮****************
    $(".Reject").bind("click",function(){
        var val=$(this).parent().siblings(".Reason").text();
        $(this).parent().siblings(".Reason").html("<input type='text' value='"+val+"'/>");
        $(".Reason input").focus();
        //*************驳回原因失去焦点****************
        $(".Reason input").blur(function(){
            var text=$(this).val();
            console.log(text);
            $(this).parent(".Reason").html(text);
        });
    });

    //************采编人和审核人共同操作*********************
    //***********编辑按钮****************
    $(".Edit").bind("click",function(){
        tool($(this),'edit/edit.html');
    });
    //************采编人操作*********************
    //***********提交按钮****************
    $(".submit").bind("click",function(){

    });
    //***********删除按钮****************
    $(".delete").bind("click",function(){
        $(this).parents("tr").remove();

    });
});
//**********数据调取************
function ajaxData(){
    //调取数据
    $.ajax({
        url:"",
        type:"",
        dataType:"",
        data:"id="
    }).success(function(data){
        var data=data,tr;
        //数据设置到页面
        for(var i=0;i<data.length;i++){
            tr+='<tr> <td>'+data[i].name+'</td> <td>'+data[i].time+'</td> <td>'+data[i].sw+'</td> <td>'+data[i].xw+'</td> </tr>'
            $(".mtk").html(tr);
        }
        $(".title").text(data.name);
    });
}
//*************点击导航栏的工具栏************
function tool(id,href){
    var tex1 =parent.$('.center #tab').text();
    var tex2=new RegExp(id.text());
    if(tex2.test(tex1)){
        for(var i=0;i<parent.$(".center #tab div").size()+1;i++){
            if(parent.$(".center #tab div:nth-child("+i+")").text()==id.text()){
                selected(parent.$(".center #tab div:nth-child("+i+")"));
            }
        }
    }else{
        parent.$(".center #tab").append("<div>" + id.text() +'<span class="main-tabs-del-bg"><span class="main-tabs-del"></span></span>' +"</div>");
        parent.$('#tab_men').append("<iframe src="+href+" ></iframe>");
        selected(parent.$(".center #tab div:last-child"));console.log(22222222222);
    }
}
//************设置tab样式*****************
function selected(id){
    //if(){
    //
    //}
    id.addClass("selected").siblings().removeClass("selected");
    //切换选中的按钮高亮状态
    var index = $(id).index();
    //获取被按下按钮的索引值，需要注意index是从0开始的
    parent.$("#tab_men iframe").eq(index).show().siblings().hide();//在按钮选中时在下面显示相应的内容，同时隐藏不需要的框架内
}
