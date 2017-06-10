package Wxzt.servlet.Import;

/**
 * Created by Administrator on 2016-5-11.
 */
public class importBean {
    private String custid;
    private String importID;
    private String custName;
    private String mobile;
    private String addr;
    private String custNature;
    private String dtCreate;
    private String dtUpdate;
    private String dtGive;

    public void setCustid(String custid){
        this.custid = custid;
    }
    public String getCustid(){
        return this.custid;
    }

    public void setImportID(String importID){
        this.importID = importID;
    }
    public String getImportID(){
        return this.importID;
    }

    public void setCustName(String custName){
        this.custName = custName;
    }
    public String getCustName(){
        return this.custName;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getMobile(){
        return this.mobile;
    }

    public void setAddr(String addr){
        this.addr = addr;
    }
    public String getAddr(){
        return this.addr;
    }

    public void setCustNature(String custNature){
        this.custNature = custNature;
    }
    public String getCustNature(){
        return this.custNature;
    }

    public void setDtCreate(String dtCreate){
        this.dtCreate = dtCreate;
    }
    public String getDtCreate(){
        return this.dtCreate;
    }

    public void setDtUpdate(String dtUpdate){
        this.dtUpdate = dtUpdate;
    }
    public String getDtUpdate(){
        return this.dtUpdate;
    }

    public void setDtGive(String dtGive){
        this.dtGive = dtGive;
    }
    public String getDtGive(){
        return this.dtGive;
    }
}
