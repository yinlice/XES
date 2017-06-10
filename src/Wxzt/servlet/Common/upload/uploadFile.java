package Wxzt.servlet.Common.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016-7-5.
 */
public class uploadFile {
    File tempPathFile;/*缓冲区域*/
    public void init(String uploadTo) throws ServletException {
        tempPathFile = new File(uploadTo);
        if (!tempPathFile.exists()) {
            tempPathFile.mkdirs();
        }
    }
    public void main(String path, int SizeMax, HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        init(path);
        // Create a factory for disk-based file items缓冲区
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置缓冲区大小，这里是4kb
        factory.setSizeThreshold(4096);
        // 设置缓冲区目录
        factory.setRepository(tempPathFile);
        // Create a new file upload handler文件上传程序
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件尺寸，这里是4MB(4*1024*1024)
        upload.setSizeMax(SizeMax);
        // 开始读取上传信息
        List fileItems = new ArrayList();
        try {
            fileItems = upload.parseRequest(req);
        } catch (FileUploadException e1) {
            e1.printStackTrace();
        }
        // 依次处理每个上传的文件
        Iterator iter = fileItems.iterator();
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
                    String name1 = setUploadName(req,resp);
                    String fileName = path + name1;
                    item.write(new File(fileName));
                    IsFile(name1,path,req,resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                NoFile();
            }
        }
    }
    /*设置生成文件名称*/
    public String setUploadName(HttpServletRequest req, HttpServletResponse resp){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss-SSS");
        return format.format(new Date()) +".xls";
    }
    /*对上传文件表单项的操作*/
    public void IsFile(String name,String path,HttpServletRequest req, HttpServletResponse resp){
        System.out.println("这是一个表单项");
    }
    /*对上传非文件表单项的操作*/
    public void NoFile(){
        System.out.println("这不是一个表单项");
    }
}
