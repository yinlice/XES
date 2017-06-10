//************配置文件****************
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
//************编辑器**************
var editor;
KindEditor.ready(function(K) {
    editor = K.create('textarea[name="content"]', {
        resizeType : 1,
        allowPreviewEmoticons : false,
        allowImageUpload : false,
        items : [
            'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
            'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
            'insertunorderedlist', '|', 'emoticons', 'image', 'link','ke-toolbar-icon']
    });
});
$(function(){
    //*************点赞、收藏设置******************
    $(".content-top div i").bind("click",function(){
        var index=$(this).index();
        var length=$(".content-top div i").size();
        if(index!=(length*2-2)){
            if($(this).attr("disabled")){
                return false;
            }
            $(this).css("color","#ccc");
            var num=parseInt($(this).next().text());
            $(this).next().text(num+1);
            $(this).attr({"disabled":true});
        }else{
            $(this).css("color","#FFD700");
            if($(this).next().text()=="收藏") {
                $(this).next().text("已收藏");
            }else{
                $(this).next().text("收藏");
            };
            $.fn.zTree.init($("#treeDemo"), setting);
        }
    });
    //************查看更改*************
    $(".selectUpdate").bind("click",function(){
        var str=[{"name":"tab","img":"abc"},{"name":"tab","img":"abc"},{"name":"tab","img":"abc"},{"name":"tab","img":"abc"}];
        for(var i=0;i<str.length;i++){
            var tr='<tr> <td>'+str[i].name+'</td> <td>'+str[i].img+'</td> </tr>';
            $(".modal-body tbody").append(tr);
        }
    });
    //***************提交评论*********************
    $("#submit").bind("click",function(){
        editor.sync();
        var date=new Date();
        var str=$(".textContent").val();
        var p='<p> ' +
            '<strong class="zuoxi">坐席一</strong><strong>:&nbsp;&nbsp;&nbsp;</strong>' +
            '<span style="color: #ccc;font-size:5px;">'+date.toLocaleString()+'</span><br/> ' +
            '<span class="comCont" style="text-indent: 2em">'+str+'</span> ' +
            '</p>';
        $(".comment").append(p);
        editor.html("");
    });
});
//****************收藏弹框*************************
/******************************获得需要加载的数据****************************/
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i=0, l=childNodes.length; i<l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        if(childNodes[i].isParent==false){
            childNodes[i].nocheck=true;
        }
    }
    zNodes=childNodes;
    console.log(childNodes);
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