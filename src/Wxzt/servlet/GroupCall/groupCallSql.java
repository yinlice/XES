package Wxzt.servlet.GroupCall;

import Wxzt.servlet.Common.QueryForCti;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2016-10-11.
 */
public class groupCallSql extends QueryForCti {
    /*管理页面的查询条件，必填写*/
    public String setGroupCallStrSql(HttpServletRequest request) {
        String strSQL = "";
        /*结束码*/
        String CustType = request.getParameter("custType");
        if (CustType != null && !CustType.equals("all")) {
            strSQL += " and CustType='" + CustType + "'";
        }
        /*文本输入框*/
        String conditions = request.getParameter("Conditions");
        if (conditions != null && !conditions.equals("")) {
            strSQL += "and TaskName = '" + conditions + "'";
        }
        return strSQL;
    }

    /*获取左侧的分类，查询条件*/
    public String setStrSqlType(HttpServletRequest request, String strSQL) {
        /*cti的项目划分*/
        strSQL += " and companyCode='" + getCompanyCode() + "'";
        /*任务状态划分*/
        String tableFormType = request.getParameter("tableFormType");
        if (tableFormType == null || Objects.equals(tableFormType, "") || Objects.equals(tableFormType, "status1")) {/*执行任务*/
            return strSQL += " and status = '1'";
        } else if (Objects.equals(tableFormType, "status0")) {/*新建任务*/
            return strSQL += " and status = '0'";
        } else if (Objects.equals(tableFormType, "status2")) {/*暂停任务*/
            return strSQL += " and status = '2'";
        } else if (Objects.equals(tableFormType, "status3")) {/*完成任务*/
            return strSQL += " and status = '3'";
        }
        return strSQL;
    }

    /*设置分页查询基础查询语句*/
    public String getBasisSql() {
        return " dddCalloutTask ";
    }

    /*设置客户管理分页数据表头(除去下拉框)信息*/
    public String[] getGroupCallList() {
        String[] otherList = {"worknumCreate", "taskid", "taskName", "telCount", "groupName", "luaName", "groupid", "groupNum", "outShowTel", "callRate", "dtCreate", "remart"};
        return otherList;
    }

    /*设置客户管理分页数据下拉框表头信息*/
    public String[] getGroupCallSelectList() {
        String[] otherList = {"status", "taskType"};
        return otherList;
    }

    /*设置客户录音数据中下拉框信息的联表查询*/
    public String getGroupCallSelectSql(String sql) {
        setSqlSelect("a", getGroupCallSelectList(), getGroupCallList(), "px_CRM_GroupCall");
        /*除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询和公用表表达式中无效。*/
        return "select " + sqlHeader + " from (" + sql + ") as a " + sqlJoin;
    }

    /*分页查询*/
    public Object groupCallQuery(HttpServletRequest request) {
        /*获取查询条件*/
        String strSql = setStrSql(request, setGroupCallStrSql(request));
        strSql = setStrSqlType(request, strSql);
        /*基础信息查询表*/
        String dataTable = getBasisSql();
        /*初始化页面*/
        init(request, dataTable, strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "taskid", "dtCreate");
        /*分页数据中下拉框信息的联表查询*/
        sql = getGroupCallSelectSql(sql);
        System.out.println("查询群呼任务语句：" + sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getGroupCallList(), getGroupCallSelectList()), "groupCall"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("groupCall" + "ToExcel", groupCallExcel(strSql));
        json.setSqlTable("groupCall");
        return json;
    }
    /*编辑群呼任务页面*/
    public Object groupCallOneQuery(HttpServletRequest request) {
        /*获取查询条件*/
        String sql = " select * from " + getBasisSql() + " where taskid= " + request.getParameter("id");
        System.out.println("编辑群呼任务页面：" + sql);
        /*获取任务数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getGroupCallList(), getGroupCallSelectList()), "groupCall"));
        return json;
    }

    /*获取查询的导出Excel语句*/
    public String groupCallExcel(String str) {
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(getBasisSql(), str, "dtCreate");
        /*分页数据中下拉框信息的联表查询*/
        sql = getGroupCallSelectSql(sql);
        return sql;
    }

    /*更新任务*/
    public boolean updataGroupStatus(HttpServletRequest request) {
        String status = request.getParameter("type");
        String taskid = request.getParameter("taskid");
        String sql = "";
        if (status != null && taskid != null) {
            sql = "update " + getBasisSql() + " set status=" + status + " where taskid=" + taskid;
            System.out.println("更新群呼任务语句：" + sql);
            return updataSql(sql);
        }
        return false;
    }

    /*添加任务*/
    public boolean insertGroupCall(HttpServletRequest request) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        String[] setList = addList(getGroupCallSelectList(), getGroupCallList());
        /*删除custid*/
        ArrayList<String> list = stringlistToArray(setList);
        list.remove("taskid");
        setList = arrayToStringlist(list);
        /*添加需要后台生成的字段*/
        String[] addName = {"dtCreate", "companyCode"};
        String[] addValue = {newTime, getCompanyCode()};
        String insertList = setOneInsert(request, setList, addName, addValue);
        String sql = "INSERT  into " + getBasisSql() + " " + insertList;
        System.out.println("添加任务语句：" + sql);
        return updataSql(sql);
    }

    /*删除任务*/
    public boolean daleteGroupCall(HttpServletRequest request) {
        String id = request.getParameter("id");
        String sql = "delete from " + getBasisSql() + " where taskid = " + id;
        System.out.println("删除任务语句：" + sql);
        return updataSql(sql);
    }

    /*任务更新语句*/
    public boolean updateGroupCall(HttpServletRequest request) {
        ArrayList<String> setList = addListArray(getGroupCallSelectList(), getGroupCallList());
        /*不更新id和custNum*/
        setList.remove("taskid");
        setList.remove("worknumCreate");
        String id = request.getParameter("id");
        String sql = "update " + getBasisSql() + " " + setOneUpdate(request, setList) + " where taskid =" + id;
        System.out.println("更新任务语句：" + sql);
        return updataSql(sql);
    }
}
