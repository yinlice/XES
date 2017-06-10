package Wxzt.servlet.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by yin on 2017/4/26.
 */
public class UpdateLog extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getCustSQL(request,action));
    }

    /*action:0为删除，1为更新*/
    public boolean getCustSQL(HttpServletRequest request,String action){
        LogSql sql = new LogSql();
        if(Objects.equals(action, "0")){/*删除客户*/
          return sql.RemoveLog(request);
        }else if(Objects.equals(action, "1")) {/*更新客户*/
           return sql.ModifyLog(request);
        }
        return false;
    }


}
