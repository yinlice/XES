package Wxzt.servlet.Cdr;

import Wxzt.servlet.Common.table.tableHeadSql;
import Wxzt.servlet.bean.Common.queryJson;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
* Created by Pengxi on 2015/9/4.
*/
public class queryCdr extends HttpServlet{
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------queryCdr");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        queryJson jsonData = (queryJson) getCdrSQL(request,action);
        //设置table表头信息
        String IsGetTableHead = request.getParameter("IsGetTableHead");
        if(IsGetTableHead!=null&&Objects.equals(IsGetTableHead, "0")){
            tableHeadSql t = new tableHeadSql();
            jsonData.setTableHead(t.tableHeadQuery(request));
        }
        String json = mapper.writeValueAsString(jsonData);
        out.println(json);
    }
    /*action:0为查询页面，1位查询单条数据;2为来电弹屏数据;3为首页弹屏数据;4为当前未处理漏接电话数量*/
    public Object getCdrSQL(HttpServletRequest request,String action) {
        cdrTableSql sql = new cdrTableSql();
        if (Objects.equals(action, "0")) {
            return sql.cdrQuery(request);
        } else if (Objects.equals(action, "1")) {
            return sql.cdrOneQuery(request);
        }else if (Objects.equals(action, "2")) {
            cdrSql cdr = new cdrSql();
            return cdr.cdrQuery(request);
        }else if (Objects.equals(action, "3")||Objects.equals(action, "4")) {
            return sql.queryMainCdr(request);
        }
        return null;
    }
}
