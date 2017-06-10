package Wxzt.servlet.Common.table;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-7-12.
 */
public class queryTableHead extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------queryTableHead");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getCdrSQL(request, action));
        out.println(json);
    }
    /*action:0更新表头信息*/
    public Object getCdrSQL(HttpServletRequest request,String action) {
        tableHeadSql sql = new tableHeadSql();
        if (Objects.equals(action, "0")) {
            return sql.updateTableHead(request);
        }
        return null;
    }
}