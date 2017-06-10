package Wxzt.tool.weixin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by pengxi on 2016/12/12.
 */
public class updataWX extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------updataWX");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        response.setHeader("Access-Control-Allow-Headers", "*");


        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        boolean b = up(request, action);
        String callback = request.getParameter("callback");
        System.out.println("返回结果："+callback+"("+b+")");
        out.println(callback+"("+b+")");
    }

    /*action:0为更新openid*/
    public boolean up(HttpServletRequest request,String action){
        wx sql = new wx();
        if(Objects.equals(action, "0")){
            return sql.upOpenid(request.getParameter("worknum"),request.getParameter("pw"),request.getParameter("openid"));
        }
        return false;
    }
}
