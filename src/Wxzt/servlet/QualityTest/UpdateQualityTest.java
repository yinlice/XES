package Wxzt.servlet.QualityTest;

import Wxzt.servlet.Log.LogSql;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by yin on 2017/4/28.
 */
public class UpdateQualityTest extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getCustSQL(request,action));
    }

    /**
     * 0:添加项目
     * 1:更新项目
     * 2:删除项目
     * 3:添加类别
     * 4:更新类别
     * 5:删除类别
     * @param request
     * @param action
     * @return
     */
    public boolean getCustSQL(HttpServletRequest request,String action){
        QualityTestSql sql = new QualityTestSql();
        if(Objects.equals(action, "0")){
            return sql.addProject(request);
        }else if(Objects.equals(action, "1")) {
            return sql.modifyProject(request);
        }else if(Objects.equals(action, "2")) {
            return sql.deleteProject(request);
        }else if(Objects.equals(action, "3")) {
            return sql.addType(request);
        }else if(Objects.equals(action, "4")) {
            return sql.ModifyType(request);
        }else if(Objects.equals(action, "5")) {
            return sql.deleteType(request);
        }
        return false;
    }
}
