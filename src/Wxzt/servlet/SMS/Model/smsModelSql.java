package Wxzt.servlet.SMS.Model;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016-9-27.
 */
public class smsModelSql extends Query {
    /*短信模板页面的查询条件，必填写*/
    public String setModelStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本输入框*/
//        String conditions = request.getParameter("Conditions");
//        if(conditions!=null&&!conditions.equals("")){
//            strSQL += "and (mobile = '"+conditions+"' or smsContent like '%"+conditions+"%')";
//        }
        /*权限判定*/
//        String IsMgr = request.getParameter("IsMgr");//权限设置:1为个人；2为组别；3为全部
//        if(IsMgr.equals("1")){//
//            String worknum = request.getParameter("worknum");//坐席工号
//            strSQL += " and worknum = '"+worknum+"'";
//        }else if(IsMgr.equals("2")){
//            String groupid = request.getParameter("groupid");//组别名称ID
//            strSQL += " and worknum in (select worknum from dddWork where groupid = '"+groupid+"')";
//        }
        return strSQL;
    }

    /*设置短信模板分页查询基础查询语句*/
    public String setModelBasisSql(){
        return " px_CRM_CompanySMSModel ";
    }

    /*设置短信模板分页数据表头(除去下拉框)信息*/
    public String[] getModelList(){
        String[] otherList = {"id","name","modelContent","note"};
        return otherList;
    }

    /*设置短信模板分页数据下拉框表头信息*/
    public String[] getModelSelectList(){
        String[] selectList = {"type"};
        return selectList;
    }

    /*设置短信模板分页数据中下拉框信息的联表查询*/
    public String getModelSelectSql(String sql){
        setSqlSelect("a", getModelSelectList(), getModelList(), "px_CRM_SmsModel");
        /*设置含有下拉框信息的查询语句*/
        return "select "+sqlHeader+" from ("+sql+") as a "+sqlJoin;
    }

    /*短信模板分页查询*/
    public Object modelQuery(HttpServletRequest request){
        /*获取查询条件*/
        String strSql = setStrSql(request, setModelStrSql(request));
        /*基础信息查询表*/
        String dataTable = setModelBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "id");
        /*分页数据中下拉框信息的联表查询*/
        sql = getModelSelectSql(sql);
        System.out.println("短信发件箱语句："+sql);
        /*获取短信模板数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getModelList(), getModelSelectList()), "smsModel"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        /*添加导出excel语句到相应的session*/
        HttpSession session = request.getSession();
        session.setAttribute("smsModel"+"ToExcel",modelExcel(strSql));
        json.setSqlTable("smsModel");
        return json;
    }

    /*获取短信模板分页查询的导出Excel语句*/
    public String modelExcel(String str){
        /*分页查询中导出excel语句*/
        String sql = setExcelSql(setModelBasisSql(), str, "id");
        /*分页数据中下拉框信息的联表查询*/
        sql = getModelSelectSql(sql);
        return sql;
    }

    /*添加短信模板*/
    public boolean insertModel(HttpServletRequest request){
        /*不添加id*/
        ArrayList<String> repairList = stringlistToArray(getModelList());
        repairList.remove("id");
        String[] list1 = addList(arrayToStringlist(repairList), getModelSelectList());
        String insertList = setOneInsertNoadd(request, list1);
        String sql = "INSERT  into px_CRM_CompanySMSModel "+insertList;
        System.out.println("短信模板添加语句："+sql);
        return updataSql(sql);
    }

    /*短信模板更新语句*/
    public boolean updataModel(HttpServletRequest request){
        /*不更新id*/
        ArrayList<String> repairList = stringlistToArray(getModelList());
        repairList.remove("id");
        String[] list1 = addList(arrayToStringlist(repairList), getModelSelectList());
        String sql = "update px_CRM_CompanySMSModel "+setOneUpdate(request,stringlistToArray(list1))+" where ID='"+request.getParameter("id")+"'";
        System.out.println("短信模板更新语句：" + sql);
        return updataSql(sql);
    }

    /*短信模板删除语句*/
    public boolean delModel(HttpServletRequest request){
        String id = request.getParameter("id");
        /*删除工单处理数据*/
        String sql = "delete from px_CRM_CompanySMSModel where id="+id;
        System.out.println("短信模板删除数据：" + sql);
        return updataSql(sql);
    }

    /*查询单条模板数据*/
    public Object modelOneQuery(HttpServletRequest request){
        queryJson json = new queryJson();
        /*客户管理页面双击打开客户资料页面*/
        String id = request.getParameter("id");
        if(id!=null&&!id.equals("")){
            String sql = "select * from px_CRM_CompanySMSModel where id = '"+id+"'";
            json.setDataList(sqlListData(sql, addList(getModelList(), getModelSelectList()), "smsModel"));
        }
        return json;
    }
}
