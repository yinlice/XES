package Wxzt.servlet.Import;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-8-2.
 */
public class updataDivis extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        PrintWriter out = response.getWriter();

        System.out.println("---------------------updataDivis");
        String action = request.getParameter("action");
        out.println(getSQL(request, action));
    }
    /*action:1为资源管理页面分配数据*/
    public boolean getSQL(HttpServletRequest request,String action){
        if(Objects.equals(action, "1")){
            importSql sta = new importSql();
            return sta.updataDivis_1(request);
        }else if(Objects.equals(action, "2")){
            importSql sta = new importSql();
            return sta.updataDivis_2(request);
        }else if(Objects.equals(action, "3")){
            importSql sta = new importSql();
            //return sta.updataDivis_3(request);
        }
        return false;
    }
}
