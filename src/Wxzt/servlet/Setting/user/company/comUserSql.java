package Wxzt.servlet.Setting.user.company;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016-7-7.
 */
public class
comUserSql extends Query {
    /*查询条件，必填写*/
    public String setStrSql(HttpServletRequest request){
        String strSQL = "";
         /*文本框输入信息*/
        String conditions = request.getParameter("Conditions");
        if(!conditions.equals("")){
            System.out.println("查询条件不为空");
            strSQL += "and (workname = '"+conditions+"' or worknum = '"+conditions+"')";
        }
        /*工作组编号*/
        String group = request.getParameter("groupName");
        if(!group.equals("all")){
            strSQL += " and Groupid = '"+group+"' ";
        }
        return strSQL;
    }
    /*设置客户录音数据表头(除去下拉框)信息*/
    public String[] getList(){
        String[] otherList = {"workid","workname","worknum","sipnum","isWatch","note","mobile1","mobile2","passNumber","timeLenAutoRecovery"};
        return otherList;
    }

    /*设置客户录音数据工作组表头信息*/
    public String[] getGroupList(){
        String[] selectList = {"groupid"};
        return selectList;
    }
    /*设置客户录音数据工作组表头信息*/
    public String[] getRoleList(){
        String[] selectList = {"roleid"};
        return selectList;
    }
    /*设置基础查询语句*/
    public String setBasisSql(){
        return " dddWork ";
    }

    /*设置数据中下拉框信息的联表查询*/
    public String getSelectSql(String sql){
        String[] list = {};
        setSqlSelect_R("a",list,getList(),list,getGroupList(),getRoleList(),"px_CRM_Cdr");
        /*除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询和公用表表达式中无效。*/
        return "select "+sqlHeader+" from ("+sql+") as a "+sqlJoin;
    }

    /*客户管理分页查询*/
    public Object comUserQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setStrSql(request));
        /*基础信息查询表*/
        String dataTable = setBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "workid", "workid");
        /*分页数据中下拉框信息的联表查询*/
        sql = getSelectSql(sql);
        System.out.println("查询坐席信息语句："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list1 = addList(getList(),getGroupList());
        json.setDataList(sqlListData(sql, addList(list1, getRoleList()), "comUser"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("comUser"+"ToExcel",comUserExcel(strSql));
        json.setSqlTable("comUser");
        return json;
    }

    /*打开录音详情页面时获取客户数据*/
    public Object comUserOneQuery(HttpServletRequest request,boolean isSelect){
        /*获取查询条件*/
        String strSql = "";
        /*客户管理页面双击打开客户资料页面*/
        String workid = request.getParameter("workid");
        if(workid!=null&&!workid.equals("")){
            strSql = " and workid = '"+workid+"'";
        }
        /*基础信息查询表*/
        String dataTable = setBasisSql();
        /*查询单个数据语句*/
        String sql = setOneSql(dataTable, strSql);
        /*分页数据中下拉框信息的联表查询*/
        sql = isSelect?getSelectSql(sql):sql;
        System.out.println("坐席查询获取数据语句：" + sql);
        /*获取录音数据并返回相关信息*/
        queryJson json = new queryJson();
        String[] list1 = addList(getList(), getGroupList());
        json.setDataList(sqlListData(sql, addList(list1, getRoleList()), "comUser"));
        return json;
    }

    /*获取客户管理分页查询的导出Excel语句*/
    public String comUserExcel(String str){
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(setBasisSql(), str, "workid");
        /*分页数据中下拉框信息的联表查询*/
        sql = getSelectSql(sql);
        return sql;
    }

    /*更新语句*/
    public boolean updateComUser(HttpServletRequest request){
        ArrayList<String> setList = stringlistToArray(getList());
        /*不更新workid*/
        setList.remove("workid");
        String sql = "update dddWork "+setOneUpdate(request,setList)+" where workid='"+request.getParameter("workid")+"'";
        System.out.println("更新坐席信息语句："+sql);
        return updataSql(sql);
    }
}
