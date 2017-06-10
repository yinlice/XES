package Wxzt.servlet.Cdr;


import Wxzt.servlet.Common.QueryForCti;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-13.
 */
public class cdrTableSql extends QueryForCti {
    /*查询客户录音的查询条件，必填写*/
    public String setCdrStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本框*/
        String Conditions = request.getParameter("Conditions");/*文本框内容*/
        String cdrScopeOfQuery = request.getParameter("ConditionsSelect");/*文本框范围*/
        if(!Objects.equals(Conditions,null)&&!Objects.equals(Conditions, "")){
            if(cdrScopeOfQuery.equals("0")){/*全选(排除ID)*/
                strSQL += "and (sipnum = '" + Conditions + "' or telnum = '" + Conditions + "' or worknum = '" + Conditions + "')";
            }else if(cdrScopeOfQuery.equals("1")){/*编号*/
                strSQL += "and id = '" + Conditions + "' ";
            }else if(cdrScopeOfQuery.equals("2")){/*坐席*/
                strSQL += "and worknum = '" + Conditions + "' ";
            }else if(cdrScopeOfQuery.equals("3")){/*分机*/
                strSQL += "and sipnum = '" + Conditions + "' ";
            }else if(cdrScopeOfQuery.equals("4")){/*呼叫号码*/
                strSQL += "and telnum = '" + Conditions + "' ";
            }else if(cdrScopeOfQuery.equals("5")){/*群呼编号*/
                strSQL += "and CalloutTaskID = '" + Conditions + "' ";
            }
        }
        /*呼叫方向*/
        String direction = request.getParameter("direction");
        if (!Objects.equals(direction,null)&&!Objects.equals(direction, "allDirection")){
            strSQL += " and Direction = '"+direction+"'";
        }
        /*工作组*/
        String groupnum = request.getParameter("groupName");//查询组别
        if (!Objects.equals(groupnum,null)&&!Objects.equals(groupnum, "allGroup")){
            strSQL += " and groupid = '"+groupnum+"'";
        }
        /*评价*/
        String scoreSatis = request.getParameter("scoreSatis");//非留言查询下，查询评价
        if (!Objects.equals(scoreSatis,null)&&!Objects.equals(scoreSatis, "allEvaluation")){
            strSQL += " and scoreSatis = '"+scoreSatis+"'";
        }
        /*呼叫时间*/
        String firstDT = request.getParameter("firstDt");//查询开始时间 min
        String endDT = request.getParameter("endDt");//查询开始时间 max
        if(firstDT!=null&& !Objects.equals(firstDT, "")){
            strSQL += " and dtCreate >'"+firstDT+"' ";
        }
        if(endDT!=null&& !Objects.equals(endDT, "")){
            strSQL += " and dtCreate <'"+endDT+"' ";
        }
        return strSQL;
    }

    public String setStrSqlType(HttpServletRequest request,String StrSqlOther){
        /*获取录音在CTI中分类，查询条件*/
        String ctiName = getCompanyCode();
        String strSQL = setStrSql(request, StrSqlOther);
        strSQL = (ctiName==null|| Objects.equals(ctiName, ""))?strSQL:(strSQL+" and companyCode='"+ctiName+"'");

        /*获取客户在左侧的分类，查询条件*/
        String tableFormType = request.getParameter("tableFormType");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        /*昨日日期*/
        Date d = new Date(System.currentTimeMillis()-1000*60*60*24);
        String Yesterday = formatter.format(d);
        /*今日日期*/
        Date d_1 = new Date();
        String todayTime = formatter.format(d_1);
        /*获取本周日期*/
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 1);/*本周第一天，以星期日开始*/
        String firstDayofWeek = formatter.format(new Date(c.getTime().getTime()));
        if(Objects.equals(tableFormType, "")||Objects.equals(tableFormType, "record-today")){//今日录音
            strSQL += " and dtCreate > '" + todayTime + "'";
        }else if(Objects.equals(tableFormType, "record-yesterday")){//昨日录音
            strSQL += " and dtCreate > '" + Yesterday + "' and dtCreate < '" + todayTime + "'";
        }else if(Objects.equals(tableFormType, "record-week")){//本周录音
            strSQL += " and dtCreate > '" + firstDayofWeek+"'" ;
        }else if(Objects.equals(tableFormType, "record-noAnswer")){//漏话录音
            strSQL += " and Direction='in' and TimelenSpeak = '0' ";
        }else if(Objects.equals(tableFormType, "record-noCallback")){//漏话录音-未回拨
            strSQL += " and Direction='in' and TimelenSpeak = '0' and IsRead!='1' ";
        }else if(Objects.equals(tableFormType, "record-voicemail")){//留言录音
            strSQL +=  " and TicketType=2";
        }else if(Objects.equals(tableFormType, "record-groupCall")){//群呼录音
            strSQL +=  " and CalloutTaskID!=-1";
        }
        /*权限*/
        String IsMgr = request.getParameter("IsMgr");//权限
        if(IsMgr.equals("1")){/*个人*/
            String worknum = request.getParameter("worknum");//权限
            strSQL += " and Worknum = '"+worknum+"'";
        }else if(IsMgr.equals("2")){/*工作组*/
            String groupid = request.getParameter("groupid");//权限
            strSQL += " and Worknum in (select worknum from dddWork where groupid ='"+groupid+"')";
        }
        System.out.println("录音查询条件："+strSQL);
        return strSQL;
    }


    /*设置客户录音数据表头(除去下拉框)信息*/
    public String[] getCdrList(){
        String[] otherList = {"id","recfilename","callret","sipnum","telnum","timelenSpeak","dtCreate","dthangup","timelenTotalCall",
                "hotLine","ticketType","remark","isRead","qualityNum","qualityNote","qualityTime","groupid","worknum"};
        return otherList;
    }

    /*设置客户录音数据下拉框表头信息*/
    public String[] getCdrSelectList(){
        String[] selectList = {"direction","isRead","scoreSatis"};
        return selectList;
    }

    /*设置客户录音数据坐席表头信息*/
    public String[] getCdrWorkList(){
        String[] selectList = {"qualityWorknum"};
        return selectList;
    }

    /*设置客户录音数据工作组表头信息*/
    public String[] getCdrGroupList(){
        String[] selectList = {};
        return selectList;
    }

    /*设置客户录音基础查询语句*/
    public String setCdrBasisSql(){
        return " (select m.*,m.groupnum as groupid from dddCdr as m) as cdr ";
    }

    /*设置客户录音数据中下拉框信息的联表查询*/
    public String getCdrSelectSql(String sql){
        setSqlSelect_T("a",getCdrSelectList(),getCdrList(),getCdrWorkList(),getCdrGroupList(),"px_CRM_Cdr");
        /*除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询和公用表表达式中无效。*/
        return "select (case when a.ticketType=2 then '留言' when a.timelenSpeak=0 then '未接' else '接通' end) as callret," +
                "a.id as cdrId,a.groupName as groupid,a.workname+'-'+a.worknum as worknum,a.worknum as worknum_1,"+sqlHeader+" from ("+sql+") as a "+sqlJoin;
    }

    /*客户管理分页查询*/
    public Object cdrQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSqlType(request, setCdrStrSql(request));
        /*基础信息查询表*/
        String dataTable = setCdrBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "dtCreate");
        /*分页数据中下拉框信息的联表查询*/
        sql = getCdrSelectSql(sql);
        System.out.println("查询录音语句："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list1 = addList(getCdrList(), getCdrSelectList());
        list1 = addList(list1, new String[]{"cdrId"});
        json.setDataList(sqlListData(sql, addList(list1, getCdrWorkList()), "cdr"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("cdr"+"ToExcel",cdrExcel(strSql));
        json.setSqlTable("cdr");
        return json;
    }

    /*打开录音详情页面时获取客户数据*/
    public Object cdrOneQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = "";
        /*客户管理页面双击打开客户资料页面*/
        String id = request.getParameter("id");
        if(id!=null&&!id.equals("")){
            strSql = " and id = '"+id+"'";
        }
        /*基础信息查询表*/
        String dataTable = setCdrBasisSql();
        /*查询单个数据语句*/
        String sql = setOneSql(dataTable, strSql);
        /*分页数据中下拉框信息的联表查询*/
        sql = getCdrSelectSql(sql);
        System.out.println("录音获取数据语句：" + sql);
        /*获取录音数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list1 = addList(getCdrList(), getCdrSelectList());
        json.setDataList(sqlListData(sql, addList(list1, getCdrWorkList()), "cdr"));
        return json;
    }

    /*获取客户管理分页查询的导出Excel语句*/
    public String cdrExcel(String str){
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(setCdrBasisSql(), str, "dtCreate");
        /*分页数据中下拉框信息的联表查询*/
        sql = getCdrSelectSql(sql);
        return sql;
    }

    /*首页弹屏时获取未接电话数据*/
    public Object queryMainCdr(HttpServletRequest request){
        boolean isQueryData = true;
        queryJson json = new queryJson();
        /*获取录音在CTI中分类，查询条件*/
        String ctiName = getCompanyCode(),ctiStr = "";
        if(!Objects.equals(ctiName, "")){
            ctiStr = " and companyCode='"+ctiName+"'";
        }
        String sqlStr = " Direction='in' and TimelenSpeak = '0' and IsRead!='1' ";
        /*设置是否查询录音数据，只需要总量*/
        String telnum = request.getParameter("cdrTelnum");
        if(telnum!=null){
            isQueryData = false;
            if(!Objects.equals(telnum, "")){
                sqlStr += " and telnum = '"+telnum+"' ";
            }
        }
        String sqlNum = "select count(*) from dddCdr where "+sqlStr+ctiStr;
        System.out.println("查询未接数量："+sqlNum);
        String num = sqlOneData(sqlNum);
        if(num==null){num = "0";}
        json.setCount(Integer.valueOf(num));
        if(isQueryData&&!Objects.equals(num, "0")){
            String sql = "select * from dddCdr where "+sqlStr+ctiStr+" order by dtCreate desc limit 1";
            System.out.println("查询未接数据："+sql);
            String[] list1 = {"sipnum","telnum","dtCreate"};
            json.setDataList(sqlListData(sql, list1, "cdr"));
        }
        return json;
    }

    /*录音页面更新*/
    public boolean updataCdr(HttpServletRequest request){
        //获取form传递的参数
        String ID = request.getParameter("ID");
        String Remark = request.getParameter("Remark");
        int IsRead = Integer.parseInt(request.getParameter("IsRead"));
        String sql = "update dddCdr set Remark='"+Remark+"',IsRead='"+IsRead+"' where ID="+ID;
        return updataSql(sql);
    }
    /*录音质检更新*/
    public boolean updataCdrQuality(HttpServletRequest request){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        //获取form传递的参数
        String ID = request.getParameter("ID");
        String Remark = request.getParameter("Remark");
        int IsRead = Integer.parseInt(request.getParameter("IsRead"));
         /*关于语音质检的参数*/
        String qualityNum = request.getParameter("qualityNum");
        String qualityNote = request.getParameter("qualityNote");
        String qualityWorknum = request.getParameter("qualityWorknum");

        String sql = "update dddCdr set Remark='"+Remark+"',IsRead='"+IsRead+"',qualityNum='"+qualityNum+"',\n" +
                "qualityNote='"+qualityNote+"',qualityTime='"+newTime+"',qualityWorknum='"+qualityWorknum+"'  where ID="+ID;
        return updataSql(sql);
    }
    /*回访更新*/
    public boolean updataCdrNoCall(HttpServletRequest request){
        String dh = request.getParameter("dh");
        String sql = "update dddCdr set IsRead='1' where telnum = '"+dh+"' and Direction='in' and TimelenSpeak = '0' and IsRead!='1'";
        System.out.println("更新回拨数据："+sql);
        return updataSql(sql);
    }
}
