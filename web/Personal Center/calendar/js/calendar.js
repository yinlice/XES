function Calendar(tableDom){
    this.tableDom = tableDom;
    this.newSelectedDay = new Date();
    /*选择当天*/
    this.showToday = function(){
        var myDate = new Date();
        this.init(myDate.getFullYear(),myDate.getMonth());
        $(".today_title").parent().parent().addClass("date_selected");
    };
    this.init = function(year,mouth){
        /*设置今年2月的天数，并将从1月到12月的天数添加到数组dayMouth中*/
        var dayMouth = [31,28+this.IsLeap(year),31,30,31,30,31,31,30,31,30,31];
        /*获取当月第一天的星期数*/
        var date = new Date(year,mouth,1);
        $(".g_title").attr("dateFull",date);
        this.nowDay = date.getDay();
        this.mouthDays = dayMouth[mouth];/*本月的天数*/
        this.setCalendar(year,mouth);
        /*设置上下页切换元素*/
        this.setTitleId(year,mouth);
        /*查询数据*/
        this.getMouthData();
    };
    /*生成日历*/
    this.setCalendar = function(year,mouth){
        /*获取月日历的行数*/
        var trCount = Math.ceil((this.mouthDays+this.nowDay)/7);
        /*设置每行数据高度*/
        this.setContentHeight(trCount);
        var nowDat = new Date();
        var nowYear = nowDat.getFullYear(),nowMouth = nowDat.getMonth(),nowDay = nowDat.getDate();
        /*生成日历*/
        var tableContent = "",tableBorder = "";
        for(var i = 0;i<trCount;i++){
            /*日历每行数据的开头*/
            tableContent += "<div class='week_line'><table><tr>";
            tableBorder += "<div class='week_line'><table><tr>";
            for(var j = 0;j<7;j++){
                var idx = i*7+j;
                var data = idx-this.nowDay+1;
                if(j==0){
                    tableContent += "<td class='week_1st_td weekend'>";
                    tableBorder += "<td class='week_1st_td weekend'>";
                }else if(j==6){
                    tableContent += "<td class='week_last_td weekend'>";
                    tableBorder += "<td class='week_1st_td weekend'>";
                }else {
                    tableContent += "<td class=''>";
                    tableBorder += "<td class=''>";
                }
                /*生成日历每格显示数据*/
                if(data>0&&data<this.mouthDays+1){
                    if(year==nowYear&&mouth==nowMouth&&data==nowDay){
                        tableContent += this.setTd(data,year,mouth,true,nowMouth,nowDay)+"</td>";
                    }else{
                        tableContent += this.setTd(data,year,mouth,false)+"</td>";
                    }
                }else{
                    tableContent += this.setTd(0,year,mouth)+"</td>";
                }
                tableBorder += "</td>";
            }
            tableContent += "</tr></table></div>";
            tableBorder += "</tr></table></div>";
        }
        var html = '<div class="table_content">'+tableContent+'</div>'+'<div class="table_border">'+tableBorder+'' +
                '<div class="cal_bottom_line"><div class="cal_bottom_in"></div></div></div>';
        $(this.tableDom).children().remove().end().append($(html));
    };
    this.setTd = function(data,year,mouth,today,nowmouth,nowday){
        var topDataClass = today?"month_date_compete month_date_num":"month_date_num";
        var topData = today?"今日("+(nowmouth+1)+"月"+nowday+"日)":data;
        var topClass = today?"today_title month_date":"month_date";
        var divHtml_top_data = "<span class='right'>五月</span>" +/*顶部时间数据*/
                "<span class='"+topDataClass+"'>"+topData+"</span>" ;
        var divHtml_visit = "<ul class='month_event'><li class='addevent'>" +/*当日日程显示*/
                //"<a href='javascript:;' hidefocus>新建事件...</a>" +
                "<a href='javascript:;' hidefocus>新建事件...</a>" +
                "<span class='event_time'></span>" +
                "</li></ul>";
        var divHtml = "<div class='selected_border'>" +
                "<div class='"+topClass+"'>" +divHtml_top_data+"</div>" +divHtml_visit+
                "</div>" +
                "<div class='month_event_more'><span class='arrow_b'></span></div>";
        var boxId = year+"-"+((mouth+1)>9?(mouth+1):("0"+(mouth+1)))+"-"+(data>9?data:("0"+data));
        var boxDate = year+"-"+(mouth+1)+"-"+data;
        return "<div class='date_box' id='div-"+boxId+"' date='"+boxDate+"'>"+divHtml+"</div>";
    };
    this.changeByMouth = function(year,mouth,num){
        var date = new Date(year,mouth,1);
        var betweenDay = (num==1)?parseInt(86400000 * this.mouthDays):parseInt(-86400000);
        var c_mouth = new Date(Date.parse(date) + betweenDay);
        this.init(c_mouth.getFullYear(),c_mouth.getMonth());
    };
    this.setContentHeight = function(count){
        if(count==6){
            $(".cal_m_content").addClass("sixweeks");
        }else{
            $(".cal_m_content").removeClass("sixweeks");
        }
    };
    /**
     * @return {number}判断是否为闰年，是则返回1，否则返回0
     */
    this.IsLeap = function(year){
        return (year%100==0)?((year%400==0)?1:0):((year%4==0)?1:0);
    };
    this.setTitleId = function(year,mouth){
        var title = year+"-"+(mouth+1);
        $(".g_title").find("h1").html(year+"年"+(mouth+1)+"月").end().attr("date",title).attr("id","title_"+title);
    };
    this.getMouthData = function(){
        var worknum = JSON.parse(localStorage.getItem("UserData"))[0][1];//坐席信息
        var newTime = $(".g_title").attr("dateFull");
        var nextMouth = new Date(Date.parse(newTime) + 86400000 * this.mouthDays);
        var that = this;
        $.ajax({
            url: "../query/visit?action=2",
            type:"post",
            dataType: "json",
            data:"worknum="+worknum+"&firstDt="+that.setQueryTime(newTime)+"&endDt="+that.setQueryTime(nextMouth),
            success:function(data){
                var dataJson = eval(data)["dataList"];
                that.dealQueryData(dataJson);
            },error:function(data){

            }
        });
    };
    this.setQueryTime = function(date){
        date = new Date(Date.parse(date));
        var mouth = (date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1));
        return date.getFullYear()+"-"+mouth;
    };
    /*生成日历数据*/
    this.dealQueryData = function(dataList){
        var date = null,html = "";
        for(var i=0;i<dataList.length;i++){
            var listDate = dataList[i].dtVisit.replace("T"," ").split(" ");
            if(listDate[0]==date){
                html += this.dealQueryData_li(dataList[i]);
            }else{
                $("#div-"+date).find("ul.month_event").find("li:not(.addevent)").remove().end().prepend($(html));
                date = listDate[0];
                html = this.dealQueryData_li(dataList[i]);
            }
            /*最后的数据更新*/
            if(i==dataList.length-1){
                $("#div-"+date).find("ul.month_event").find("li:not(.addevent)").remove().end().prepend($(html));
            }
        }
    };
    this.dealQueryData_li = function(dataList){
        return "<li><p class='event_msg' mobile='"+dataList.mobile+"'>"+dataList.dtVisit.replace("T"," ").split(" ")[1]+"&nbsp;"+dataList.custName+"</p></li>";
    };
    /*获取当前选中日期,参数为与当前选中日期差异时间*/
    this.getSelectedDayForH5 = function(num){
        num = (arguments.length != 1) ? 0 : num;
        var date = new Date(Date.parse(this.newSelectedDay) + 86400000 * num);
        var mouth = (date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1));
        var day = (date.getDate())>9?(date.getDate()):("0"+(date.getDate()));
        return date.getFullYear()+"-"+mouth+"-"+day;
    }
}

/*日日历的相关操作*/
function CalendarByDay(selectedDay){
    this.selectedDay = selectedDay;/*Date()对象*/
    /*显示基础数据*/
    this.init = function(){
        /*设置年月日*/
        this.yearMouthDay = this.selectedDay.getFullYear()+"年"+(this.selectedDay.getMonth()+1)+"月"+this.selectedDay.getDate()+"日";
        /*设置星期*/
        var common = new CalendarCommon();
        this.weekDay = "星期"+common.getWeekDay(this.selectedDay.getDay());
        /*设置农历日期*/
        var lunar = new LunarCalendar();
        this.lunarDay = lunar.GetLunarDay(this.selectedDay.getFullYear(),this.selectedDay.getMonth()+1,this.selectedDay.getDate());
        /*更新数据*/
        this.setDataByDay();
    };
    /*更新数据*/
    this.setDataByDay = function(){
        /*设置头部信息*/
        $(".g_title").find("h1").html(this.yearMouthDay+" "+this.weekDay);
        /*设置左侧显示信息*/
        $(".day_info").find(".day_num").html(this.selectedDay.getDate())
                .next().html(this.yearMouthDay+" "+this.weekDay)
                .next().html(this.lunarDay);
    };
    /*头部信息中的上下日切换*/
    this.changeByDay = function(num){
        var betweenDay = parseInt(86400000 * num);
        this.selectedDay = new Date(Date.parse(this.selectedDay) + betweenDay);
        this.init();
    };
}

/*日历的公共函数*/
function CalendarCommon(){
    this.getWeekDay = function(num){
        var weekList = ["天","一","二","三","四","五","六"];
        return weekList[num];
    }
}
/*table数据操作*/
function changeData(data,dataName){
    if(dataName=="mobile"||dataName=="telnum"){
        data = "<a href='javascript:void(0)' class='callOut' data-callout='"+data+"'>"+getEncryptTel(data)+"</a>";
    }
    return "<td>"+data+"</td>";
}