package Wxzt.servlet.background;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/9/15.
 */
public class layerMenuData extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        System.out.println("------------=============");
        PrintWriter out = response.getWriter();
        String sql = "";
        JDBC jdbc = new JDBC();
        //获取form传递的参数
        String action = request.getParameter("action");//1为添加；2为编辑；3为删除
        String MenuID = request.getParameter("MenuID");//
        System.out.println("------------============="+action);
        if(action.equals("3")){
            System.out.println("------------=============");
            sql = "DELETE FROM px_CRM_Menu WHERE MenuID = '"+MenuID+"'";
            boolean result = jdbc.executeUpdate(sql);
            out.println(result);
        }else {
            String ParentID = request.getParameter("parentMenu");//
            String MenuName = request.getParameter("MenuName");//
            String URL = request.getParameter("URL");
            int OrderID = Integer.parseInt(request.getParameter("MenuOrder"));
            String Note = request.getParameter("Note");
            if(action.equals("1")){
                sql = "INSERT  into px_CRM_Menu (MenuID,ParentID,MenuName,URL,OrderID,Note) values ('"+MenuID+"','"+ParentID+"','"+MenuName+"','"+URL+"','"+OrderID+"','"+Note+"')";
            }else if(action.equals("2")){
                sql = "update px_CRM_Menu set MenuID='"+MenuID+"' , ParentID='"+ParentID+"', MenuName='"+MenuName+"' , URL='"+URL+"', OrderID="+OrderID+", Note='"+Note+"'  where MenuID ='"+MenuID+"'";
            }
        }
        System.out.println("SQLstate==="+sql);
        boolean result = jdbc.executeUpdate(sql);
        out.print(result);
        jdbc.closeConnection();

    }
}
