package Wxzt.servlet.Visit;

/**
 * Created by Administrator on 2016-5-11.
 */
public class visitBean {
    private String dtVisit;
    private String custName;
    private String mobile;
    private String worknum;

    public void setDtVisit(String dtVisit){
        this.dtVisit = dtVisit.replace("T"," ");
    }
    public String getDtVisit(){
        return this.dtVisit;
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

    public void setWorknum(String worknum){
        this.worknum = worknum;
    }
    public String getWorknum(){
        return this.worknum;
    }

}
