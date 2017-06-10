package Wxzt.servlet;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/10/14.
 */
public class restorePassword extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.println("-----------");

        PrintWriter out = response.getWriter();
        //获取form传递的参数
        int ID = Integer.parseInt(request.getParameter("ID"));

        String sql = "update dddWork set password='123'  where workid="+ID;
        System.out.println("密码："+sql);

        JDBC jdbc = new JDBC();
        boolean count=jdbc.executeUpdate(sql);
        out.println(count);
    }
}
