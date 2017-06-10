package Wxzt.servlet.dynamic;

import Wxzt.tool.IniReader;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-10.
 */
public class dynamicBean {
    private String custDynamicInfo;
    private String dtCreate;
    private String workname;
    private String mobile;
    private String custnum;
    private String custname;
    private String WorknumCreate;
    private String picName;
    private String id;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    public void setPicName(String picName) throws IOException {
        IniReader reader = new IniReader("");
        String imgPath = reader.getValue("Img","url");
        if(picName!=null&& !Objects.equals(picName, "null")){
            picName = imgPath+"/"+picName;
        }
        this.picName = picName;
    }
    public String getPicName(){
        return this.picName;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getMobile(){
        return this.mobile;
    }

    public void setCustDynamicInfo(String custDynamicInfo){
        this.custDynamicInfo = custDynamicInfo;
    }
    public String getCustDynamicInfo(){
        return this.custDynamicInfo;
    }

    public void setDtCreate(String dtCreate){
        this.dtCreate = dtCreate;
    }
    public String getDtCreate(){
        return this.dtCreate;
    }

    public void setWorkname(String workname){
        this.workname = workname;
    }
    public String getWorkname(){
        return this.workname;
    }

    public void setWorknumCreate(String worknumCreate){
        this.WorknumCreate = worknumCreate;
    }
    public String getWorknumCreate(){
        return this.WorknumCreate;
    }

    public void setCustnum(String custnum){
        this.custnum = custnum;
    }
    public String getCustnum(){
        return this.custnum;
    }

    public void setCustname(String custname){
        this.custname = custname;
    }
    public String getCustname(){
        return this.custname;
    }

}
