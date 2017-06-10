package Wxzt.servlet.Common.table;

import Wxzt.servlet.Common.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2016-7-8.
 */
public class tableHeadSql extends Query {
    /*查询条件，必填写*/
    public String setStrSql(HttpServletRequest request){
        String strSQL = "";
        /*工号和table名称*/
        String worknum = request.getParameter("worknum");
        String tableName = request.getParameter("tableName");
        if(worknum!=null&&!worknum.equals("")&&tableName!=null&&!tableName.equals("")){
            strSQL+=" and worknum='"+worknum+"' and tableName='"+tableName+"'";
        }
        return strSQL;
    }
    /*基础查询语句*/
    public String setBasisSql(){
        return " dddConfig_table ";
    }
    /*设置数据表头(除去下拉框)信息*/
    public String[] getList(){
        String[] otherList = {"headName","headMean","headWidth","headOrder","isCommon","dataDav","isShow"};
        return otherList;
    }

    /*设置下拉框表头信息*/
    public String[] getSelectList(){
        String[] selectList = {"headType"};
        return selectList;
    }

    /*设置下拉框信息的联表查询*/
    public String getSelectSql(String sql){
        setSqlSelect("a", getSelectList(), getList(), "px_CRM_tableHead");
        /*设置含有下拉框信息的查询语句*/
        return "select "+sqlHeader+" from ("+sql+") as a "+sqlJoin +" ORDER BY tableName,headOrder DESC";
    }

    /*获取数据*/
    public ArrayList<Object> tableHeadQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql1 = setStrSql(request);
        /*查询对应坐席是否有相应设置*/
        String sql1 = "select headHideList from dddConfig_tableOfWorker where 1=1 "+strSql1;
        System.out.println("坐席对应的table头部获取数据语句：" + sql1);
        String workHeadTypeList = sqlOneData(sql1);
        if(workHeadTypeList==null|| Objects.equals(workHeadTypeList, "null")||Objects.equals(workHeadTypeList, "")||Objects.equals(workHeadTypeList, "0")){
            workHeadTypeList = "0";
        }
        String[] typeList = workHeadTypeList.split(",");
        workHeadTypeList = "";
        for(int i = 0;i<typeList.length;i++){
            workHeadTypeList += "'"+typeList[i]+"'";
            if(i<typeList.length-1){
                workHeadTypeList += ",";
            }
        }
        String tableName = request.getParameter("tableName");
        String strSql = "";
        if(tableName!=null){
            strSql = " where tableName='"+tableName+"'";
        }
        String sql = "select headMean,headName,headWidth,headType,headOrder,isCommon,dataDav,\n" +
                "(case when \n" +
                "headMean in ("+workHeadTypeList+") \n" +
                "then isHide else isShow end ) as isShow from dddConfig_table "+strSql+" order by headOrder";
        System.out.println("table头部获取数据语句：" + sql);
        /*获取数据并返回相关信息*/
        return sqlListData(sql, addList(getList(), getSelectList()), "tableHead");
    }

    /*更新表头信息*/
    public boolean updateTableHead(HttpServletRequest request){
        String worknum = request.getParameter("worknum");
        String tableName = request.getParameter("tableName");
        String hiddenList = request.getParameter("hiddenList");
        /*查找是否客户相关数据存在*/
        String sql1 = " select count(*) from dddConfig_tableOfWorker where 1=1 "+setStrSql(request);
        int num = Integer.parseInt(sqlOneData(sql1));
        String sql = "";
        if(num>0){/*存在，执行更新*/
            sql = "update dddConfig_tableOfWorker set headHideList='"+request.getParameter("hiddenList")+"' where 1=1 "+setStrSql(request);
        }else {/*执行添加*/
            sql = "insert into dddConfig_tableOfWorker (worknum,tableName,headHideList) values ('"+worknum+"','"+tableName+"','"+hiddenList+"')";
        }
        System.out.println("table头部更新数据语句：" + sql);
        return updataSql(sql);
    }

    /*更新数据*/
    public String getUpdateSql(int inShow,String worknum,String tableName,String showList){
        String in = inShow==1?"in":"not in";
        return "update dddConfig_table set isShow = '"+inShow+"' where worknum='"+worknum+"' and tableName='"+tableName+"' " +
                "and headMean "+in+" ("+showList+")";
    }
}
