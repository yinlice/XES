package Wxzt.servlet.SMS.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-9-27.
 */
public class updataSmsModel extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------updataSmsModel");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //��ȡ������䷵��ֵ
        out.println(getSmsModelSQL(request, action));
    }

    /*action:0Ϊ����ģ����£�1Ϊ����ģ����ӣ�2Ϊ����ģ��ɾ��*/
    public boolean getSmsModelSQL(HttpServletRequest request,String action){
        smsModelSql sql = new smsModelSql();
        if(Objects.equals(action, "0")){
            return sql.updataModel(request);
        }else if(Objects.equals(action, "1")) {
            return sql.insertModel(request);
        }else if(Objects.equals(action, "2")){
            return sql.delModel(request);
        }
        return false;
    }
}
