package Wxzt.servlet.background;

import Wxzt.servlet.bean.QueryTableJson;
import Wxzt.tool.JDBC;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryColleagues extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();

        int count = 0;//数据总数
        int countPage;//总页面数
        int currentPage;//当前页数
        int currentNum = 50;//页面显示最大数据

        String conditions;//输入条件
        String strSQL="";//查询条件
        String sql ;//查询语句
        String DataTable = "(select a.*,b.groupname,c.jsmc FROM dddWork AS a LEFT OUTER JOIN\n" +
                " dddGroup AS b ON a.Groupid = b.Groupid LEFT OUTER JOIN\n" +
                " px_CRM_JSQX AS c ON a.roleid = c.JSBH) as m";//数据表
        ResultSet osCount;//记录总数据数量的ResultSet
        ResultSet os;//执行SQL的ResultSet

        /*文本框输入信息*/
        conditions = request.getParameter("Conditions");
        System.out.println("conditions="+conditions);
        if(!conditions.equals("")){
            System.out.println("查询条件不为空");
            strSQL += " and workname = '"+conditions+"' or worknum = '"+conditions+"'";
        }
        /*工作组编号*/
        String group = request.getParameter("groupName");
        if(!group.equals("all")){
            strSQL += " and Groupid = '"+group+"' ";
        }
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
        if(currentPage<1){
            currentPage = 1;
        }
        int count1 = currentNum*(currentPage-1);

        sql = "SELECT  * FROM " +DataTable+
                " WHERE 1=1  "+strSQL+" ORDER BY workid DESC limit "+count1+","+currentNum;
        System.out.println("companySQL="+sql);

        JDBC jdbc = new JDBC();

        //查询表单数据数量
        String countSQL = "select count(*) from "+DataTable+" where 1=1"+strSQL;
        try {
            osCount = jdbc.executeQuery(countSQL);
            if (osCount.next()) {
                count = osCount.getInt(1);//表单数据数量
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        //获取总页数countPage
        if (count%currentNum==0) {
            countPage=count/currentNum;
        }else {
            countPage=count/currentNum+1;
        }
        //获取数据
        QueryTableJson jsonData = new QueryTableJson();
        if(count>0){
            try {
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                    while (os.next()) {
                        ArrayList data = new ArrayList();
                        data.add(os.getString("workid"));//ID0
                        data.add(os.getString("workname"));//用户姓名1
                        data.add(os.getString("worknum"));//工号2
                        data.add(os.getString("sipnum"));//默认分机3
                        data.add(os.getString("Groupid"));//部门编号4
                        data.add(os.getString("roleid"));//角色权限5
                        data.add(os.getString("Mobile1"));//手机号1-6
                        data.add(os.getString("Mobile2"));//手机号2-7
                        data.add(os.getString("PassNumber"));//透传号码-8
                        data.add(os.getString("TimeLenAutoRecovery"));//自动恢复时长-9
                        data.add(os.getString("IsWatch"));//是否监控10
                        data.add(os.getString("note"));//备注11
                        data.add(os.getString("GroupName"));//组名12
                        data.add(os.getString("JSMC"));//角色名称13
                        data.add(os.getString("email"));//email14
                        listData.add(data);
                    }
                    jsonData.setDataList(listData);
                    jsonData.setCount(count);
                    jsonData.setCountPage(countPage);
                    jsonData.setCurrentPage(currentPage);
                    jsonData.setTableName(DataTable);
                    jsonData.setDataType("Y");
                    System.out.println("ok");
                } catch (SQLException e) {
                    System.out.println("+++++++++++++++++");
                    e.printStackTrace();
                }
                jdbc.closeConnection();
//                out.println(outAjax);//返回html信息
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No one");
            jsonData.setDataType("N");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);
    }
}

