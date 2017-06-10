package Wxzt.servlet.table.web.Import;

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
 * Created by Administrator on 2015/11/23.
 */
public class getAllWorkers extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();

        ResultSet os;//执行SQL的ResultSet

        String sql = "select worknum,workname,GroupName from dbo.dddWork as a\n" +
                "left join dbo.dddGroup as b on a.Groupid = b.Groupid order by GroupName";
        JDBC jdbc = new JDBC();
        System.out.println(sql);

        QueryJsonData jsonData = new QueryJsonData();
        try {
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("worknum"));//工号-0
                    data.add(os.getString("workname"));//姓名-1
                    data.add(os.getString("GroupName"));//工作组-2
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



