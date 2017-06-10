package Wxzt.servlet.Setting.role;

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

/**
 * Created by Administrator on 2016-4-27.
 */
public class getMenu extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String sql = "select * from px_CRM_Menu order by OrderID";//查询用户基础信息
        ResultSet os;//记录用户基础信息的ResultSet
        JDBC jdbc = new JDBC();
        //获取数据
        QueryJsonData jsonData = new QueryJsonData();
        try {
            // 执行查询
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data_T = new ArrayList();
                    data_T.add(os.getString("MenuID"));
                    data_T.add(os.getString("ParentID"));
                    data_T.add(os.getString("MenuName"));
                    data_T.add(os.getString("URL"));
                    data_T.add(os.getString("OrderID"));
                    listData.add(data_T);
                }
                int count = listData.size();
                if(count>0){
                    jsonData.setDataList(listData);
                    jsonData.setDataType("Y");
                }else {
                    jsonData.setDataType("N");//无数据
                }
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

