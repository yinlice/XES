package Wxzt.servlet.Log;

import Wxzt.servlet.QueryReport.ReportSql;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by yin on 2017/4/25.
 */
public class QueryLog extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        System.out.println("-----------QueryLog");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        ArrayList jsonData = (ArrayList)getCustSQL(request,action);
        String json = mapper.writeValueAsString(jsonData);
        out.println(json);
    }
    /**
     * action:0为查询第一级节点
     * 1:根据pid查询第二,三,四节点
     * 2:配置第一个节点
     * 3:配置第2,3,4个节点
     * */
    public Object getCustSQL(HttpServletRequest request,String action){
        LogSql sql = new LogSql();
        if (Objects.equals(action, "0")){
            return sql.getFirstNode(request);
        }else if (Objects.equals(action, "1")){
            return sql.getNodeByPid(request);
        }else if (Objects.equals(action, "2")){
            return sql.addFirstMenu(request);
        }else if (Objects.equals(action, "3")){
            return sql.addMenu(request);
        }
        return null;
    }
}
