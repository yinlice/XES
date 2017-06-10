package Wxzt.servlet.SMS;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016-5-18.
 */
public class sendedSql extends Query {
    /*短信发件箱页面的查询条件，必填写*/
    public String setSendedStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本输入框*/
        String conditions = request.getParameter("Conditions");
        if(conditions!=null&&!conditions.equals("")){
            strSQL += "and (mobile = '"+conditions+"' or smsContent like '%"+conditions+"%')";
        }
        /*权限判定*/
        String IsMgr = request.getParameter("IsMgr");//权限设置:1为个人；2为组别；3为全部
        if(IsMgr.equals("1")){//
            String worknum = request.getParameter("worknum");//坐席工号
            strSQL += " and worknum = '"+worknum+"'";
        }else if(IsMgr.equals("2")){
            String groupid = request.getParameter("groupid");//组别名称ID
            strSQL += " and worknum in (select worknum from dddWork where groupid = '"+groupid+"')";
        }
        return strSQL;
    }

    /*设置短信发件箱分页查询基础查询语句*/
    public String setSendedBasisSql(){
        return " WX_SMS_Sended ";
    }

    /*设置短信发件箱分页数据表头(除去下拉框)信息*/
    public String[] getSendedList(){
        String[] otherList = {"nID","mobile","smsContent","dt","custnum","mTreport","mTdt","mTid"};
        return otherList;
    }

    /*设置短信发件箱分页数据下拉框表头信息*/
    public String[] getSendedSelectList(){
        String[] selectList = {};
        return selectList;
    }

    /*设置短信发件箱分页数据坐席表头信息*/
    public String[] getSendedWorkList(){
        String[] selectList = {"worknum"};
        return selectList;
    }

    /*设置短信发件箱分页数据工作组表头信息*/
    public String[] getSendedGroupList(){
        String[] selectList = {};
        return selectList;
    }

    /*设置短信发件箱分页数据中下拉框信息的联表查询*/
    public String getSendedSelectSql(String sql){
        setSqlSelect_T("a", getSendedSelectList(), getSendedList(), getSendedWorkList(), getSendedGroupList(), "px_CRM_Cust");
        /*设置含有下拉框信息的查询语句*/
        return "select "+sqlHeader+" from ("+sql+") as a "+sqlJoin;
    }

    /*短信发件箱分页查询*/
    public Object sendedQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setSendedStrSql(request));
        /*基础信息查询表*/
        String dataTable = setSendedBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "nid", "dt");
        /*分页数据中下拉框信息的联表查询*/
        sql = getSendedSelectSql(sql);
        System.out.println("短信发件箱语句："+sql);
        /*获取短信发件箱数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getSendedList(), getSendedWorkList()), "sms"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("smsSended"+"ToExcel",sendedExcel(strSql));
        json.setSqlTable("smsSended");
        return json;
    }

    /*获取短信发件箱分页查询的导出Excel语句*/
    public String sendedExcel(String str){
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(setSendedBasisSql(), str, "dt");
        /*分页数据中下拉框信息的联表查询*/
        sql = getSendedSelectSql(sql);
        return sql;
    }
}
