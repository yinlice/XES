package Wxzt.servlet;

import Wxzt.servlet.bean.CommunicationBean;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
* Created by Pengxi on 2015/8/25.
*/
public class CompanySMS extends HttpServlet{
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();
        System.out.println("******^^^^^^^^^^+++++++++++");
        //获取form传递的参数
        String text = request.getParameter("text1");
        String ID = request.getParameter("ID1");
        String type = request.getParameter("type");
        System.out.println(ID+"^^^"+text);

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now=format.format(date);

        String sql;
        ResultSet os;//执行SQL的ResultSet

        JDBC jdbc = new JDBC();

        if(type.equals("add")){
            sql = "INSERT  into px_CRM_CompanySMS (users,Record,FirstTime) values('"+ID+"','"+text+"','"+now+"')";
            System.out.println("^^^^^^^^^^6"+sql);

            Boolean AddCom = jdbc.executeUpdate(sql);
            out.println(AddCom);
        }else if(type.equals("select")){
            sql = "select * from px_CRM_CompanySMS where users = '"+ID+"' order by id desc";
            System.out.println("6666666666"+sql);

            //获取数据
            CommunicationBean jsonData = new CommunicationBean();
            try {
                // 执行查询
                System.out.println(sql);
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<String[]> listData = new ArrayList<String[]>();
                    while (os.next()) {
                        String[] data = new String[4];
                        data[0] = os.getString("id");
                        data[1] = os.getString("users");
                        data[2] = os.getString("Record");
                        data[3] = os.getString("FirstTime");
                        listData.add(data);
                    }
                    jsonData.setDataList(listData);
                } catch (SQLException e) {
                    System.out.println("+++++++++++++++++");
                    e.printStackTrace();
                }
                jdbc.closeConnection();
//                out.println(outAjax);//返回html信息
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(jsonData);
            System.out.println(json);
            out.println(json);
        }

    }
}
