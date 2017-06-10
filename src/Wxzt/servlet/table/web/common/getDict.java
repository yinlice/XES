package Wxzt.servlet.table.web.common;

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
 * Created by Administrator on 2015/11/9.
 */
public class getDict extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.println("=-------------getDict");
        PrintWriter out = response.getWriter();

        String sql ;//查询语句
        ResultSet os;//记录总数据数量的ResultSet

        //获取jQuery传递的参数
        String DictList = request.getParameter("DictType");//更新数据
        sql = "select * from V_Dict where DictType in ("+DictList+")";
        System.out.println("SQLstate="+sql);

        JDBC jdbc = new JDBC();
        //查询表单数据数量
        //获取数据
        QueryJsonData jsonData = new QueryJsonData();
        try {
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("parentID"));//父级ID-0
                    data.add(os.getString("DictType"));//字典项编号-1
                    data.add(os.getString("DictCode"));//字典项value-2
                    data.add(os.getString("DictMean"));//字典项显示值-3
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
