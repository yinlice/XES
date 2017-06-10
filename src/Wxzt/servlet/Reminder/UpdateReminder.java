package Wxzt.servlet.Reminder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
/**
 * UpdateReminder
 */
public class UpdateReminder extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------updataCust");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getReminderSQL(request,action));
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    /*action:0为更新*/
    public boolean getReminderSQL(HttpServletRequest request,String action){
        ReminderSql reminderSql = new ReminderSql();
        if(Objects.equals(action, "0")){/*更新消息提醒*/
            System.out.println("更新消息提醒------");
            return reminderSql.updateReminder(request);
        }
        return false;
    }

}
