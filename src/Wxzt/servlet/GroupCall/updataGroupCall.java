package Wxzt.servlet.GroupCall;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-10-17.
 */
public class updataGroupCall extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------updataGroupCall");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getCustSQL(request,action));
    }

    /*action:0为删除，1为更新，2为添加，3为更新群呼任务状态*/
    public boolean getCustSQL(HttpServletRequest request,String action){
        groupCallSql gc = new groupCallSql();
        if(Objects.equals(action, "0")){/*删除任务*/
            return gc.daleteGroupCall(request);
        }else if(Objects.equals(action, "1")) {/*更新任务*/
            return gc.updateGroupCall(request);
        }else if(Objects.equals(action, "2")){/*添加任务*/
            return gc.insertGroupCall(request);
        }else if (Objects.equals(action, "3")) {
            return gc.updataGroupStatus(request);
        }
        return false;
    }
}
