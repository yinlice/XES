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
public class DialogCompanyUpdata extends HttpServlet{
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        PrintWriter out = response.getWriter();

        //获取form传递的参数
        String name = request.getParameter("nameTest");
        String Tel = request.getParameter("nameTel");
        String ID = request.getParameter("ID");
        String NewType = request.getParameter("type");
        System.out.println("77777777777777777777777777777");

        String sql ="";
        if(NewType.equals("delete")){
            sql = "delete from px_CRM_Company where id = '"+ID+"'";
        }else if(NewType.equals("updata")){
            sql = "update px_CRM_Company set CompanyName='"+name+"' , CompanyTel='"+Tel+"' where id="+ID;
        }

        System.out.println("SQLstate+++++="+sql);
        JDBC jdbc = new JDBC();

        Boolean AddCom = jdbc.executeUpdate(sql);
        out.println(AddCom);
    }
}
