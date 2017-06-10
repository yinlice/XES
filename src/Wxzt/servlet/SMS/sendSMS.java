package Wxzt.servlet.SMS;

import Wxzt.servlet.bean.QueryTableJson;
import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2016-4-28.
 */
public class sendSMS extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------sendSMS");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JDBC jdbc = new JDBC();
        QueryTableJson jsonData = new QueryTableJson();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        ResultSet os;//记录用户基础信息的ResultSet

        String content = "",mobile = "",worknum = "",custnum = "",dt = "",sql = "";
        content = request.getParameter("smsContent");
        mobile = request.getParameter("mobile");
        worknum = request.getParameter("worknum");
        custnum = request.getParameter("custNum");

        String[] mobiles = mobile.split(",");

        if(mobiles.length==1){
            //获取来电号码的归属坐席
            if(Objects.equals(custnum, "")){
                sql = "select custnum from px_CRM_Cust where mobile = '"+mobile+"'";
                os = jdbc.executeQuery(sql);
                try {
                    ArrayList<String> cust = new ArrayList<String>();
                    while (os.next()) {
                        cust.add(os.getString("custnum"));
                    }
                    int count = cust.size();
                    if(count>0){
                        custnum = cust.get(0);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else {
            custnum = "";
        }

        String insertValues = "";
        for(int i = 0;i<mobiles.length;i++){
            insertValues += " select '"+mobiles[i]+"','"+content+"','"+formatter.format(new Date(System.currentTimeMillis()))+"'," +
            "'"+custnum+"','"+worknum+"' ";
            if(i!=mobiles.length-1){
                insertValues += " union all ";
            }
        }

        sql = "insert into WX_SMS_waitSend (Mobile,smsContent,dt,custnum,worknum) " +insertValues;
        System.out.println("添加发送短信："+sql);
        // 添加数据
        boolean AddCom = jdbc.executeUpdate(sql);
        System.out.println("查询结果："+AddCom);
        jdbc.closeConnection();
        out.println(AddCom);
    }
}

