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
public class layerCompanyUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");
        System.out.println("-------layerCompanyUser");

        String sql = "";
        JDBC jdbc = new JDBC();
        PrintWriter out = response.getWriter();
        //获取form传递的参数
        int ID;//ID;
        String action = request.getParameter("action");//1为添加；2为更新;3为删除
        if(action.equals("3")){
            ID = Integer.parseInt(request.getParameter("id"));//ID
            sql = "DELETE FROM dddWork WHERE workID = "+ID;
        }else {
            String YGXM = request.getParameter("YGXM");//用户姓名
            String GH = request.getParameter("GH");//工号
            String FJH = request.getParameter("FJH");//分机号
            int IsWatchInt = Integer.parseInt(request.getParameter("IsWatch"));//是否监控
            String note = request.getParameter("note");//备注
            String Mobile1 = request.getParameter("Mobile1");//手机1
            String Mobile2 = request.getParameter("Mobile2");//手机2
            String Email = request.getParameter("Email");//Email
            String PassNumber = request.getParameter("PassNumber");//透传号码
            int TimeLenAutoRecovery = Integer.parseInt(request.getParameter("TimeLenAutoRecovery"));//自动恢复时长
            int groupNum = Integer.parseInt(request.getParameter("groupName"));//部门
            int JSBH = Integer.parseInt(request.getParameter("roleName"));//角色
            if(action.equals("1")){
                sql = "INSERT  into dddWork (workname,worknum,sipnum,Groupid,roleid,IsWatch,note,Mobile1,Mobile2,Email,PassNumber,TimeLenAutoRecovery) values ('"+YGXM+"','"+GH+"','"+FJH+"',"+groupNum+","+JSBH+","+IsWatchInt+",'"+note+"','"+Mobile1+"','"+Mobile2+"','"+Email+"','"+PassNumber+"',"+TimeLenAutoRecovery+")";
            }else if(action.equals("2")){
                ID = Integer.parseInt(request.getParameter("id"));//ID
                sql = "update dddWork set workname='"+YGXM+"' ,  worknum='"+GH+"' , sipnum='"+FJH+"' , Groupid="+groupNum+" , roleid="+JSBH+" , IsWatch=" + IsWatchInt+" ,note='"+note+"' ,Mobile1='"+Mobile1+"',Mobile2='"+Mobile2+"',Email='"+Email+"',PassNumber='"+PassNumber+"',TimeLenAutoRecovery="+TimeLenAutoRecovery+" where workid="+ID;
            }
        }
        System.out.println("更新坐席信息==="+sql);
        boolean result = jdbc.executeUpdate(sql);
        jdbc.closeConnection();
        out.print(result);

    }
}
