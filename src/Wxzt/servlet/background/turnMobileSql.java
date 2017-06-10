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
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-24.
 */
public class turnMobileSql extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("----------------turnMobileSql");
        String action = request.getParameter("action");/*0为获取数据，1为更新数据*/
        if(Objects.equals(action, "0")){
            getData(request,response);
        }else if(Objects.equals(action, "1")){
            updata(request,response);
        }
    }
    public void getData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String worknum = request.getParameter("worknum");
        String sql = "select mobile1,turnMobileType from dddWork where worknum='" + worknum + "'";
        System.out.println("查询转移手机相关信息：" + sql);
        QueryJsonData jsonData = new QueryJsonData();
        PrintWriter out = response.getWriter();
        ResultSet os;
        try {
            JDBC jdbc = new JDBC();
            os = jdbc.executeQuery(sql);
            try {
                ArrayList<ArrayList> listData = new ArrayList<ArrayList>();
                while (os.next()) {
                    ArrayList data = new ArrayList();
                    data.add(os.getString("mobile1"));
                    data.add(os.getString("turnMobileType"));
                    listData.add(data);
                }
                jsonData.setDataList(listData);
                jsonData.setDataType("Y");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            jdbc.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonData);
        System.out.println(json);
        out.println(json);
    }
    public void updata(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String worknum = request.getParameter("worknum");
        String mobile1 = request.getParameter("mobile1");
        String type = request.getParameter("turnMobileType");
        String sql = "update dddWork set mobile1='"+mobile1+"',turnMobileType='"+type+"' where worknum='"+worknum+"'";
        System.out.println("更新转移手机相关信息："+sql);
        PrintWriter out = response.getWriter();
        boolean os = false;
        try {
            JDBC jdbc = new JDBC();
            os = jdbc.executeUpdate(sql);
            jdbc.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(os);
    }
}
