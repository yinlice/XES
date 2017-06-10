package Wxzt.servlet.table.web.knowledge;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Updata_KB_Main extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println("------------------Updata_KB_Main");

        JDBC jdbc = new JDBC();
        String action = request.getParameter("action");//0为删除;1为更新;2为添加
        boolean sqlJDBC = false;
        if(action.equals("2")){
            Insert_KB insert = new Insert_KB();
            System.out.println(insert.updata(request));
            sqlJDBC = jdbc.executeUpdate(insert.updata(request));
        }else if(action.equals("1")){
            Updata_KB up = new Updata_KB();
            sqlJDBC = jdbc.executeUpdate(up.updata(request));
        }else if(action.equals("0")){
            Delete_KB delete = new Delete_KB();
            sqlJDBC = jdbc.executeUpdate(delete.updata(request));
        }
        jdbc.closeConnection();
        /**反馈数据*/
        out.println(sqlJDBC);
    }
}



