package Wxzt.servlet.Import;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-11.
 */
public class stationSql extends Query {
    /*导入批次页面的查询条件，必填写*/
    public String setStationStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本输入框*/
        String Conditions = request.getParameter("Conditions");
        if(!Conditions.equals("")){
            String action = request.getParameter("ConditionsSelect");
            /*输入条件种类:1为导入批号,2为导入人工号，3为导入人名称*/
            if(action.equals("0")){
                strSQL += "and (ImportID = '"+Conditions+"' or CreatWorker = '"+Conditions+"' or workname = '"+Conditions+"')";
            }else if(action.equals("1")){
                strSQL += "and ImportID = '"+Conditions+"'";
            }else if(action.equals("2")){
                strSQL += "and CreatWorker = '"+Conditions+"'";
            }else if(action.equals("3")){
                strSQL += "and workname = '"+Conditions+"'";
            }
        }
        return strSQL;
    }

    /*设置导入批次查询基础查询语句*/
    public String getStationBasisSql(){
        return " px_CRM_ImportStation ";
    }

    /*设置导入批次数据表头(除去下拉框)信息*/
    public String[] getStationList(){
        String[] otherList = {"id","importID","creatTime","totalNum","remainNum"};
        return otherList;
    }

    /*设置客户管理分页数据下拉框表头信息*/
    public String[] getStationSelectList(){
        String[] selectList = {"isShow"};
        return selectList;
    }

    /*设置客户管理分页数据坐席表头信息*/
    public String[] getStationWorkList(){
        String[] selectList = {"custOwner","creatWorker"};
        return selectList;
    }

    /*设置导入批次数据中下拉框信息的联表查询*/
    public String getStationSelectSql(String sql){
        String[] group = {};
        setSqlSelect_T("a", getStationSelectList(), getStationList(), getStationWorkList(), group, "px_CRM_Station");
        return " select b.remainNum,"+sqlHeader+" from ("+sql+") as a " +sqlJoin+
                "left join (select count(*) as remainNum,ImportID from px_CRM_Cust where (WorknumOwner not in (select worknum from dddWork) or  WorknumOwner is null) group by ImportID) as b on a.ImportID = b.ImportID";
    }

    /*导入批次查询*/
    public Object stationQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setStationStrSql(request));
        /*基础信息查询表*/
        String dataTable = getStationBasisSql();
        /*初始化页面*/
        init(request, dataTable, strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "creatTime");
        /*添加下拉框的联表查询*/
        sql = getStationSelectSql(sql)+" ORDER BY creatTime DESC";
        System.out.println("查询导入批次："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list1 = addList(getStationSelectList(),getStationWorkList());
        json.setDataList(sqlListData(sql, addList(list1,getStationList()), "station"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("importStation" + "ToExcel", importStationExcel(strSql));
        json.setSqlTable("importStation");
        return json;
    }

    /*获取导入情况查询的导出Excel语句*/
    public String importStationExcel(String str){
        Query query = new Query();
        /*分页查询中导出excel语句*/
        String sql = query.setExcelSql(getStationBasisSql(), str, "CreatTime");
        /*分页数据中下拉框信息的联表查询*/
        sql = getStationSelectSql(sql);
        return sql;
    }

    /*更新可提取*/
    public boolean updataStationIsShow(HttpServletRequest request){
        String list = request.getParameter("checkIdList");
        String val = request.getParameter("val");
        int val1 = Objects.equals(val, "-1") ?0:1;
        String sql = " update px_CRM_ImportStation set isShow = "+val1+" where id in ("+list+")";
        System.out.println("更新可提取操作："+sql);
        return updataSql(sql);
    }
}
