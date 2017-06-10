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
 * Created by Administrator on 2015/10/5.
 */
public class getReportLine extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.print("tt--------------------");
        PrintWriter out = response.getWriter();

        String sql = "select HotLine from dddReport_HotLine Group by HotLine";//查询语句
        ResultSet os;//执行SQL的ResultSet

        JDBC jdbc = new JDBC();
        QueryTableJson jsonData = new QueryTableJson();
        try {
            // 执行查询
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(os.getString("HotLine"));//0
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
