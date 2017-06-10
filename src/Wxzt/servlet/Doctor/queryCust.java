package Wxzt.servlet.Doctor;

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
 * Created by Administrator on 2016-5-9.
 */
public class queryCust extends HttpServlet {
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

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        queryJson jsonData = (queryJson) getCustSQL(request,action);
        //设置table表头信息
        String IsGetTableHead = request.getParameter("IsGetTableHead");
        if(IsGetTableHead!=null&&Objects.equals(IsGetTableHead, "0")){
            tableHeadSql t = new tableHeadSql();
            jsonData.setTableHead(t.tableHeadQuery(request));
        }
        String json = mapper.writeValueAsString(jsonData);
        out.println(json);
    }
    /*action:0为查询页面，1位查询单条数据，2为查询联系人数据，3为获取单条联系人数据*/
    public Object getCustSQL(HttpServletRequest request,String action) {
        Wxzt.servlet.Doctor.custSql sql = new Wxzt.servlet.Doctor.custSql();
        if (Objects.equals(action, "0")) {
            return sql.custQuery(request);
        } else if (Objects.equals(action, "1")) {
            return sql.custOneQuery(request);
        }else if (Objects.equals(action, "2")) {
            return sql.custContactQuery(request);
        }else if (Objects.equals(action, "3")) {
            return sql.custConOneQuery(request);
        }
        return null;
    }
}

