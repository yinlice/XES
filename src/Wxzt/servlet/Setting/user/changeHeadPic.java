package Wxzt.servlet.Setting.user;

import Wxzt.servlet.Common.upload.uploadPic;
import Wxzt.tool.IniReader;
import Wxzt.tool.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016-7-5.
 */
public class changeHeadPic extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("------------changeHeadPic");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String worknum = req.getParameter("worknum");
        //取得服务器真实路径
        File f = new File(this.getClass().getResource("/").getPath());
        String urlINI = f+"\\conf\\sysconf.ini";
        IniReader reader = new IniReader(urlINI);
        String uploadTo = reader.getValue("upload", "url_pic");
        /*上传图片*/
        uploadPic pic = new uploadPic();
        pic.main(uploadTo,3*1024*1024,req,resp);
        /*更新头像*/
        String picId = pic.getFileIds().get(0);
        String sql = "update dddWork set picid = '"+picId+"' where worknum = '"+worknum+"'";
        System.out.println("更新头像语句："+sql);
        boolean s = false;
        try {
            JDBC jdbc = new JDBC();
            s = jdbc.executeUpdate(sql);
            jdbc.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = resp.getWriter();
        out.println(s);
    }
}
