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
 * Created by Administrator on 2015/10/9.
 */
public class QueryCustomer extends HttpServlet {
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
        String DataTable = "px_CRM_ServiceStation";//数据表
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
            strSQL = "and (UserName like '%"+conditions+"%' or TelNumber like '%"+conditions+"%')";
        }
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
        if(currentPage<1){
            currentPage = 1;
        }
        int currentCount = currentNum * (currentPage-1);//已显示的数据
        sql = "SELECT * FROM " +DataTable+
                " WHERE 1=1 "+strSQL+" ORDER BY nID DESC limit "+currentCount+","+currentNum;
//        SQLstate = "select * from "+DataTable+" where 1=1 "+strSQL+" ORDER BY  nID DESC limit "+currentCount+","+currentNum;
        System.out.println("companySQL="+sql);

        JDBC jdbc = new JDBC();

        //查询表单数据数量
        String countSQL = "select count(*) from "+DataTable+" where 1=1"+strSQL;
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
                    while (os.next()) {
                        ArrayList data = new ArrayList();
                        data.add(os.getString("nID"));//ID0
                        data.add(os.getString("UserName"));//用户姓名1
                        data.add(os.getString("UserSex"));//性别2
                        data.add(os.getString("StationName"));//公司3
                        data.add(os.getString("TelNumber"));//电话4
                        data.add(os.getString("Email"));//EMail5
                        data.add(os.getString("MSN"));//MSN6
                        data.add(os.getString("QQ"));//QQ7
                        data.add(os.getString("Address"));//地址8
                        data.add(os.getString("AddTime"));//日趋9
                        listData.add(data);
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


