package Wxzt.servlet.CustHead;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2016-11-10.
 */
public class custHeadTPSql extends Query {
    /*客户管理页面的查询条件，必填写*/
    public String setCustHeadTPStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本输入框*/
        String conditions = request.getParameter("Conditions");
        if(conditions!=null&&!conditions.equals("")){
            strSQL += "and ( hotLine = '"+conditions+"' or callInLine = '"+conditions+"' or dest = '"+conditions+"')";
            System.out.println("strSQL::::"+strSQL);
        }
        return strSQL;
    }

    /*设置客户管理分页查询基础查询语句*/
    public String setCustHeadTPBasisSql(){
        return " px_CRM_CustHead ";
    }

    /*设置客户管理分页数据表头(除去下拉框)信息*/
    public String[] getCustHeadTPList(){
        String[] otherList = {"id","hotLine","callInLine","dest","greetings","note"};
        return otherList;
    }

    /*分页查询*/
    public Object custHeadTPQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setCustHeadTPStrSql(request));
        strSql = setStrSql(request,strSql);
        /*基础信息查询表*/
        String dataTable = setCustHeadTPBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "id");
        /*分页数据中下拉框信息的联表查询*/
        System.out.println("客户查询语句："+sql);
        /*获取数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, getCustHeadTPList(), "custHeadTP"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("custHeadTP"+"ToExcel",custHeadTPExcel(strSql));
        json.setSqlTable("custHeadTP");
        return json;
    }

    /*详情页面*/
    public Object custHeadTPOneQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = "";
        String custid = request.getParameter("id");
        if(custid!=null&&!custid.equals("")){
            strSql = " and id = '"+custid+"'";
        }
        System.out.println("---------------------------"+strSql);
        /*基础信息查询表*/
        String dataTable = setCustHeadTPBasisSql();
        /*查询单个数据语句*/
        String sql = setOneSql(dataTable, strSql);
        System.out.println("获取数据语句：" + sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, getCustHeadTPList(), "custHeadTP"));
        return json;
    }

    /*获取来电弹屏顶部数据页查询的导出Excel语句*/
    public String custHeadTPExcel(String str) {
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(setCustHeadTPBasisSql(), str, "id");
        /*分页数据中下拉框信息的联表查询*/
        setSqlSelect("a", getCustHeadTPList(), new String[]{}, "px_CRM_CustHeadTP");
        /*设置含有下拉框信息的查询语句*/
        return "select "+sqlHeader+" from ("+sql+") as a "+sqlJoin;
    }

    /*来电弹屏顶部数据删除语句*/
    public boolean custHeadTPDel(HttpServletRequest request){
        String id = request.getParameter("id");
        if(id==null||Objects.equals(id, "")){
            return  false;
        }
        String sql1 = "delete from px_CRM_CustHead where id="+id;
        System.out.println("删除来电弹屏顶部数据："+sql1);
        return updataSql(sql1);
    }

    /*来电弹屏顶部数据更新语句*/
    public boolean updateCustHeadTP(HttpServletRequest request){
        String id = request.getParameter("id");
        if(id==null||Objects.equals(id, "")){
            return  false;
        }
        ArrayList<String> setList = stringlistToArray(getCustHeadTPList());
        /*不更新id*/
        setList.remove("id");
        String sql = "update px_CRM_CustHead "+setOneUpdate(request,setList)+" where id="+id;
        System.out.println("更新语句："+sql);
        return updataSql(sql);
    }

    /*来电弹屏顶部数据添加语句*/
    public boolean insertCustHeadTP(HttpServletRequest request){
        ArrayList<String> setList = stringlistToArray(getCustHeadTPList());
        /*不添加id*/
        setList.remove("id");

        String insertList = setOneInsertNoadd(request, arrayToStringlist(setList));
        String sql = "INSERT into px_CRM_CustHead "+insertList;
        System.out.println("添加语句："+sql);
        return updataSql(sql);
    }
}
