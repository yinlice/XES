package Wxzt.servlet;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
* Created by Pengxi on 2015/8/24.
*/
public class DialogCompanyAddTel extends HttpServlet{
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        System.out.println("******^^^^^^^^^^+++++++++++");
        PrintWriter out = response.getWriter();
        //获取form传递的参数
        String name = request.getParameter("nameTest");
        String Tel = request.getParameter("nameTel");

        String sql = "INSERT  into px_CRM_Company values ('"+name+"','"+Tel+"')";

        JDBC jdbc = new JDBC();

        Boolean AddCom = jdbc.executeUpdate(sql);
        out.println(AddCom);

    }
}
