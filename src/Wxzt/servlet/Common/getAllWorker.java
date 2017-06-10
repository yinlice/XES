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

/**
 * Created by Administrator on 2016/1/6.
 */
public class getAllWorker extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("getAllWorker------------");
        PrintWriter out = response.getWriter();

        String sql;//查询用户基础信息
        ResultSet os;//记录用户基础信息的ResultSet

        JDBC jdbc = new JDBC();
        sql = "select worknum,workname,GroupName,workid from V_dddWork order by GroupName";
        System.out.println("SQLstate="+sql);
        //获取数据
        QueryJsonData jsonData = new QueryJsonData();
        try {
            // 执行查询
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("workname"));//0姓名
                    data.add(os.getString("worknum"));//1工号
                    data.add(os.getString("GroupName"));//2工作组
                    data.add(os.getString("workid"));//3坐席编号
                    listData.add(data);
                }
                jsonData.setDataList(listData);
                jsonData.setDataType("Y");
            } catch (SQLException e) {
                jsonData.setDataType("N");//无数据
                System.out.println("+++++++++++++++++");
                e.printStackTrace();
            }
            jdbc.closeConnection();
        } catch (ClassCastException e) {
            jsonData.setDataType("N");//无数据
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);
    }
}
