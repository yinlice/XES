package Wxzt.servlet.QualityTest;

import Wxzt.servlet.Log.LogSql;
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
 * Created by yin on 2017/4/28.
 */
public class QueryQualityTest extends HttpServlet {
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
     * action:0为根据呼入方向和组别来查询项目
     * 1:根据项目的id 获取类别
     * */
    public Object getCustSQL(HttpServletRequest request,String action) {
        QualityTestSql sql = new QualityTestSql();
        if (Objects.equals(action, "0")) {
          return sql.getProjectByModelId(request);
        } else if (Objects.equals(action, "1")) {
            return sql.getTypeBeanByProjectId(request);
        }
        return null;
    }

}
