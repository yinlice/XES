package Wxzt.servlet.background;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/10/10.
 */
public class layerCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("layerCustomer*********");

        String sql = "";
        JDBC jdbc = new JDBC();
        String DataTable = "px_CRM_ServiceStation";//数据表
        PrintWriter out = response.getWriter();
        //获取form传递的参数
        int ID;//ID;
        String action = request.getParameter("action");//1为添加；2为更新;3为删除
        System.out.println("+++++" + action);
        if (action.equals("3")) {
            System.out.println("action=3");
            ID = Integer.parseInt(request.getParameter("id"));//ID
            sql = "DELETE FROM "+DataTable+" WHERE nID = " + ID;
        } else {
            System.out.println("+++++" + action);
            String userName = request.getParameter("userName");//用户姓名
            String sex = request.getParameter("sex");//性别
            String StationName = request.getParameter("companyName");//分机号
            String Address = request.getParameter("address");//备注
            String TelNumber = request.getParameter("Tel");//是否监控
            String QQ = request.getParameter("QQ");//备注
            String Email = request.getParameter("Email");//是否监控
            String MSN = request.getParameter("MSN");//备注
            System.out.println("+++++" + action);
            if (action.equals("1")) {
                System.out.println("+++++" + action+"===========");
                sql = "INSERT  into "+DataTable+" (UserName,UserSex,StationName,TelNumber,Address,QQ,Email,MSN,AddTime) values ('" + userName + "','" + sex + "','" + StationName + "','" + TelNumber + "','" + Address + "','" + QQ + "','" + Email + "','" + MSN + "',getdate())";
            } else if (action.equals("2")) {
                ID = Integer.parseInt(request.getParameter("id"));//ID
                sql = "update "+DataTable+" set UserName='" + userName + "' ,  UserSex='" + sex + "' , StationName='" + StationName + "' , TelNumber='"+ TelNumber +"' , Address='" + Address + "' ,QQ='" + QQ + "' ,Email='" + Email + "',MSN='" + MSN + "' where nID=" + ID;
            }
        }
        System.out.println("SQLstate===" + sql);
        boolean result = jdbc.executeUpdate(sql);
        out.print(result);
        jdbc.closeConnection();
    }
}

