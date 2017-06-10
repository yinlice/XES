var root = getRootPath();
/*js获取项目根路径*/
function getRootPath(){
    /*获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp*/
    var curWwwPath=window.document.location.href;
    /*获取主机地址之后的目录，如： uimcardprj/share/meun.jsp*/
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    /*获取主机地址，如： http://localhost:8083*/
    var localhostPaht=curWwwPath.substring(0,pos);
    /*获取带"/"的项目名，如：/uimcardprj*/
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}
function getEncryptTel(data,dom){
    var length = data.length;
    var UserConfig = JSON.parse(localStorage.getItem("UserConfig"));
    var encrypt = UserConfig["dataEncrypt"];
    if(arguments.length>1){
        dom.data("callout",data);
    }
    if(encrypt=="1"&&length>4){
        if(length<8||length==8){
            return data.slice(0,2)+data.slice(0,-2).replace(/[0-9]/g,"*")+data.slice(-2);
        }else if(length>8){
            return data.slice(0,3)+data.slice(3,-4).replace(/[0-9]/g,"*")+data.slice(-4);
        }
    }else {
        return data;
    }
}
function TableJs(){
    this.tableDom = null;
    this.queryFrom = null;
    this.header = null;
    this.idName = null;
    this.parameter = null;
    this.selectList = [];
    this.textList = [];
    this.buttonType = "select";
    this.init = function($dom,$queryFrom,parameter){
        var tableId = $dom.attr("id");
        this.tableDom = $dom;
        this.queryFrom = $queryFrom;
        this.parameter = parameter;
        this.setTableInfor(tableId);
        this.setClick();
        this.getTableHeader();
    };
    /*添加table的相关信息*/
    this.setTableInfor = function(id){
        var infor = '<div id="'+id+'_prompt" class="table-prompt">查询中......</div>';
        /*页面数据的详细信息*/
        var infor1 = '<div id="'+id+'_information" class="information" style="text-align: center" hidden>' +
                '每页<select class="pageDataNumber" style="width: 60px">' +
                '<option value="20">20</option><option value="50" selected>50</option> <option value="100">100</option></select>条&nbsp;&nbsp;' +
                '总共:<span class="count"></span>&nbsp;&nbsp;当前页:<span class="currentPage"></span>/ <span class="countPage"></span>页&nbsp;&nbsp; ' +
                '<input type="number" class="jumpPage">&nbsp;<a href="javascript:void(0)" class="paging">跳转</a></div>';
        /*选择上下页*/
        var infor2 = '<div id="'+id+'_Paging" class="Paging" style="text-align: center" hidden> ' +
                '<span class="pagingFirst"><a href="javascript:void(0)" class="paging">首页</a>&nbsp;<a href="javascript:void(0)" class="paging">上一页</a> </span>&nbsp;&nbsp; ' +
                '<span class="pagingEnd"><a href="javascript:void(0)" class="paging">下一页</a>&nbsp;<a href="javascript:void(0)" class="paging">尾页</a> </span> ' +
                '</div>';
        this.tableDom.parent().append($(infor+infor1+infor2));
        this.$tablePrompt = $('#'+id+'_prompt');
        this.$Paging = $('#'+id+'_Paging');
        this.$information = $('#'+id+'_information');
    };
    /*获取页面显示数据的表头信息*/
    this.getTableHeader = function(){
        var header = [];
        this.tableDom.find("th:not(.th)").each(function(){
            header.push(this.getAttribute("name"));
        });
        this.header = header;
        this.idName = this.tableDom.find("th").eq(0).attr("name");
    };
    /*设置表头显示数据,用于快速查询*/
    this.getTableHeader1 = function(){
        var that = this;
        that.selectList = [];
        that.textList = [];
        this.tableDom.find("th").each(function(){
            var className = this.className;
            var name = this.getAttribute("name");
            if(name&&className.indexOf("common")==-1){
                if(className.indexOf("select")!=-1){
                    that.selectList.push(name);
                }else{
                    that.textList.push(name);
                }
            }
        });
    };
    /*绑定按钮事件*/
    this.setClick = function(){
        var that = this;
        /*按钮操作*/
        this.queryFrom.on("click","input[type=button]:not(.public)",function(){/*按钮快捷查询*/
            var nameVal = $(this).attr("name");
            that.queryFrom.attr("name",nameVal);
            that.QueryTable(6,that.parameter,nameVal);
            that.queryFrom.find("input[type=button]:not(.public)").removeClass("selected");
            $(this).addClass("selected");
        }).on("change","select",function(){/*选择表头的快捷查询时，自动查询*/
            that.QueryTable(6,that.parameter,"select");
        }).on("click","input[type=button].refresh",function(){/*刷新*/
            that.QueryTable(5,that.parameter,"AdvancedQuery");
        }).on("click","input[type=button].advancedQuery",function(){/*高级查询*/
            layer.open({
                type: 2,
                title: '高级查询',
                shadeClose: true,
                shade: 0.1,
                area: ['640px', '35%'],
                content: root+'/Common/AdvancedQuery/AdvancedQuery.html'
            });
        }).on("click","input[type=button].jsExcel",function(){/*导出数据*/
            var count = that.tableDom.find("tr").length;
            if(!count||count<1){
                alert("无数据，无法导出Excel！");
                return false
            }
            var scope = [];/*待查询字段*/
            var header = [];/*表头*/
            var th = that.tableDom.find("th:not(.th)");
            var sqlTable = this.getAttribute("data");
            for(var i = 0;i<th.length;i++){
                scope.push(th[i].getAttribute("name"));
                header.push(th[i].innerText);
            }
            /*执行导出*/
            that.queryFrom.attr("method","post")
                    .attr("action",root+"/toExcel?scope="+scope.join()+"&scopeHeader="+header.join()+"&sqlTable="+sqlTable)
                    .submit();
        }).on("click","#Button_Add",function(){/*添加*/
            that.operation(-1,1);
        });
        /*数据表单操作*/
        this.tableDom.on("click","a.callOut",function(){/*点击外呼*/
            that.Callout($(this));
        }).on("click","input[type=button]",function(){ /*操作按钮*/
            var id = $(this).parent().parent().find("input[type=checkbox]").val();
            var index = $(this).parent().parent().index();
            that.operation(id,$(this).data("type"),index);
        }).on("click","td.dataSort",function(){ /*表头点击排序*/
            sorting(this,$(this).html());
        }).on("click","tr.th input[type=checkbox].tableCheck",function(){/*序号多选框的全选/不全选*/
            var tableCheck = document.getElementsByClassName("table_check");
            for(var a in tableCheck){
                tableCheck[a].checked = this.checked;
            }
        });
        /*页面数据信息显示栏*/
        this.$information.on("change","select",function(){/*每页显示数据变更*/
            that.QueryTable(5,that.parameter,"AdvancedQuery");
        }).on("click","a.paging",function(){/*页面跳转*/
            that.QueryTable(7,that.parameter,"AdvancedQuery");
        });
        /*上下页*/
        this.$Paging.on("click","a.paging",function(){
            var index = that.$Paging.find("a.paging").index($(this))+1;
            that.QueryTable(index,that.parameter,"AdvancedQuery");
        });
    };
    /*点击外呼操作*/
    this.Callout = function(TelDom){
        var Tel = TelDom.data("callout");
        if(!Tel) return false;
        var UserData = JSON.parse(localStorage.getItem("UserData"));
        var CustHrefURL = UserData[0][10]+'&format=json&sipnum='+UserData[0][2]+'&destnum='+Tel+'&outshowtel='+UserData[0][9]+'&calloid='+$("#CustNum").val()+"&callback=?";
        $.ajax({
            url: CustHrefURL,
            type:"get",
            dataType: "jsonp",
            jsonp: 'callback',
            success:function(data){
                var dataJson = eval(data);
                if(dataJson.call[0]["result"]=="300") alert("未签入！\n无法外拨号码");
            },error:function(data){
                console.log(data);
            }
        });
    };
    /*初始化table数据*/
    this.QueryTableInit = function(){
        /*删除原先的数据*/
        this.tableDom.find("tr:not(.th)").remove();
        /*页面信息初始化*/
        this.$tablePrompt.text("查询中......").show();
        this.$Paging.hide();
        this.$information.hide();
    };
    /*查询数据操作*/
    this.QueryTable = function(currentPageType,parameter,buttonType,otherQueryCondition){
        var that = this;
        var page;/*待查询页数*/
        var $prompt = that.$tablePrompt;/*页面查询状态显示*/
        var currentPage = this.$information.find(".currentPage").html();/*当前页*/
        var countPage = this.$information.find(".countPage").html();/*总页数*/
        var advanced = $("#advancedQuery");/*高级查询信息*/
        var jumpPage = null;/*跳转页*/
        /*初始化table数据*/
        this.QueryTableInit();
        this.getTableHeader1();
        var userData = JSON.parse(localStorage.getItem("UserData"))[0];
        /*页面上的查询条件*/
        var QueryForm = this.queryFrom.serialize();
        /*上下页按钮*/
        if(currentPageType==1){/*首页*/
            page = 1;
        }else if(currentPageType==2){/*上一页*/
            page = currentPage*1-1;
        }else if(currentPageType==3){/*下一页*/
            page = currentPage*1+1;
        }else if(currentPageType==4){/*尾页*/
            page = countPage;
        }else if(currentPageType==5){/*刷新当前页*/
            page = currentPage*1;
        }else if(currentPageType==6){/*查询按钮快速查询*/
            page = 1;
            advanced.val("undefined");/*重置高级查询内容*/
            this.buttonType = buttonType;
        }else if(currentPageType==7){/*页面跳转查询*/
            jumpPage = this.$information.find(".jumpPage").val();
            if(jumpPage*1>countPage*1){
                jumpPage = countPage;
            }else if(jumpPage<0){
                jumpPage = 0;
            }
            page = jumpPage*1;
        }
        var advancedQuery = advanced.val();/*本地保存的高级查询信息*/
        if(advancedQuery&&advancedQuery!="undefined"&&buttonType=="AdvancedQuery"){
            QueryForm += "&otherQueryCondition="+encodeURIComponent(advancedQuery);
        }
        if(!advancedQuery||advancedQuery=="undefined"){/*设置按钮条件*/
            buttonType = this.buttonType;
        }
        $.ajax({
            url:parameter["url"],
            type:"post",
            dataType: parameter["dataType"]?parameter["dataType"]:"json",
            "jsonp": "callback",
            async: parameter["async"]?parameter["async"]:false,
            data: "currentNum="+that.$information.find(".pageDataNumber").val()+"&currentPage="+page+"&buttonType="+buttonType+
            "&IsMgr="+userData[6]+"&worknum="+userData[1]+"&sipnum="+userData[2]+"&groupid="+userData[3]+"&roleid="+userData[4]+
            "&"+QueryForm+"&selectList="+that.selectList+"&textList="+that.textList,
            success: function(data){
                var dataJson = eval(data);
                /*显示表单数据*/
                if(dataJson.dataList.length>0){
                    $prompt.hide();
                    /*显示返回的数据*/
                    var setTable = new SetTable(that);
                    setTable.main(dataJson.dataList);
                    /*显示当前页数，总页数，总数据量*/
                    var countPage = dataJson.countPage;
                    var count = dataJson.count;
                    var currentPage = dataJson.currentPage;
                    that.$information.show();
                    that.$information.find(".count").html(count);
                    that.$information.find(".countPage").html(countPage);
                    that.$information.find(".currentPage").html(currentPage);
                    /*显示分页按钮*/
                    that.$Paging.show();
                    if(currentPage-countPage==0&&countPage-1>0){/*只显示首页*/
                        that.EndTableData(true,false);
                    }else if(currentPage-1==0&&countPage-1>0){/*只显示尾页*/
                        that.EndTableData(false,true);
                    }else if(countPage-1==0){/*什么都不显示*/
                        that.EndTableData(false,false);
                    }else if(currentPage-1>0&&countPage-currentPage>0){
                        that.EndTableData(true,true);
                    }
                    /*设置导出excel的语句*/
                    var toexcel = that.queryFrom.find(".jsExcel");
                    if(toexcel){
                        /** @namespace dataJson.sqlTable */
                        toexcel.attr("data", dataJson.sqlTable);
                    }
                }else{
                    $prompt.text("无数据");
                }
            },
            error: function(data){
                $prompt.text("查询出现错误！").show();
                console.log(data.responseText);
            }
        })
    };
    /*按钮操作函数,action表示操作类型,1为添加；2为更新;3为删除;4为其他*/
    this.operation = function(id,action,index){
        if(action==1||action==2){
            this.parameter.editInformation(id,action,index);
        }else if(action==3){
            this.parameter.delectInformation(id);
        }else if(action==4){
            this.parameter.buttonOther(id);
        }
    };
    /*页面上下页尾部信息栏显示*/
    this.EndTableData = function(First,End){
        var first = this.$Paging.find(".pagingFirst"),end = this.$Paging.find(".pagingEnd");
        if(First==true){
            first.show();
        }else if(First==false){
            first.hide();
        }
        if(End==true){
            end.show();
        }else if(End==false){
            end.hide();
        }
    };
}
/*生成数据table中每行数据*/
function SetTable(obj){
    this.tableJs = obj;
    this.main = function(dataList){
        var trList = this.setTableTr(dataList);
        this.tableJs.tableDom.append($(trList));
    };
    this.setTableTr = function(dataList){
        var tr = "";
        for(var i = 0;i<dataList.length;i++){
            tr += "<tr>";
            /*添加序号*/
            var trData = dataList[i];
            var index = "<input type='checkbox' class='table_check' value='"+trData[this.tableJs.idName]+"'>"+(i+1)+"</input>";
            tr += this.setTableTd(index,"id");
            /*添加数据*/
            var tableHeader = this.tableJs.header;
            for(var j = 0;j<tableHeader.length;j++){
                tr += this.setTableTd(trData[tableHeader[j]],tableHeader[j],trData);
            }
            /*添加按钮操作*/
            tr += "<td>";
            var buttonList = this.tableJs.parameter["button"];
            for(var b=0;b<buttonList.length;b++){
                tr += "<input type='button' data-type='"+buttonList[b][0]+"' class='buttonEdit btn btn-primary' value='"+buttonList[b][1]+"'></input>";
            }
            tr += "</td></tr>";
        }
        return tr;
    };
    this.setTableTd = function(data,dataName,trData){
        if(data==null){data=""}
        if(this.tableJs.parameter.isChange){
            if("changeData" in this){
                return this.changeData(data,dataName,trData);
            }else{
                return changeData(data,dataName,trData);
            }
        }
        return "<td>"+data+"</td>"
    }
}