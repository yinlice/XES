package Wxzt.servlet.background;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
* Created by Pengxi on 2015/9/2.
*/
public class DialogUserAdd extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        System.out.println("******^^^^^^^^^^+++++++++++");
        PrintWriter out = response.getWriter();
        //获取form传递的参数
        String name = request.getParameter("UserName");//用户姓名
        String WorkName = request.getParameter("WorkName");//工号
        String RoleName = request.getParameter("RoleName");//角色
        String DepartmentName = request.getParameter("DepartmentName");//部门
        String IsManager = request.getParameter("IsManager");//是否经理（1：是；2：不是）
        String Queue = request.getParameter("Queue");//队列

        String sql = "INSERT  into PX_CRM_User (UserName,WorkName,RoleID,DepartmentID,IsManager,Queue) values ('"+name+"','"+WorkName+"','"+RoleName+"','"+DepartmentName+"','"+IsManager+"','"+Queue+"')";
        System.out.println("SQLstate---"+sql);
        JDBC jdbc = new JDBC();

        Boolean AddCom = jdbc.executeUpdate(sql);
        out.println(AddCom);

    }
}
