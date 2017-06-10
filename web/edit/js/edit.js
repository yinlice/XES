//**********************************配置文件*************************
var setting = {
    async: {
        enable: true,
        url:"../js/data/text1.txt",
        autoParam:["id", "name=n", "level=lv"],
        otherParam:{"otherParam":"zTreeAsyncTest"},
        dataFilter: filter
    },
    view: {expandSpeed:""
    },
    edit: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    check: {
        enable: true,
        nocheckInherit: true
    },
    callback: {
        onCheck: onCheck
    }
};
var zNodes;
$(function(){
    //**************设置当前时间******************
    document.getElementById('date1').valueAsDate = new Date();
    document.getElementById('date2').valueAsDate = new Date();
    document.getElementById('update1').valueAsDate = new Date();
    //*************点击选择显示逻辑树页面*****************
    $(".choice").bind("click",function(){
        $(".Logical").css("display","block");
        $.fn.zTree.init($("#treeDemo"), setting);
    });
    //*************点击关闭选择显示逻辑树页面*****************
    $("#sure").bind("click",function(){
        $(".Logical").css("display","none");
    });
    //*************点击保存按钮保存文件***************
    $(".subSubmit").bind("click",function(){
        submit();
    });
});
var editor;
KindEditor.ready(function(K) {
    //**************编辑器*************
    editor=K.create("textarea[name='content']",{
        allowFileManager : true
    });
    //*************预览按钮*****************
    K('.yulan').click(function() {
        var dialog = K.dialog({
            width : 500,
            title : '预览',
            body : '<div style="margin:10px auto;width: 450px;min-height: 200px;word-wrap: break-word;"><strong>'+editor.text()+'</strong></div>',
            closeBtn : {
                name : '关闭',
                click : function(e) {
                    dialog.remove();
                }
            },
            noBtn : {
                name : '关闭',
                click : function(e) {
                    dialog.remove();
                }
            }
        });
    });
});
/******************************获得需要加载的数据****************************/
var aqa=0;
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i=0, l=childNodes.length; i<l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        if(childNodes[i].isParent==false){
            childNodes[i].nocheck=true;
        }
    }
    if(aqa==0){
        aqa=1;
    }else{
        return false;
    }
    zNodes=childNodes;
    return childNodes;
}
//******************点击复选框************************
function onCheck(e, treeId, treeNode) {
    $("#relationship").val();
    var a="";
    for(var j=0;j<zNodes.length;j++){
        var str=treeNode.id.toString();
        for(var z=0;z<str.length;z++) {
            if (str.substring(0,z) == zNodes[j].id) {
                a+=zNodes[j].name+"/";
                zNodes[j].check=true;
            }
        }
    }
    $("#relationship").val(a+ treeNode.name);
}
//***********点击保存把文件保存到相应逻辑树下************
function submit(){
    var pathArr=$("#relationship").val().split("/");
    console.log(pathArr);
}