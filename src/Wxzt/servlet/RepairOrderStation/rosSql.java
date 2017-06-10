package Wxzt.servlet.RepairOrderStation;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-17.
 */
public class rosSql extends Query {
    /*工单查询的查询条件，必填写*/
    public String setRosStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本输入框*/
        String conditions = request.getParameter("Conditions");
        if(conditions!=null&& !Objects.equals(conditions, "")){
            strSQL+=" and (gdnum like '%"+conditions+"%' or gdInfor like '%"+conditions+"%') ";
        }
        /*工单编号*/
        String gdnum = request.getParameter("gdnum");
        if(gdnum!=null&&!gdnum.equals("")){
            strSQL += "and gdnum = '"+gdnum+"'";
        }
        return strSQL;
    }

    /*设置工单查询数据表头(除去下拉框)信息*/
    public String[] getRosList(){
        String[] otherList = {"gdnum","gdInfor","dtUpdate","custTel"};
        return otherList;
    }

    /*设置工单查询数据下拉框表头信息*/
    public String[] getRosSelectList(){
        String[] selectList = {"state","emergency","problemType"};
        return selectList;
    }

    /*设置工单查询数据坐席表头信息*/
    public String[] getRosWorkList(){
        String[] selectList = {"updataWorknum","worknumReceive","worknumRecipient"};
        return selectList;
    }

    /*设置工单查询数据工作组表头信息*/
    public String[] getRosGroupList(){
        String[] selectList = {};
        return selectList;
    }

    /*设置工单查询基础查询语句*/
    public String getRosBasisSql(){
        return " px_CRM_GDStation ";
    }

    /*设置工单查询数据中下拉框信息的联表查询*/
    public String getRosSelectSql(String sql){
        setSqlSelect_T("a",getRosSelectList(),getRosList(),getRosWorkList(),getRosGroupList(),"px_CRM_GD");
        /*除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询和公用表表达式中无效。*/
        return "select m.questionsBriefly,m.custname,m.gdtheme,"+sqlHeader+" from ("+sql+") as a "+sqlJoin+
                " left join px_crm_gd as m on a.gdnum = m.gdnum";
    }

    /*工单查询*/
    public Object rosQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setRosStrSql(request));
        /*基础信息查询表*/
        String dataTable = getRosBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "gdnum");
        System.out.println("查询工单处理过程分页语句："+sql);
        /*分页数据中下拉框信息的联表查询*/
        sql = getRosSelectSql(sql);
        System.out.println("查询工单处理过程语句："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list1 = addList(getRosList(),getRosSelectList());
        String[] list2 = {"gdtheme","custname","questionsBriefly"};/*待添加的字段*/
        json.setDataList(sqlListData(sql, addList(list1,addList(list2, getRosWorkList())), "repairSta"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("repairSta" + "ToExcel", rosExcel(strSql));
        json.setSqlTable("repairSta");
        return json;
    }

    /*获取工单分页查询的导出Excel语句*/
    public String rosExcel(String str){
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(getRosBasisSql(), str, "gdnum");
        /*分页数据中下拉框信息的联表查询*/
        sql = getRosSelectSql(sql);
        return sql;
    }
}
