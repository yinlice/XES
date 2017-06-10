package Wxzt.servlet.SMS;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016-5-18.
 */
public class waitSendSql extends Query {
    /*待发送短信页面的查询条件，必填写*/
    public String setWaitSendStrSql(HttpServletRequest request){
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

    /*设置待发送短信分页查询基础查询语句*/
    public String setWaitSendBasisSql(){
        return " WX_SMS_waitSend ";
    }

    /*设置待发送短信分页数据表头(除去下拉框)信息*/
    public String[] getWaitSendList(){
        String[] otherList = {"nID","mobile","smsContent","dt","custnum"};
        return otherList;
    }

    /*设置待发送短信分页数据下拉框表头信息*/
    public String[] getWaitSendSelectList(){
        String[] selectList = {};
        return selectList;
    }

    /*设置待发送短信分页数据坐席表头信息*/
    public String[] getWaitSendWorkList(){
        String[] selectList = {"worknum"};
        return selectList;
    }

    /*设置待发送短信分页数据工作组表头信息*/
    public String[] getWaitSendGroupList(){
        String[] selectList = {};
        return selectList;
    }

    /*设置待发送短信分页数据中下拉框信息的联表查询*/
    public String getWaitSendSelectSql(String sql){
        setSqlSelect_T("a", getWaitSendSelectList(), getWaitSendList(), getWaitSendGroupList(), getWaitSendWorkList(), "px_CRM_Cust");
        /*设置含有下拉框信息的查询语句*/
        return "select "+sqlHeader+" from ("+sql+") as a "+sqlJoin;
    }

    /*待发送短信分页查询*/
    public Object waitSendQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setWaitSendStrSql(request));
        /*基础信息查询表*/
        String dataTable = setWaitSendBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "nid", "dt");
        /*分页数据中下拉框信息的联表查询*/
        sql = getWaitSendSelectSql(sql);
        System.out.println("待发送短信查询语句："+sql);
        /*获取待发送短信数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getWaitSendList(), getWaitSendWorkList()), "sms"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("smsWait"+"ToExcel",waitSendExcel(strSql));
        json.setSqlTable("smsWait");
        return json;
    }

    /*获取待发送短信分页查询的导出Excel语句*/
    public String waitSendExcel(String str){
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(setWaitSendBasisSql(), str, "dt");
        /*分页数据中下拉框信息的联表查询*/
        sql = getWaitSendSelectSql(sql);
        return sql;
    }
}

