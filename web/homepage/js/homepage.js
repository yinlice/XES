/**
 * Created by Admin on 2017/4/1.
 */
//页面加载数据
function getData(){

}
var valueTab;
$(function(){
    //tab切换
    $(".panel_title div").bind("click",function(){
        var index=$(this).index();
        $(this).siblings().css("color","white");
        $(this).css("color","darkred");
        $(this).parent().siblings("table").css("display","none");
        $(this).parent().siblings("table").eq(index).css("display","block");
    });
    //点击导航栏的公告
    $(".more1").bind("click",function(){
        valueTab="公告";
        tool($(this),'notice/notice.html?index=1',valueTab);
    })
    //点击导航栏的知识
    $(".more2").bind("click",function(){
        valueTab="知识库";
        tool($(this),'notice/notice.html?index=2',valueTab);
    })
});

//************设置tab样式*****************
function selected(id){
    id.addClass("selected").siblings().removeClass("selected");
    //切换选中的按钮高亮状态
    var index =id.index();
    //获取被按下按钮的索引值，需要注意index是从0开始的
    parent.$("#tab_men iframe").eq(index).show().siblings().hide();//在按钮选中时在下面显示相应的内容，同时隐藏不需要的框架内
}

//*************点击“更多公告”和“更多知识”************
function tool(id,href,valueTab){
    var tex1 =parent.$('.center #tab').text();
    var tex2=new RegExp(valueTab);
    if(tex2.test(tex1)){
        for(var i=0;i<parent.$(".center #tab div").size();i++){
            if(parent.$(".center #tab div").eq(i).text().indexOf(valueTab)!=-1){
                selected(parent.$(".center #tab div").eq(i));
            }
        }
    }else{
        parent.$(".center #tab").append('<div><span>'+ valueTab +'</span><span class="main-tabs-del-bg"><span class="main-tabs-del"></span></span></div>');
        parent.$('#tab_men').append("<iframe src="+href+" ></iframe>");
        selected(parent.$(".center #tab div:last-child"));
    }
}