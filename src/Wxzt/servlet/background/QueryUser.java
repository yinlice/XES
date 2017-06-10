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

/**
* Created by Pengxi on 2015/8/31.
*/
public class QueryUser extends HttpServlet {
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
        String strSQL;//查询条件
        String sql ;//查询语句
        ResultSet osCount;//记录总数据数量的ResultSet
        ResultSet os;//执行SQL的ResultSet

        //获取jQuery传递的参数
        conditions = request.getParameter("conditions");
        System.out.println("conditions="+conditions);
        if(conditions.equals("")){
            System.out.println("查询条件为空");
            strSQL = "";
        }else {
            System.out.println("查询条件不为空");
            strSQL = "and (UserName like '%"+conditions+"%' or WorkName like '%"+conditions+"%')";
        }
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
        if(currentPage<1){
            currentPage = 1;
        }
        int count1 = currentNum*(currentPage-1);

        sql = "SELECT  * FROM PX_CRM_User " +
                "WHERE 1=1 "+strSQL+" ORDER BY ID DESC limit "+count1+","+currentNum;
        System.out.println("companySQL="+sql);

        JDBC jdbc = new JDBC();

        //查询表单数据数量
        String countSQL = "select count(*) from PX_CRM_User where 1=1"+strSQL;
        try {
            osCount = jdbc.executeQuery(countSQL);
            if (osCount.next()) {
                count = osCount.getInt(1);//表单数据数量
                System.out.println("count========"+count);
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
        System.out.println("ok");
        if(count>0){
            try {
                // 执行查询
//                System.out.println(SQLstate);
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<ArrayList> listData = new ArrayList<ArrayList>();

                    int i = 1;
                    while (os.next()) {
                        ArrayList data = new ArrayList();
                        data.add(i + "");
                        data.add(os.getString("ID"));//ID
                        data.add(os.getString("UserName"));//用户姓名
                        data.add(os.getString("WorkName"));//工号
                        data.add(os.getString("RoleID"));//角色ID
                        data.add(os.getString("DepartmentID"));//部门ID
                        data.add(os.getString("IsManager"));//是否经理
                        data.add(os.getString("Queue"));//队列号
                        listData.add(data);
                        i++;
                    }
                    jsonData.setDataList(listData);
                    jsonData.setCount(count);
                    jsonData.setCountPage(countPage);
                    jsonData.setCurrentPage(currentPage);
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
