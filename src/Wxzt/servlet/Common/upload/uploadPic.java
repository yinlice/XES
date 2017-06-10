package Wxzt.servlet.Common.upload;

import Wxzt.tool.JDBC;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016-7-5.
 */
public class uploadPic extends uploadFile{
    List<String> fileIds = new ArrayList<String>();//保存图片的id的数组
    /*设置生成文件名称*/
    public String setUploadName(HttpServletRequest req, HttpServletResponse resp){
        String worknum = req.getParameter("worknum");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return worknum+format.format(new Date()) +".jpg";
    }
    /*对上传文件表单项的操作*/
    public void IsFile(String name, String path,HttpServletRequest req, HttpServletResponse resp){
        /*添加到图片表*/
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        System.out.println(uuid.toString().replace("-",""));
        String picId = uuid.toString().replace("-","");
        String sql = "insert into dddPic (picId,picName,picPath) values ('"+picId+"','"+name+"','"+path+"')";
        System.out.println("添加图片语句："+sql);
        boolean s = false;
        try {
            JDBC jdbc = new JDBC();
            s = jdbc.executeUpdate(sql);
            jdbc.closeConnection();
            if(s){
                fileIds.add(picId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public List<String> getFileIds(){
        return this.fileIds;
    }

}
