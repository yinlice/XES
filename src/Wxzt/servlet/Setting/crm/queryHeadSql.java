package Wxzt.servlet.Setting.crm;

import Wxzt.servlet.Common.table.tableHeadSql;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2016-7-15.
 */
public class queryHeadSql extends tableHeadSql {
    /*查询条件，必填写*/
    public String setStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本输入框*/
        String conditions = request.getParameter("Conditions");
        if(conditions!=null&&!conditions.equals("")){
            strSQL += "and (tableName like '%"+conditions+"%' or headName like '%"+conditions+"%' or headMean like '%"+conditions+"%')";
        }
        /*按钮查询，会覆盖之前设置的查询条件*/
        String QueryName = request.getParameter("buttonType");
        if (QueryName==null||QueryName.equals("all")) {/*全选*/
            strSQL += " ";
        } else {
            strSQL += " and tableName = '"+QueryName+"'";
        }
        /*id*/
        String id = request.getParameter("id");
        if(id!=null&&!id.equals("")){
            strSQL += " and id = "+id;
        }
        return strSQL;
    }

    /*获取页面分类查询条件*/
    /*获取录音在CTI中分类，查询条件*/
    public String setStrSqlType(HttpServletRequest request,String StrSqlOther){
        String strSQL = setStrSql(request, StrSqlOther);
        String tableFormType = request.getParameter("tableFormType");
        if(tableFormType==null|| Objects.equals(tableFormType, "")|| Objects.equals(tableFormType, "all")){
            return strSQL;
        }else {
            strSQL += " and tableName='"+tableFormType+"'";
        }
        System.out.println("查询条件："+strSQL);
        return strSQL;
    }

    /*设置据表头(除去下拉框)信息*/
    public String[] getList(){
        String[] otherList = {"id","tableName","headMean","headName","headWidth","headOrder","dataDav"};
        return otherList;
    }

    /*设置数据下拉框表头信息*/
    public String[] getSelectList(){
        String[] selectList = {"headType","isCommon"};
        return selectList;
    }

    /*客户管理分页查询*/
    public Object tableHeadQuery_q(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSqlType(request, setStrSql(request));
        /*基础信息查询表*/
        String dataTable = setBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "tableName,headOrder");
        /*分页数据中下拉框信息的联表查询*/
        sql = getSelectSql(sql);
        System.out.println("表头信息查询语句："+sql);
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getList(), getSelectList()), "tableHead"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("tableHead"+"ToExcel",headExcel(strSql));
        json.setSqlTable("tableHead");
        return json;
    }

    /*获取分页查询的导出Excel语句*/
    public String headExcel(String str){
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(setBasisSql(), str, "id");
        return getSelectSql(sql);
    }

    /*打开编辑页面获取数据*/
    public Object headOneQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setStrSql(request));
        /*基础信息查询表*/
        String dataTable = setBasisSql();
        /*查询单个数据语句*/
        String sql = setOneSql(dataTable, strSql);
        /*分页数据中下拉框信息的联表查询*/
        System.out.println("编辑表头信息数据语句：" + sql);
        /*获取录音数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getList(), getSelectList()), "tableHead"));
        return json;
    }

    /*更新语句*/
    public boolean updateHead(HttpServletRequest request){
        ArrayList<String> setList = addListArray(getList(), getSelectList());
        /*不更新id*/
        setList.remove("id");
        String sql = "update "+setBasisSql()+" "+setOneUpdate(request,setList)+" where id='"+request.getParameter("id")+"'";
        System.out.println("更新语句：" + sql);
        return updataSql(sql);
    }

    /*删除语句*/
    public boolean delHead(HttpServletRequest request){
        String id = request.getParameter("id");
        String sql = "delete from "+setBasisSql()+" where id="+id;
        System.out.println("删除语句：" + sql);
        return updataSql(sql);
    }

    /*客户管理添加客户*/
    public boolean insertHead(HttpServletRequest request){
        ArrayList<String> list = addListArray(getList(), getSelectList());
        /*删除custid*/
        list.remove("id");
        String[] setList = arrayToStringlist(list);
        String insertList = setOneInsertNoadd(request, setList);
        String sql = "INSERT  into "+setBasisSql()+" "+insertList;
        System.out.println("添加语句："+sql);
        return updataSql(sql);
    }
}
