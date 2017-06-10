package Wxzt.servlet.Reminder;
import Wxzt.servlet.Cdr.cdrBean;
import Wxzt.servlet.Common.Query;
import Wxzt.servlet.Common.table.getCustOrOrderHead;
import Wxzt.servlet.bean.Common.queryJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class ReminderSql extends Query{
    /*客户管理页面的查询条件，必填写*/
    public String setReminderStrSql(HttpServletRequest request){
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
    /*设置客户管理分页查询基础查询语句*/
    public String setReminderBasisSql(){
        return "  (select concat(id,'-',type) as id,type,worknum,title,isRead,openURL,createTime,readTime,groupid from px_crm_reminder) as a  ";
    }
    /*设置客户管理分页数据下拉框表头信息*/
    public String[] getReminderSelectList(){
        getCustOrOrderHead get = new getCustOrOrderHead();
        String[] selectList = new String[0];
        selectList = new String[]{};
        return selectList;
    }
    /*设置客户管理分页数据表头(除去下拉框)信息*/
    public String[] getReminderList(){
        getCustOrOrderHead get = new getCustOrOrderHead();
        String[] otherList = new String[0];
        otherList = new String[]{"id","worknum","title","isRead","openURL","createTime","readTime","groupid","type"};
        return otherList;
    }
    public boolean insertReminder( ReminderBean reminderBean){//添加消息提醒
         boolean insert = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(new Date());
            String sql = "insert into px_CRM_reminder ( worknum,title,isRead,openURL,createTime,groupid,type ) values ('"+reminderBean.getWorknum()+"','"+reminderBean.getTitle()+"','"+reminderBean.getIsRead()+"','"+reminderBean.getOpenURL()+"','"+createTime+"','"+reminderBean.getGroupid()+"','"+reminderBean.getType()+"')";
            System.out.println("添加消息提醒语句：" + sql);
            insert = updataSql(sql);
        return insert;
    }
    public boolean updateReminder(HttpServletRequest request){//更新消息提醒
            String id = request.getParameter("id");
            String isRead = request.getParameter("isRead");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String readTime = format.format(new Date());
            String sql = "";
            if(id!=null&&id!=""){
                sql = "update px_crm_reminder set readTime ='"+readTime+"',isRead="+isRead+" where id="+id;
                System.out.println("修改语句:" + sql);
                return updataSql(sql);
            }
            return false;
    }
    public Object reminderQuery(HttpServletRequest request){//页面数据查询
       /*获取查询条件*/
        String strSql = setStrSql(request, setReminderStrSql(request));
         /*基础信息查询表*/
        String dataTable = setReminderBasisSql();
        /*初始化页面*/
        init(request,dataTable,strSql);
        /*查询分页数据语句*/
        String sql = setSql(dataTable, strSql, "id", "createTime");
        /*获取客户数据并返回相关信息*/
        queryJson json = new queryJson();
        json.setDataList(sqlListData(sql, addList(getReminderList(), getReminderSelectList()), "QueryReminder"));
        json.setCount(count);
        json.setCountPage(countPage);
        json.setCurrentPage(currentPage);
        return json;
    }
}
