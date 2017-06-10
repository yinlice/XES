package Wxzt.servlet.table.web;

import Wxzt.tool.ReadExcel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
/**
 * Created by Administrator on 2015/11/19.
 */
public class test extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //缓冲区域
    File tempPathFile;
    //默认路径
    String uploadTo = "D:\\";
    //格式化日期
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        //取得服务器真实路径
        uploadTo = req.getSession().getServletContext().getRealPath("\\") + "upload\\";
        // Create a factory for disk-based file items缓冲区
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置缓冲区大小，这里是4kb
        factory.setSizeThreshold(4096);
        // 设置缓冲区目录
        factory.setRepository(tempPathFile);
        // Create a new file upload handler文件上传程序
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件尺寸，这里是4MB
        upload.setSizeMax(4 * 1024 * 1024);
        // 开始读取上传信息
        List fileItems = new ArrayList();
        try {
            fileItems = upload.parseRequest(req);
        } catch (FileUploadException e1) {
            e1.printStackTrace();
        }
        // 依次处理每个上传的文件
        Iterator iter = fileItems.iterator();
        System.out.println("fileItems的大小是" + fileItems.size());
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            // 忽略其他不是文件域的所有表单信息
            System.out.println("正在处理" + item.getFieldName());
            if (!item.isFormField()) {
                String name = item.getName();
                long size = item.getSize();
                if ((name == null || name.equals("")) && size == 0)
                    continue;
                try {
                    String fileName = uploadTo + format.format(new Date()) +".xls";
                    item.write(new File(fileName));
                    //调用ReadExcel类进行读出excel
//                    ReadExcel.readExcel(fileName, resp.getWriter());
                    System.out.println(fileName + "\t\t" + size);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // 这里添加对不是上传文件表单项的处理
                System.out.println("这是一个表单项");
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void init() throws ServletException {
        tempPathFile = new File("D:\\");
        if (!tempPathFile.exists()) {
            tempPathFile.mkdirs();
        }
    }

}

