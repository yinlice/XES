package Wxzt.servlet.QueryReport;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/4.
 */
public class QueryReport extends HttpServlet{
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        System.out.println("-----------QueryReport");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        ArrayList jsonData = (ArrayList)getCustSQL(request,action);
        String json = mapper.writeValueAsString(jsonData);
        out.println(json);
    }
    /**
     * action:0为今日个人话务总量
     * 1:满意度
     * 2:今日个人工单总量
     * 3:今日上线总时长
     * 5.工单总量
     * 6:分机号码各小时接听数量 折线图
     * 7: 权限接通率 满意度(双折线图)
     * 8:今日个人平均通话时长
     * 9:昨日各线路对比
     * 10:操作记录
     * */

    public Object getCustSQL(HttpServletRequest request,String action){
        ReportSql sql = new ReportSql();
        if (Objects.equals(action, "0")){
            return sql.getPersonTel(request);
        }else if (Objects.equals(action, "1")){
            return sql.Manyidu(request);
        }else if (Objects.equals(action, "3")){
            return sql.getOnLineInfo(request);
        }else if (Objects.equals(action, "6")){
            return sql.getHourCount(request);
        }else if (Objects.equals(action, "7")){
            return sql.getDoubleLine(request);
        }else if (Objects.equals(action, "8")){
            return sql.getPersonAver(request);
        }else if (Objects.equals(action, "9")){
            return sql.getYesterdayHotline(request);
        }else if (Objects.equals(action, "10")){
            return sql.getOpera(request);
        }
        return null;
   }
}
