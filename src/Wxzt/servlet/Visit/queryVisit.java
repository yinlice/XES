package Wxzt.servlet.Visit;

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
 * Created by Administrator on 2016/1/22.
 */
public class queryVisit extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------queryVisit");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        String json = "";
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        if(Objects.equals(action, "1")){
            json = String.valueOf(getInvSQL(request,action));
        }else {
            queryJson jsonData = (queryJson) getInvSQL(request,action);
            //设置table表头信息
            String IsGetTableHead = request.getParameter("IsGetTableHead");
            if(IsGetTableHead!=null&&Objects.equals(IsGetTableHead, "0")){
                tableHeadSql t = new tableHeadSql();
                jsonData.setTableHead(t.tableHeadQuery(request));
            }
            json = mapper.writeValueAsString(jsonData);
        }
        out.println(json);
    }
    /*action:0为查询页面,1为首页获取当日待回访*/
    public Object getInvSQL(HttpServletRequest request,String action) {
        visitSql sql = new visitSql();
        if (Objects.equals(action, "0")) {
            return sql.visitQuery(request);
        }else if(Objects.equals(action, "1")){
            return sql.visitOneQuery(request);
        }else if(Objects.equals(action, "2")){
            calendarSql cal = new calendarSql();
            return cal.visitQuery(request);
        }
        return null;
    }
}

