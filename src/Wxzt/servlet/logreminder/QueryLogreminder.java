package Wxzt.servlet.logreminder;
import Wxzt.servlet.Common.table.tableHeadSql;
import Wxzt.servlet.bean.Common.queryJson;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
/**
 * Created by Administrator on 2017/4/26 0026.
 */
public class QueryLogreminder extends HttpServlet{
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------queryCust");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        queryJson jsonData = (queryJson) getLogreminderSQL(request,action);
        //设置table表头信息
        String IsGetTableHead = request.getParameter("IsGetTableHead");
        if(IsGetTableHead!=null&&Objects.equals(IsGetTableHead, "0")){
            tableHeadSql t = new tableHeadSql();
            jsonData.setTableHead(t.tableHeadQuery(request));
        }
        String json = mapper.writeValueAsString(jsonData);
        out.println(json);
    }
    /*action:0为查询页面*/
    public Object getLogreminderSQL(HttpServletRequest request,String action) {
        LogreminderSql sql = new LogreminderSql();
        if (Objects.equals(action, "0")) {
            return sql.logreminderQuery(request);
        }
        return null;
    }
}
