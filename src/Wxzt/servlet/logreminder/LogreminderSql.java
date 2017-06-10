package Wxzt.servlet.logreminder;

import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Common.table.getCustOrOrderHead;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class LogreminderSql  extends Query{
    /*客户管理页面的查询条件，必填写*/
    public String setLogreminderStrSql(HttpServletRequest request){
        String strSQL = "";
        /*文本输入框*/
        String line1 = request.getParameter("line1");
        if (line1 != null && !line1.equals("")) {
            strSQL += "and line1 = '" + line1 + "'";
        }
        String line2 = request.getParameter("line2");
        if (line2 != null && !line2.equals("")) {
            strSQL += "and line2 = '" + line2 + "'";
        }
        String line3 = request.getParameter("line3");
        if (line3 != null && !line3.equals("")) {
            strSQL += "and line3 = '" + line3 + "'";
        }
        String line4 = request.getParameter("line4");
        if (line4 != null && !line4.equals("")) {
            strSQL += "and line4 = '" + line4 + "'";
        }
        String worknum = request.getParameter("worknum");
        if (worknum != null && !worknum.equals("")) {
            strSQL += "and worknum = '" + worknum + "'";
        }
        String data = request.getParameter("data");
        if (data != null && !data.equals("")) {
            strSQL += "and data = '" + data + "'";
        }
        String updateTime = request.getParameter("updateTime");
        if (updateTime != null && !updateTime.equals("")) {
            strSQL += "and updateTime = '" + updateTime + "'";
        }
        String custnum = request.getParameter("custnum");
        if (custnum != null && !custnum.equals("")) {
            strSQL += "and custnum = '" + custnum + "'";
        }
        String tel = request.getParameter("tel");
        if (tel != null && !tel.equals("")) {
            strSQL += "and tel like '%" + tel + "%'";
        }

        return strSQL;
    }
    /*设置客户管理分页查询基础查询语句*/
    public String setLogreminderBasisSql(){
        return " px_CRM_logreminder ";
    }
    public boolean logreminderDel(HttpServletRequest request){//删除日志提醒
        String id = request.getParameter("id");
        /*删除动态数据*/
        String sql = "delete from px_CRM_logreminder where id="+id;
        System.out.println("删除日志提醒数据："+sql);
        boolean b = updataSql(sql);
        return b;
    }
    /*添加日志提醒*/
    public boolean insertlogreminder(HttpServletRequest request){
        boolean insert = false;
        String worknum = request.getParameter("worknum");
        String line1 = request.getParameter("line1");
        String line2 = request.getParameter("line2");
        String line3 = request.getParameter("line3");
        String line4 = request.getParameter("line4");
        String custnum = request.getParameter("custnum");
        String tel = request.getParameter("tel");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(new Date());
        String sql = "insert into px_CRM_logreminder (worknum,line1,line2,line3,line4,createTime,custnum,tel) values ('"+worknum+"','"+line1+"','"+line2+"','"+line3+"','"+line4+"','"+createTime+"','"+custnum+"','"+tel+"')";
        System.out.println("添加。。。。：："+sql);
        return updataSql(sql);
    }   //修改
    public boolean updatelogreminder(HttpServletRequest request) {
        boolean insert = false;
        String id = request.getParameter("id");
        String worknum = request.getParameter("worknum");
        String line1 = request.getParameter("line1");
        String line2 = request.getParameter("line2");
        String line3 = request.getParameter("line3");
        String line4 = request.getParameter("line4");
        String custnum = request.getParameter("custnum");
        String tel = request.getParameter("tel");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime = format.format(new Date());
        String sql = "";
        if(id!=null&&id!=""){
            sql = "update px_CRM_logreminder set worknum='"+worknum+"',line1='"+line1+"',line2='"+line2+"',line3='"+line3+"',line4='"+line4+"',updateTime='"+updateTime+"',custnum='"+custnum+"',tel='"+tel+"'where id="+id;
            System.out.println("修改语句:" + sql);
            return updataSql(sql);
        }
        return false;
    }
    public Object logreminderQuery(HttpServletRequest request){
         /*获取查询条件*/
        String strSql = setStrSql(request, setLogreminderStrSql(request));
         /*基础信息查询表*/
        String dataTable = setLogreminderBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "updateTime");
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getLogreminderList(), getLogreminderSelectList()), "QueryLogreminder"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        return json;
    }
    /*设置客户管理分页数据表头(除去下拉框)信息*/
    public String[] getLogreminderList(){
        String[] otherList = new String[0];
        otherList = new String[]{"id","worknum","line1","line2","line3","line4","createTime","updateTime","custnum","tel"};
        return otherList;
    }
    /*设置客户管理分页数据下拉框表头信息*/
    public String[] getLogreminderSelectList(){
        String[] selectList = new String[0];
        selectList = new String[]{};
        return selectList;
    }

}
