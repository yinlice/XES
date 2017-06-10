package Wxzt.servlet.table.web.dictionary;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/12/29.
 */
public class updataDictMain extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        System.out.println("------------------updataDictMain");

        JDBC jdbc = new JDBC();
        String action = request.getParameter("action");//0为删除;1为更新;2为添加
        boolean sqlJDBC = false;
        if(action.equals("2")){
            Insert_Dict insert = new Insert_Dict();
            sqlJDBC = jdbc.executeUpdate(insert.updata(request));
        }else if(action.equals("1")){
            Updata_Dict up = new Updata_Dict();
            sqlJDBC = jdbc.executeUpdate(up.updata(request));
        }else if(action.equals("0")){
            Delete_Dict delete = new Delete_Dict();
            sqlJDBC = jdbc.executeUpdate(delete.updata(request));
        }
        jdbc.closeConnection();
        /**反馈数据*/
        out.println(sqlJDBC);
    }
}
