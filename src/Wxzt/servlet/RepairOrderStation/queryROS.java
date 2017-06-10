package Wxzt.servlet.RepairOrderStation;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-17.
 */
public class queryROS extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------queryROS");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取查询语句返回数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getRepairSQL(request, action));
        out.println(json);
    }
    /*action:0为查询工单处理过程页面*/
    public Object getRepairSQL(HttpServletRequest request,String action) {
        rosSql sql = new rosSql();
        if (Objects.equals(action, "0")) {
            return sql.rosQuery(request);
        }
        return null;
    }
}
