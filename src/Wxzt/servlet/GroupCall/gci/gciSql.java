package Wxzt.servlet.GroupCall.gci;

import Wxzt.servlet.Common.QueryForCti;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by Administrator on 2016-10-17.
 */
public class gciSql extends QueryForCti {
    /*群呼数据是否拨打*/
    public String isCall(String isCall,String str,String[] list){
        String headList = join(list, ",");
        String headListForEnd = headList.replace("telid", "id as telid").replace("taskid", "CalloutTaskID as taskid")
                .replace("custName", "'' as custName").replace("dtCall", "dtCreate as dtCall");
        String strForEnd = str.replace("taskid =","CalloutTaskID =");
        String sql_wait = "select "+headList+" from dddCalloutTelWait where 1=1 "+str;
        String sql_end = "select "+headListForEnd+" from dddCdr where 1=1 "+strForEnd;
        String sql = "";
        if(Objects.equals(isCall, "call")){
            sql = sql_end;
        }else {
            sql = sql_wait;
        }
        return sql;
    }

    /*管理页面的查询条件，必填写*/
    public String setGciStrSql(HttpServletRequest request){
        String str = " and taskid ="+request.getParameter("taskid");
        /*文本框*/
        String Conditions = request.getParameter("Conditions");/*文本框内容*/
        if(!Objects.equals(Conditions,null)&&!Objects.equals(Conditions, "")){
            str += " and (custName = '"+Conditions+"' or telnum = '"+Conditions+"')";
        }
        return str;
    }

    /*设置分页查询基础查询语句*/
    public String getBasisSql(){
        return " dddCalloutTask ";
    }

    /*设置客户管理分页数据表头(除去下拉框)信息*/
    public String[] getGciList(){
        String[] otherList = {"telid", "taskid", "telnum", "custName", "dtCall","callRet"};
        return otherList;
    }

    /*设置客户管理分页数据下拉框表头信息*/
    public String[] getGciSelectList(){
        String[] otherList = {};
        return otherList;
    }

    /*设置客户录音数据中下拉框信息的联表查询*/
    public String getGciSelectSql(String sql){
        setSqlSelect("a",getGciSelectList(),getGciList(),"px_CRM_Gci");
        /*除非另外还指定了 TOP 或 FOR XML，否则，ORDER BY 子句在视图、内联函数、派生表、子查询和公用表表达式中无效。*/
        return "select "+sqlHeader+" from ("+sql+") as a "+sqlJoin;
    }

    /*分页查询*/
    public Object gciQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setGciStrSql(request));
        String[] list = getGciList();
        /*基础信息查询表*/
        String isCall = request.getParameter("buttonType");
        String dataTable = isCall(isCall, strSql, list);
        /*初始化页面*/
        init(request," ("+dataTable+") as a ","");
        /*分页数据中下拉框信息的联表查询*/
        dataTable = setSql(" ("+dataTable+") as a ", strSql, "telid", "telid");
        System.out.println("查询群呼任务电话情况语句："+dataTable);
        /*获取客户数据并返回相关信息*/
        gciQueryJson json = new gciQueryJson();
        json.setDataList(sqlListData(dataTable, list, "groupCallImport"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        json.setCountType(isCall);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("groupCallImport"+"ToExcel",gciExcel(dataTable));
        json.setSqlTable("groupCallImport");
        return json;
    }

    /*获取查询的导出Excel语句*/
    public String gciExcel(String str){
        /*分页查询中导出excel语句*/
        return setExcelSql("("+str+") as a", "", "telid");
    }

//    /*删除任务情况电话*/
//    public boolean daleteGci(HttpServletRequest request){
//        String id = request.getParameter("id");
//        String sql = "delete from "+getBasisSql()+" where telid = "+id;
//        System.out.println("删除任务情况电话语句："+sql);
//        return updataSql(sql);
//    }
}
