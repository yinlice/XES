package Wxzt.servlet.Common.toExcel;

import Wxzt.tool.JDBC;
import Wxzt.tool.JdbcForCti;
import Wxzt.tool.SimpleExcelWrite;
import jxl.write.WriteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2016/1/8.
 */
public class toExcel extends HttpServlet{
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding ("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("----------------toExcel");

        /*参数初始化*/
        String fname = "导出数据";
        String scope = request.getParameter("scope");//待查询数据
        String header = request.getParameter("scopeHeader");//表头
        String sqlTable = request.getParameter("sqlTable");//导出excel查询语句名称
        String sql = "";
        HttpSession session = request.getSession();
        sql = String.valueOf(session.getAttribute(sqlTable+"ToExcel"));
        System.out.println("导出excel语句："+sql);
        JDBC jdbc = new JDBC();
        /*数据查询*/
        if(Objects.equals(request.getParameter("forCti"), "true")){
            jdbc = new JdbcForCti();
        }
        ResultSet os;//执行SQL的ResultSet
        ArrayList<ArrayList> listData = new ArrayList<ArrayList>();//用于存储查询出的数据
        try {
            os = jdbc.executeQuery(sql);//执行查询数据操作
            try {
                while (os.next()) {
                    ArrayList data = new ArrayList();//用于存储每一行的数据
                    for(int i=0;i<scope.split(",").length;i++){
                        data.add(os.getString(scope.split(",")[i]));//ID0
                    }
                    listData.add(data);
                }
                System.out.println("ok");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            jdbc.closeConnection();//关闭数据库连接
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        /*对待传入SimpleExcelWrite,进行流操作的数据处理*/
        response.reset();//清空输出流
        OutputStream osOut = response.getOutputStream();//取得输出流
        excelBean isExcelBean = new excelBean();//导出excel的相关数据
        isExcelBean.setDataList(listData);//查询数据
        System.out.println("导出表头:"+header);
        isExcelBean.setExcelData(header.split(","));//表头
        isExcelBean.setCount(listData.size());//数据总量
        /*下面是对中文文件名的处理*/
        response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
        fname = java.net.URLEncoder.encode(fname,"UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fname + ".xls");
        response.setContentType("application/msexcel");//定义输出类型
        SimpleExcelWrite sw = new SimpleExcelWrite();
        try {
            sw.createExcel(osOut, isExcelBean);
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }
}


