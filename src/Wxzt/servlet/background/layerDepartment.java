package Wxzt.servlet.background;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/9/18.
 */
public class layerDepartment  extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String sql = "";
        JDBC jdbc = new JDBC();
        //获取form传递的参数
        String action = request.getParameter("action");//1为添加；2为编辑；3为删除
        System.out.println("action============="+action);
        if(action.equals("3")){
            int ID = Integer.parseInt(request.getParameter("ID"));//
            sql = "DELETE FROM dddGroup WHERE Groupid = "+ID;
            boolean result = jdbc.executeUpdate(sql);
            out.println(result);
        }else {
            String GroupName = request.getParameter("GroupName");//
            String Note = request.getParameter("Note");//
            String crm = request.getParameter("crm");//
            int role = Integer.parseInt(request.getParameter("role"));
            int Groupnum = Integer.parseInt(request.getParameter("Groupnum"));//
            if(action.equals("1")){
                sql = "INSERT  into dddGroup (GroupName,Note,Groupnum,crm,role) values ('"+GroupName+"','"+Note+"',"+Groupnum+",'"+crm+"',"+role+")";
            }else if(action.equals("2")){
                int ID = Integer.parseInt(request.getParameter("ID"));//
                sql = "update dddGroup set GroupName='"+GroupName+"' , Note='"+Note+"',Groupnum="+Groupnum+" ,crm = '"+crm+"',role = "+role+"  where Groupid ="+ID;
            }
            jdbc.executeUpdate(sql);
        }
        System.out.println("SQLstate---" + sql);
        jdbc.closeConnection();

    }
}
