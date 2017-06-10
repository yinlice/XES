package Wxzt.servlet.logreminder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class UpdateLogreminder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------日志提醒");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getLogreminderSQL(request, action));
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    /*action:0为删除，1为更新，2为添加*/
    public boolean getLogreminderSQL(HttpServletRequest request, String action) {
        LogreminderSql sql = new LogreminderSql();
        if (Objects.equals(action, "0")) {/*删除日志提醒*/
            return sql.logreminderDel(request);
        }else if(Objects.equals(action, "1")) {/*添加日志提醒*/
            return sql.insertlogreminder(request);
        }else if(Objects.equals(action, "2")) {/*修改日志提醒*/
            return sql.updatelogreminder(request);
        }
        return false;
    }

}
