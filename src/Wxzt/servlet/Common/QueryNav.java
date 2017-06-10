package Wxzt.servlet.Common;

import Wxzt.servlet.bean.QueryJsonData;
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

public class QueryNav extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String sql;
        //传递的参数

        String nav = request.getParameter("nav");
        System.out.println("Nav*********************"+nav);
        if(nav.equals("all")){
            sql = "select * from px_CRM_Menu order by OrderID";
        }else {
            sql= "select * from px_CRM_Menu where MenuID in ("+nav+") order by OrderID";
        }
        System.out.println("sqlQueryNav="+sql);
        ResultSet os;//执行SQL的ResultSet

        JDBC jdbc = new JDBC();
        QueryJsonData jsonData = new QueryJsonData();
            try {
                // 执行查询
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<ArrayList> listData = new ArrayList<ArrayList>();

                    int i = 1;
                    while (os.next()) {
                        ArrayList data = new ArrayList();
                        data.add(os.getString("MenuID"));
                        data.add(os.getString("ParentID"));
                        data.add(os.getString("MenuName"));
                        data.add(os.getString("URL"));
                        data.add(os.getString("OrderID"));
                        listData.add(data);
                    }
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
