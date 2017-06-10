package Wxzt.servlet.Common;

import Wxzt.servlet.bean.QueryJsonData;
import Wxzt.servlet.bean.queryBox;
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
 * Created by Administrator on 2015/11/13.
 */
public class QuerySearch extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String sql;
        //传递的参数

        String keyword = request.getParameter("keyword");
        System.out.println("keyword*********************"+keyword);
        sql = "select  CustName,Mobile from dbo.px_CRM_Cust where CustName like '%"+keyword+"%' limit 0,10 ";
        System.out.println("sqlQueryNav="+sql);
        ResultSet os;//执行SQL的ResultSet

        JDBC jdbc = new JDBC();
        QueryJsonData jsonData = new QueryJsonData();
        try {
            // 执行查询
            os = jdbc.executeQuery(sql);
            try {
                ArrayList listData = new ArrayList();
                int i = 1;
                while (os.next()) {
                    queryBox titleData = new queryBox();
                    titleData.setTitle(os.getString("CustName"));
                    titleData.setTelNum(os.getString("Mobile"));
                    listData.add(titleData);
                }
                jsonData.setDataList_O(listData);
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
