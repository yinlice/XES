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

/**
* Created by Pengxi on 2015/9/2.
*/
public class CallFace extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String TelNum;
        String sql;

        ResultSet osCount;//查询数据的ResultSet

        TelNum = request.getParameter("Tel");

        JDBC jdbc = new JDBC();
        sql = "select  * from px_CRM_Company where CompanyTel='"+TelNum+"' limit 1";
        System.out.println("findsql="+sql);
        //获取数据
        QueryTableJson jsonData = new QueryTableJson();
        try {
            ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
            osCount = jdbc.executeQuery(sql);
            if (osCount.next()) {
                ArrayList data = new ArrayList();
                data.add(osCount.getString("id"));
                data.add(osCount.getString("CompanyName"));
                listData.add(data);
                jsonData.setDataList(listData);
                jsonData.setDataType("O");
            }else {
                jsonData.setDataType("N");
            }
        }catch(SQLException e){
            e.printStackTrace();
            jsonData.setDataType("No");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);
    }
}
