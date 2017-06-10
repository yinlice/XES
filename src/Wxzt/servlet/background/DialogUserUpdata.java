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
public class DialogUserUpdata extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.println("ddddddddddddddddd");
        PrintWriter out = response.getWriter();

        //获取form传递的参数
        String UserName = request.getParameter("UserName");//用户姓名
        String WorkName = request.getParameter("WorkName");//工号
        String ID = request.getParameter("ID");//编号
        String RoleName = request.getParameter("RoleName");//角色
        String DepartmentName = request.getParameter("DepartmentName");//部门
        String IsManager = request.getParameter("IsManager");//是否经理
        String Queue = request.getParameter("Queue");//队列
        System.out.println("77777777777777777777777777777");

        String sql ="";
//        if(NewType.equals("delete")){
//            SQLstate = "delete from px_CRM_Company where id = '"+ID+"'";
//        }else if(NewType.equals("updata")){
//            SQLstate = "update px_CRM_Company set CompanyName='"+name+"' , CompanyTel='"+Tel+"' where id="+ID;
//        }
        sql = "update PX_CRM_User set UserName='"+UserName+"' , WorkName='"+WorkName+"', RoleID='"+RoleName+"' , DepartmentID='"+DepartmentName+"', IsManager='"+IsManager+"' , Queue='"+Queue+"'where ID="+ID;

        System.out.println("SQLstate+++++="+sql);
        JDBC jdbc = new JDBC();

        Boolean AddCom = jdbc.executeUpdate(sql);
        out.println(AddCom);
    }
}
