package Wxzt.servlet.Cdr;

import Wxzt.tool.JdbcForCti;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
* Created by Pengxi on 2015/9/5.
*/
public class updataCdr extends HttpServlet{
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("------------------updataCust");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        //获取更新语句返回值
        out.println(getCdrSQL(request, action));
    }

    /*action:0为录音页面更新，1为录音质检更新，2为回访更新*/
    public boolean getCdrSQL(HttpServletRequest request,String action){
        cdrTableSql sql = new cdrTableSql();
        if(Objects.equals(action, "0")){
            return sql.updataCdr(request);
        }else if(Objects.equals(action, "1")) {
            return sql.updataCdrQuality(request);
        }else if(Objects.equals(action, "2")){
            return sql.updataCdrNoCall(request);
        }
        return false;
    }
    public void doPost1(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding ("UTF-8");

        System.out.println("record-------------");
        PrintWriter out = response.getWriter();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = format.format(new Date());
        //获取form传递的参数
        String ID = request.getParameter("ID");
        String Remark = request.getParameter("Remark");
        int IsRead = Integer.parseInt(request.getParameter("IsRead"));
        /*关于语音质检的参数*/
        String qualityNum = request.getParameter("qualityNum");
        String qualityNote = request.getParameter("qualityNote");
        String qualityWorknum = request.getParameter("qualityWorknum");

        String sql = "update dddCdr set Remark='"+Remark+"',IsRead='"+IsRead+"',qualityNum='"+qualityNum+"',\n" +
                "qualityNote='"+qualityNote+"',qualityTime='"+newTime+"',qualityWorknum='"+qualityWorknum+"'  where ID="+ID;
        System.out.println("语音信箱更新语句："+sql);
        JdbcForCti jdbc = new JdbcForCti();

        Boolean AddCom = jdbc.executeUpdate(sql);
        out.print(AddCom);
    }
}
