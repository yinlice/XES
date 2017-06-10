package Wxzt.servlet.SMS;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-18.
 */
public class querySms extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------querySms");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getCustSQL(request,action));
        out.println(json);
    }
    /*action:0为待发送短信分页查询，1为短信发件箱分页查询，2为短信收件箱分页查询*/
    public Object getCustSQL(HttpServletRequest request,String action) {
        if (Objects.equals(action, "0")) {
            waitSendSql sql = new waitSendSql();
            return sql.waitSendQuery(request);
        } else if (Objects.equals(action, "1")) {
            sendedSql sql = new sendedSql();
            return sql.sendedQuery(request);
        }else if (Objects.equals(action, "2")) {
            rcvSql sql = new rcvSql();
            return sql.rcvQuery(request);
        }
        return null;
    }
}

