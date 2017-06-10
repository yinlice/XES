package Wxzt.servlet.dynamic;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-17.
 */
public class queryDynamic extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------queryDynamic");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getCustSQL(request,action));
        out.println(json);
    }
    /*action:0为查询动态页面，1位查询单条数据*/
    public Object getCustSQL(HttpServletRequest request,String action) {
        dynamicSql sql = new dynamicSql();
        if (Objects.equals(action, "0")) {
            return sql.dynTableQuery(request);
        }
        return null;
    }
}
