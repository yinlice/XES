/************配置文件**********/
var setting = {
    async: {
        enable: true,//开启异步加载处理
        url:getUrl,
        //autoParam:["id", "name=n", "level=lv"],
        //otherParam:{"otherParam":"zTreeAsyncTest"},
        dataFilter: filter//用于对 Ajax 返回数据进行预处理的函数
    },
    view: {
        expandSpeed: "",//zTree 节点展开、折叠时的动画速度，设置方法同 JQuery 动画效果中 speed 参数。
        addHoverDom: addHoverDom,//用于当鼠标移动到节点上时，显示用户自定义控件，显示隐藏状态同 zTree 内部的编辑、删除按钮
        removeHoverDom: removeHoverDom,//设置鼠标移到节点上，在后面显示一个按钮
        selectedMulti: false// 禁止多点同时选中的功能
    },
    edit: {
        enable: true,//设置 zTree 进入编辑状态
        showRemoveBtn: showRemoveBtn,
        showRenameBtn:showRenameBtn
    },
    data: {
        simpleData: {
            enable: true,//使用简单 Array 格式的数据
            idKey : "id",
            pIdKey : "pId",
            rootPId:0,
        },
        key:{
            name:"name",
        },
        keep:{
            parent:true,
            leaf:true
        }
    },
    callback: {
        beforeRemove: beforeRemove,//用于捕获节点被删除之前的事件回调函数，并且根据返回值确定是否允许删除操作
        beforeRename: beforeRename,//用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
        onDrop: onDrop,//用于捕获节点拖拽操作结束的事件回调函数
        onAsyncSuccess: onAsyncSuccess,
        onAsyncError: onAsyncError,
    }
};
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
    //var zTree = $.fn.zTree.getZTreeObj("tree");
    //zTree.expandAll(false);
    return "../find.do?"+param;
}
/**********获得需要加载的数据**********/
//修改异步获取到的节点name属性
var a=0;
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    var arr=[];
    for (var i=0, l=childNodes.length; i<l; i++) {
        //childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        if(childNodes[i].isParent=="true"){
            arr.push(childNodes[i]);
        }
    }
    //if(a==0){
    //    a=1;
        return arr;
    //}
}

//异步加载成功
function onAsyncSuccess(event, treeId, treeNode, msg) {
    if(a==1){
        return false;
    }
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    alert("异步获取数据出现异常。");
    treeNode.icon = "";
    zTree.updateNode(treeNode);
}
var log, className = "dark";
//拖拽
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    className = (className === "dark" ? "":"dark");
    //拖拽成功时，修改被拖拽节点的pid
    if(treeNodes && targetNode){
        $.ajax({
            url:'../updateMenu.do?id='+treeNodes[0].id+'&pId='+targetNode.id+"&name="+treeNodes[0].name+"&open=true&isParent=true&checked=true",
            type:"post",
            dataType:"json",
        }).success(function(data){

        }).error(function(){

        });
    }
}
//**********删除文件夹**************
function beforeRemove(treeId, treeNode) {
    //父节点设置
    var parentNode=treeNode.getParentNode();
    //
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    className = (className === "dark" ? "":"dark");
    showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.title);
    var treeInfo = treeNode.tableNum;
    var re = false;
    $.ajax({
        url: "../find.do",
        type: "POST",
        async: false,
        data: "pId=" + treeNode.id,
        success: function (res) {
            if (res.length>0) {
                alert("该文件夹下有子文件，不可删除！");
            } else {
                $.ajax({
                    url: "../deleteMenu.do",
                    type: "POST",
                    async: false,
                    data: "id=" + treeNode.id+"&isParent=true",
                    success: function (data) {
                        if (data) {
                            alert('删除成功!');
                            //$.ajax({
                            //    url:"../find.do?id="+parentNode.id+"&name="+parentNode.name+"&pId="+parentNode.pId+"&open=true&isParent=true&checked=true",
                            //    type: "POST",
                            //    async: false,
                            //    success: function (res) {
                            //        console.log(res);
                            //    }
                            //})
                        } else {
                            alert('删除失败!');
                        }
                        re = true;
                    }
                });
            }
        }
    });
    return re;
}
function onRemove(e, treeId, treeNode) {
    showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}
//***************编辑文件夹*****************
function beforeRename(treeId, treeNode, newName) {
    if (newName.length == 0) {
        alert("文件名称不能为空.");
        return false;
    }
    var treeInfo = treeNode.tableNum;
    $.ajax({
        url: "../updateMenu.do?id="+treeNode.id+"&name="+newName+"&pId="+treeNode.pId+"&open=true&isParent=true&checked=true",
        type: "POST",
        async: false,
        success: function (res) {
            if (res = "success") {
                alert('修改成功!');
            } else {
                alert('修改失败!');
            }
        },error:function(){
            alert("操作失败");
        }
    });
}
function onRename(e, treeId, treeNode, isCancel) {
    showLog((isCancel ? "<span style='color:red;'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
}
function showRemoveBtn(treeId, treeNode) {
    //判断为顶级节点则不显示删除按钮
    if(treeNode.level == 0)
        return false;
    else
        return true;
}
function showRenameBtn(treeId, treeNode) {
    if(treeNode.level == 0)
        return false;
    else
        return true;
}
function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='"+className+"'>"+str+"</li>");
    if(log.children("li").length > 8) {
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
$(document).ready(function(){
    //初始化
    $.fn.zTree.init($("#treeDemo"), setting);
});
/*********文件夹添加功能***************/
var newCount = 1;
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
//                        增删改图标
        + "' style='margin-left:30px' title='add node' onfocus='this.blur();'></span>";
    if(treeNode.isParent){
        sObj.after(addStr);
    }
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
        var treeInfo = treeNode.tableNum;
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        $.ajax({
                url: "../insertMenu.do",
                type: "post",
                async: false,
                data: "pId="+treeNode.id+"&name=新建文件夹&isParent=true&checked=true&open=true",
                success: function (data) {
                    if(data){
                        alert("添加成功");
                        var newnade={id:data, pId: treeNode.id, name:"新建文件夹",isParent:true,checked:true,open:true};
                        zTree.addNodes(treeNode, newnade);
                    }else{
                        alert("添加失败");
                    }
                },error:function(){
                alert("操作失败");
                }
            });
    });
};
//设置鼠标移到节点上，在后面显示一个按钮
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};

