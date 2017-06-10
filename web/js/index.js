/*****************配置文件*******************/
var setting = {
    async: {
        enable: true,
        url:getUrl,
        //type:"post",
        //contentType:"application/json",
        //autoParam:["id", "name", "level"],
        //otherParam:{"otherParam":"zTreeAsyncTest"},
        dataFilter: filter//用于对 Ajax 返回数据进行预处理的函数
    },
    view: {
        fontCss: getFontCss,
        expandSpeed:"",
        selectedMulti:false,
    },
    data: {
        simpleData: {
            enable: true,//使用简单 Array 格式的数据
            idKey : "id",
            pIdKey : "pId",
            rootPId:1,
        },
        key:{
            name:"name",
        }
    },
    callback: {
        onClick: zTreeOnClick,
        beforeExpand: beforeExpand,
        onAsyncSuccess: onAsyncSuccess,
        onAsyncError: onAsyncError,
    }
};
//点击事件
function zTreeOnClick(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("permission_tree");
    zTree.reAsyncChildNodes(treeNode,"refresh",true);
}
//*********获取接口***********
function getUrl(treeId, treeNode) {
    //var curCount = (treeNode.children) ? treeNode.children.length : 0;
    //var getCount =treeNode.count;
    var id;
    if(!treeNode){
        id=0;
    }else{
        id=treeNode.id;
    }
    var param="pId="+id ;
    return "find.do?"+param;
}
/***************数据加载，数据调取******************/
var zNodes;
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    return childNodes;
}
var log, className = "dark",
    startTime = 0, endTime = 0, perCount = 100, perTime = 100;
function beforeExpand(treeId, treeNode) {
    if (!treeNode.isAjaxing) {
        startTime = new Date();
        treeNode.times = 1;
        return true;
    } else {
        alert("zTree 正在下载数据中，请稍后展开节点。。。");
        return false;
    }
}
var a=0;
function onAsyncSuccess(event, treeId, treeNode, msg) {
    //zNodes=eval(msg);
    ////if(a==0) {
    //$("#treeDemo").html()
    //    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    //    a = 1;
    //}
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    alert("异步获取数据出现异常。");
    treeNode.icon = "";
    zTree.updateNode(treeNode);
}
function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='"+className+"'>"+str+"</li>");
    if(log.children("li").length > 4) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}
function getTime() {
    var now= new Date(),
        h=now.getHours(),
        m=now.getMinutes(),
        s=now.getSeconds(),
        ms=now.getMilliseconds();
    return (h+":"+m+":"+s+ " " +ms);
}
/************************************逻辑树搜索失去焦点***************/
function focusKey(e) {
    if (key.hasClass("empty")) {
        key.removeClass("empty");
    }
    $.ajax({
        url:"findMenu.do",
        type: "POST",
        async: false,
        success: function (res) {
            zNodes=res;
        }
    })
}
function blurKey(e) {
    if (key.get(0).value === "") {
        key.addClass("empty");
    }
}
/****************逻辑树搜索**************/
var lastValue = "", nodeList = [], fontCss = {};var arrData=[];
function searchNode(e) {
    //var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    if ($("#key").val()) {
        $(".searchNone").css("display","block");
        $(".searchNone ul").html("");
        var str;
        for(var i=0;i<zNodes.length;i++){
            for(var j=0;j<=zNodes[i].name.length;j++) {
                if (zNodes[i].name.substring(0,j)==$("#key").val()) {
                    if(zNodes[i].isParent=="true") {
                        str = '<li class="liLj" style="padding:0 5px;">' +
                            '<h6>' + zNodes[i].name + '</h6>'+
                            '</li><hr style="margin:10px 0;"/>';
                    }else{
                        str = '<li class="liLj" style="padding:0 5px;">' +
                            '<h6 style="font-weight: bolder">' + zNodes[i].name + '</h6>' +
                            '<p style="width: 100%;height: 20px;font-size: 12px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">百度覅ui二个范围U废物废物大哥我V为V飞跃无关风月如发热V为丰富V而我范围</p>' +
                            '</li><hr style="margin:10px 0;"/>';
                        arrData.push(i);
                    }
                    $(".searchNone ul").append(str);
               }
            }
        }
        //    var value = $.trim(key.get(0).value);
        //var keyType = "name";
        //if (key.hasClass("empty")) {
        //    value = "";
        //}
        //if (lastValue === value) return;
        //lastValue = value;
        //if (value === "") return;
        //updateNodes(false);
        //nodeList = zTree.getNodesByParamFuzzy(keyType, value);

    } else {
        $(".searchNone").css("display","none");
        $(".searchNone ul").html("");
    //    updateNodes(false);
    //    nodeList = zTree.getNodesByFilter(filter);
    //
    }
    //打开至节点
    //if($("#key").val()!=""){
    //    for(var j=0;j<zNodes.length;j++){
    //        zNodes[j].open=false;
    //    }
    //    for(var i=0;i<nodeList.length;i++) {
    //        for(var j=0;j<zNodes.length;j++){
    //            var str=nodeList[i].id.toString();
    //            for(var z=0;z<str.length;z++) {
    //                if (str.substring(0,z) == zNodes[j].id) {
    //                    zNodes[j].open = true;
    //                    nodeList[i].open=true;
    //                }
    //            }
    //        }
    //    }
    //}else{
    //    for(var j=0;j<zNodes.length;j++){
    //        zNodes[j].open=false;
    //    }
    //}
    //$.fn.zTree.init($("#treeDemo"), setting, zNodes);
    //updateNodes(true);
}
//function updateNodes(highlight) {
//    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//    for( var i=0, l=nodeList.length; i<l; i++) {
//        nodeList[i].highlight = highlight;
//        zTree.updateNode(nodeList[i]);
//    }
//}
function getFontCss(treeId, treeNode) {
    return (!!treeNode.highlight) ? {backgroundColor:"#FFE6B0",border:"1px #FFB951 solid", "font-weight":"bolder"} : {backgroundColor:"none",border:"none", "font-weight":"normal"};
}
var key;
$(document).ready(function () {
    $.fn.zTree.init($("#treeDemo"), setting);
    //逻辑树搜索
        key = $("#key");
        key.bind("focus", focusKey)
            .bind("blur", blurKey)
            .bind("propertychange", searchNode)
            .bind("input", searchNode);
    $(document).click(function(){
        $(".searchNone").css({display:"none"});
    });
    //逻辑树和主题内容的高
    var height=$(window).height()-$(".nav").height();
    $(".content-left,.content-right").height(height-40);
    $("#treeDemo").width($(".content-left").width);
    //左侧逻辑树下的日历显示及逻辑树高度
    var riliTop=$(".rili").offset().top;
    $("#treeDemo").height(riliTop-$("#key").height()-140);
    $(".searchNone").css("max-height",riliTop-$("#key").height()-140);
    $("#treeDemo,.searchNone").css("overflow","scroll");
    var date=new Date();
    $(".riliRight").text(date.getFullYear()+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日");
    //点击导航栏的知识
    $("#knowledge").bind("click",function(){
        tool($(this),'notice/notice.html?index=1');
    })
    //点击导航栏的公告
    $("#notice").bind("click",function(){
        tool($(this),'notice/notice.html?index=2');
    })
    //点击导航栏的工具按钮
    $("#tool").bind("click",function(){
        tool($(this),'tool/setLogicTree.html');
    })
    //点击导航栏的编辑按钮
    $("#edit").bind("click",function(){
        tool($(this),'edit/edit.html');
    });
    //点击导航栏的编辑按钮
    $("#news").bind("click",function(){
        tool($(this),'news/news.html');
    });
    //点击导航栏的个人中心按钮
    $("#personal").bind("click",function(){
        tool($(this),'Personal%20Center/Personal.html');
    });

    //逻辑树li的背景
    $("#treeDemo li").css({"background-image":"../images/bg_36.png"});
    //**************添加选项卡***************
    //********搜索框x清空********
    $(".del").bind("click",function(){
        $("#key").val("");
    });
    //***********点击搜索内容li**********
    $('.searchNone').on('click', 'li', function() {
        var tex1 =$('.center #tab').text();
        var tex2=new RegExp($(this).find("h6").text());
        if(tex2.test(tex1)){
            for(var i=0;i<$(".center #tab div").size()+1;i++){
                if($(".center #tab div").eq(i).text().replace(" ","")==$(this).find("h6").text()){
                    selected($(".center #tab div").eq(i));
                }
            }
        }else{
                if($(this).has("p").length&&zNodes[arrData[$(this).index()]].isParent=="false"){
                    $(".center #tab").append("<div>" + $(this).find("h6").text() +'<span class="main-tabs-del-bg"><span class="main-tabs-del"></span></span>' +"</div>");
                    $('#tab_men').append("<iframe src='text/text.html?id="+arrData[$(this).index()]+"'></iframe>");
                    selected($(".center #tab div:last-child"));
                    return false;
                }
        }
    });
    //**********点击逻辑树文件**********
    $('#treeDemo').on('click', 'li a span', function() {
        var tex1 =$('.center #tab').text();
        var tex2=new RegExp($(this).text());
        if(tex2.test(tex1)){
            for(var i=0;i<$(".center #tab div").size()+1;i++){
                if($(".center #tab div:nth-child("+i+")").text().replace(" ","")==$(this).text()){
                    selected($(".center #tab div:nth-child("+i+")"));
                }
            }
            return false;
        }else{
            for(var i=0;i<zNodes.length;i++){
                if($(this).text()==zNodes[i].name&&zNodes[i].isParent==false){
                    $(".center #tab").append("<div>" + $(this).text() +'<span class="main-tabs-del-bg"><span class="main-tabs-del"></span></span>' +"</div>");
                    $('#tab_men').append("<iframe src='text/text.html' ></iframe>");
                    selected($(".center #tab div:last-child"));
                    return false;
                }
            }
        }
    });
    //***********选项卡切换**************
    $("#tab").on('click','div',function(){
        selected($(this));
    });
    //**************删除选项卡，以及添加样式。**********
    $('#tab').on('click','div .main-tabs-del-bg',function(event){
        var index= $(this).parent().index();
        $("#tab_men iframe").eq(index).remove();
        $("#tab_men iframe:last-child").show();
        $(this).parent().remove();
        if ($(this).parent().is('.selected')) {
            $("#tab div:last-child").addClass("selected").siblings().removeClass("selected");
        }
        event.stopPropagation();
    });
    //**************回到首页***************
    $('.nav .nav_C img').click(function(){
        $("#tab div:first-child").addClass('selected').siblings().removeClass("selected");
        var index = $(this).index();
        //获取被按下按钮的索引值，需要注意index是从0开始的
        $("#tab_men iframe").eq(index).show().siblings().hide();
    })

});
//************设置tab样式*****************
function selected(id){
    //if(){
    //
    //}
    id.addClass("selected").siblings().removeClass("selected");
    //切换选中的按钮高亮状态
    var index = $(id).index();
    //获取被按下按钮的索引值，需要注意index是从0开始的
    $("#tab_men iframe").eq(index).show().siblings().hide();//在按钮选中时在下面显示相应的内容，同时隐藏不需要的框架内
}

//*************点击导航栏的工具栏************
function tool(id,href){
    var id=id.find("span");
    var tex1 =$('.center #tab').text();
    var tex2=new RegExp(id.text());
    if(tex2.test(tex1)){
        for(var i=0;i<=$(".center #tab div").size();i++){
            //console.log($(".center #tab div").eq(i).text()+id.text());
            if($(".center #tab div").eq(i).text()==id.text()){
                selected($(".center #tab div").eq(i));
            }
        }
    }else{
        $(".center #tab").append('<div><span>'+ id.text() +'</span><span class="main-tabs-del-bg"><span class="main-tabs-del"></span></span></div>');
        $('#tab_men').append("<iframe src="+href+" ></iframe>");
        selected($(".center #tab div:last-child"));
    }
}
//***********改变窗口大小*************
window.onresize = function(){
    //逻辑树和主题内容的高
    var height=$(window).height()-$(".nav").height();
    $(".content-left,.content-right").height(height-40);
    //左侧逻辑树下的日历显示
    var riliTop=$(".rili").offset().top;
    $("#treeDemo").height(riliTop-$("#key").height()-140);
    $(".searchNone").css("max-height",riliTop-$("#key").height()-140);
    $("#treeDemo,.searchNone").css("overflow","scroll");
};

