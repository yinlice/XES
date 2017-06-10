package Wxzt.servlet.table.web;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/10/19.
 */
public class updataDuty extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("===================updataDuty");
        PrintWriter out = response.getWriter();

        String sql ="";
        //获取form传递的参数
        String action = request.getParameter("action");//0为删除；1为更新添加
        String ID = request.getParameter("ID");
        if(action.equals("0")){
            sql = "delete  from dddOnDuty where id ="+ID;
        }else if(action.equals("1")){
            System.out.println("ID+++++="+ID);
            String GroupName = request.getParameter("GroupName");
            String Weekly = request.getParameter("Weekly");
            String OnDutyName = request.getParameter("OnDutyName");
            String OnDutyTelnum = request.getParameter("OnDutyTelnum");

            if(ID.equals("0")){
                sql = "INSERT  into dddOnDuty (WorkOID,Weekly,OnDutyName,OnDutyTelnum) values ("+GroupName+",'"+Weekly+"','"+OnDutyName+"','"+OnDutyTelnum+"')";
            }else {
                sql = "update dddOnDuty set WorkOID="+GroupName+" , Weekly='"+Weekly+"', OnDutyName='"+OnDutyName+"', OnDutyTelnum='"+OnDutyTelnum+"' where id="+ID;
            }
        }
        System.out.println("SQLstate+++++="+sql);
        JDBC jdbc = new JDBC();

        boolean AddCom = jdbc.executeUpdate(sql);
        out.println(AddCom);
    }
}
