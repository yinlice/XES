package Wxzt.servlet.table.web.knowledge;

import Wxzt.servlet.bean.QueryJsonData;
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
 * Created by Administrator on 2015/12/23.
 */
public class getContent extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();

        String sql ;//查询语句
        ResultSet os;//执行SQL的ResultSet

        //获取jQuery传递的参数
        String ID = request.getParameter("ID");
        sql = "select kb_Content from px_CRM_KnowledgeBase where ID = '"+ID+"'";
        System.out.println("SQL="+sql);

        JDBC jdbc = new JDBC();
        //获取数据
        QueryJsonData jsonData = new QueryJsonData();
        jsonData.setDataType("N");//默认无数据
        try {
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("kb_Content"));//ID-0
                    listData.add(data);
                }
                jsonData.setDataList(listData);
                jsonData.setDataType("Y");
                System.out.println("ok");
            } catch (SQLException e) {
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

