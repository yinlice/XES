package Wxzt.servlet.Log;

import Wxzt.servlet.Common.Query;
import Wxzt.tool.JDBC;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yin on 2017/4/25.
 */
public class LogSql extends Query{
    //获取第一级节点
    public List<LogBean> getFirstNode(HttpServletRequest request){
        int groupid = Integer.parseInt(request.getParameter("groupid"));
        String sql = "select id,pid,content from px_crm_log where pid = 0 and groupid = "+groupid+" order by id ";
        List<LogBean> list = new ArrayList<>();
        try{
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            while (rs.next()){
                LogBean bean = new LogBean();
                String id = rs.getString("id");
                String pid = rs.getString("pid");
                String content = rs.getString("content");
                bean.setId(id);
                bean.setPid(pid);
                bean.setContent(content);
                list.add(bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public List<LogBean> getNodeByPid(HttpServletRequest request){
        List<LogBean> list = new ArrayList<>();
        int pid = Integer.parseInt(request.getParameter("pid"));
        String sql  =  "select id,pid,content from px_crm_log where pid = "+pid;
        try{
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            while(rs.next()){
                LogBean bean = new LogBean();
                String id = rs.getString("id");
                String pid2 = rs.getString("pid");
                String content = rs.getString("content");
                bean.setId(id);
                bean.setPid(pid2);
                bean.setContent(content);
                list.add(bean);
            }

        }catch (Exception e){
            e.printStackTrace();;
        }
        return list;
    }
    //添加第一级菜单
    public List addFirstMenu(HttpServletRequest request){
        List list = new ArrayList();
        int groupid = Integer.parseInt(request.getParameter("groupid"));
        String content = request.getParameter("content");
        String sql = "insert into px_crm_log (pid,content,groupid) values (0,'"+content+"',"+groupid+")";
        String sql2 = "select last_insert_id()";
        try{
            JDBC jdbc = new JDBC();
            jdbc.executeUpdate(sql);
            ResultSet rs = jdbc.executeQuery(sql2);
            int id = -1;
            while(rs.next()){
                id = rs.getInt(1);
            }
            list.add(id);
            jdbc.closeConnection();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //添加2,3,4级菜单
    public List addMenu(HttpServletRequest request){
        List list = new ArrayList();
        String content = request.getParameter("content");
        int pid = Integer.parseInt(request.getParameter("pid"));
        String sql = "insert into px_crm_log (pid,content) values ("+pid+",'"+content+"')";
        String sql2 = "select last_insert_id()";
        try{
            JDBC jdbc = new JDBC();
            jdbc.executeUpdate(sql);
            ResultSet rs = jdbc.executeQuery(sql2);
            int id = -1;
            while(rs.next()){
                id = rs.getInt(1);
            }
            list.add(id);
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //节点的编辑
    public boolean ModifyLog(HttpServletRequest request){
        String content = request.getParameter("content");
        int id = Integer.parseInt(request.getParameter("id"));
        String sql = "update px_crm_log set content = '"+content+"' where id = "+id;
        return updataSql(sql);
    }
    //节点的删除
    public boolean RemoveLog(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        String sql = "delete from  px_crm_log  where id = "+id;
        return updataSql(sql);
    }
}
