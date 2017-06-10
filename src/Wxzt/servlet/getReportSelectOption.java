package Wxzt.servlet;

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

public class getReportSelectOption extends HttpServlet {
        private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.print("tt--------------------");
        PrintWriter out = response.getWriter();

        String sql="";
        ResultSet os;//执行SQL的ResultSet
        //获取jQuery传递的参数
        String dataTable = request.getParameter("dataTable");//数据库表
        if(dataTable.equals("dddReport_Work")){
            sql = "select GroupName from dddReport_Work Group by GroupName";//查询语句
        }else if(dataTable.equals("dddGroup")){
            sql = "select b.GroupName from dddCdr as a left join dddGroup as b on a.Groupnum=b.Groupnum Group by b.GroupName";//查询语句
        }else if(dataTable.equals("group")){
            sql = "select Groupnum,GroupName from dddGroup ";//查询语句
        }else if(dataTable.equals("groupID")){
            sql = "select Groupid as Groupnum,GroupName from dddGroup ";//查询语句
        }else if(dataTable.equals("role")){
            sql = "select JSBH as Groupnum,JSMC as GroupName from px_CRM_JSQX ";//查询语句
        }
        JDBC jdbc = new JDBC();
        QueryTableJson jsonData = new QueryTableJson();
        try {
            // 执行查询
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(os.getString("GroupName"));//0
                    if(dataTable.equals("group")||dataTable.equals("role")||dataTable.equals("groupID")){
                        data.add(os.getString("Groupnum"));//0
                    }
                    listData.add(data);
                }
                System.out.println("创建数据完成");
                jsonData.setDataList(listData);
                jsonData.setDataType("Y");
            } catch (SQLException e) {
                System.out.println("+++++++++++++++++");
                e.printStackTrace();
            }
            jdbc.closeConnection();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);
    }
}
