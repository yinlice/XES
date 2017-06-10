package Wxzt.servlet.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-6-23.
 */
public class queryConfig extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------queryConfig");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getConfigSQL(request, action));
        out.println(json);
    }
    /*action:0为获取数据，1为更新数据*/
    public Object getConfigSQL(HttpServletRequest request,String action) {
        configSql sql = new configSql();
        if (Objects.equals(action, "0")) {
            return sql.configOneQuery();
        } else if (Objects.equals(action, "1")) {
            return sql.updateConfig(request);
        }
        return null;
    }
}
