package Wxzt.servlet.background;

import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class layerRoleData extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.println("layerRoleData=======================");
        PrintWriter out = response.getWriter();

        String sql = "";//查询语句
        String DataTable = "px_CRM_JSQX";//数据表
        JDBC jdbc = new JDBC();

        //获取jQuery传递的参数
        String action = request.getParameter("action");//1为添加；2为更新;3为删除
        if(action.equals("3")){//删除
            String ID = request.getParameter("ID");
            sql = "DELETE FROM "+DataTable+" WHERE JSBH = "+ID;
        }else {
            String RoleName = request.getParameter("RoleName");
            String RoleNote = request.getParameter("RoleNote");
            if(action.equals("1")){//添加
                sql = "INSERT  into "+DataTable+" (JSMC,Note) values ('"+RoleName+"','"+RoleNote+"')";
            } else if (action.equals("2")){//更新
                String IsMgr = request.getParameter("IsMgr");
                String QXLB = request.getParameter("QXLB");
                String ID = request.getParameter("ID");
                sql = "update "+DataTable+" set JSMC='"+RoleName+"' , IsMgr='"+IsMgr+"' , QXLB='"+QXLB+"', Note='"+RoleNote+"'  where JSBH ="+ID;
                System.out.println("--更新数据语句----：：：："+sql);
            }
        }
        System.out.println("SQLstate="+sql);
        Boolean AddCom = jdbc.executeUpdate(sql);
        out.println(AddCom);
    }
}

