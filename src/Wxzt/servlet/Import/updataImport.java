package Wxzt.servlet.Import;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-8-1.
 */
public class updataImport extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------updataImport");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getCustSQL(request,action));
    }

    /*action:0为更新是否可提取*/
    public boolean getCustSQL(HttpServletRequest request,String action){
        System.out.println("------------------updataImport"+action);
        if(Objects.equals(action, "0")){
            stationSql sta = new stationSql();
            return sta.updataStationIsShow(request);
        }
        return false;
    }
}

