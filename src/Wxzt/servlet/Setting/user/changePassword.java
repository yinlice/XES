package Wxzt.servlet.Setting.user;

import Wxzt.servlet.Common.Jdbc001;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/9/28.
 */
public class changePassword extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.println("-----------changePassword");

        PrintWriter out = response.getWriter();
        //获取form传递的参数
        String User = request.getParameter("User");
        String newPassword = request.getParameter("newPassword");
        String oldPassword = request.getParameter("oldPassword");

        String sql = "update dddWork set password='"+newPassword+"'  where worknum='"+User+"' and password='"+oldPassword+"'";
        System.out.println("密码修改路径："+sql);

        Jdbc001 jdbc = new Jdbc001();
        int count=jdbc.executeUpdate(sql);
        jdbc.closeConnection();
        out.println(count);
    }
}

