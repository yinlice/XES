package Wxzt.servlet.background;

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
 * Created by Administrator on 2015/9/18.
 */
public class getTableSelectData extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("**********************************");

        PrintWriter out = response.getWriter();

        String sql;//查询用户基础信息
        String sql_T;//查询导航条信息
        ResultSet os;//记录用户基础信息的ResultSet
        ResultSet os_T;//记录导航条信息的ResultSet

        JDBC jdbc = new JDBC();
        sql = "select JSBH,JSMC from px_CRM_JSQX";
        System.out.println("sql_1="+sql);

        //获取数据
        QueryJsonData jsonData = new QueryJsonData();
        try {
            // 执行查询
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("JSBH"));//0角色编号
                    data.add(os.getString("JSMC"));//1角色名称
                    listData.add(data);
                }

                sql_T = "select * from dddGroup";
                os_T = jdbc.executeQuery(sql_T);
                ArrayList<ArrayList> listData_T = new ArrayList<ArrayList>();
                while (os_T.next()) {
                    ArrayList data_T = new ArrayList();
                    data_T.add(os_T.getString("Groupid"));
                    data_T.add(os_T.getString("GroupName"));
                    listData_T.add(data_T);
                }
                jsonData.setDataList(listData);
                jsonData.setDataList_T(listData_T);
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
