package Wxzt.servlet.Setting.user.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-7-7.
 */
public class queryComUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------queryComUser");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getCdrSQL(request, action));
        out.println(json);
    }
    /*action:0为查询页面，1为表单编辑单条数据;2为个人编辑;3为更新坐席*/
    public Object getCdrSQL(HttpServletRequest request,String action) {
        comUserSql sql = new comUserSql();
        if (Objects.equals(action, "0")) {
            return sql.comUserQuery(request);
        } else if (Objects.equals(action, "1")) {
            return sql.comUserOneQuery(request, true);
        }else if (Objects.equals(action, "2")) {
            return sql.comUserOneQuery(request, false);
        }else if (Objects.equals(action, "3")) {
            return sql.updateComUser(request);
        }
        return null;
    }
}