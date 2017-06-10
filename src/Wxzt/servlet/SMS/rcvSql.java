package Wxzt.servlet.SMS;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016-5-18.
 */
public class rcvSql extends Query {
    /*短信收件箱页面的查询条件，必填写*/
    public String setRcvStrSql(HttpServletRequest request){
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

    /*设置短信收件箱分页查询基础查询语句*/
    public String setRcvBasisSql(){
        return " WX_SMS_Rcv ";
    }

    /*设置短信收件箱分页数据表头(除去下拉框)信息*/
    public String[] getRcvList(){
        String[] otherList = {"nID","mobile","smsContent","dt","custnum","readFlag"};
        return otherList;
    }

    /*设置短信收件箱分页数据下拉框表头信息*/
    public String[] getRcvSelectList(){
        String[] selectList = {};
        return selectList;
    }

    /*设置短信收件箱分页数据坐席表头信息*/
    public String[] getRcvWorkList(){
        String[] selectList = {"worknum"};
        return selectList;
    }

    /*设置短信收件箱分页数据工作组表头信息*/
    public String[] getRcvGroupList(){
        String[] selectList = {};
        return selectList;
    }

    /*设置短信收件箱分页数据中下拉框信息的联表查询*/
    public String getRcvSelectSql(String sql){
        setSqlSelect_T("a", getRcvSelectList(), getRcvList(), getRcvWorkList(), getRcvGroupList(), "px_CRM_Cust");
        /*设置含有下拉框信息的查询语句*/
        return "select "+sqlHeader+" from ("+sql+") as a "+sqlJoin;
    }

    /*短信收件箱分页查询*/
    public Object rcvQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setRcvStrSql(request));
        /*基础信息查询表*/
        String dataTable = setRcvBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "nid", "dt");
        /*分页数据中下拉框信息的联表查询*/
        sql = getRcvSelectSql(sql);
        System.out.println("短信收件箱语句："+sql);
        /*获取短信收件箱数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getRcvList(), getRcvWorkList()), "sms"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("smsRcv"+"ToExcel",rcvExcel(strSql));
        json.setSqlTable("smsRcv");
        return json;
    }

    /*获取短信收件箱分页查询的导出Excel语句*/
    public String rcvExcel(String str){
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(setRcvBasisSql(), str, "dt");
        /*分页数据中下拉框信息的联表查询*/
        sql = getRcvSelectSql(sql);
        return sql;
    }
}
