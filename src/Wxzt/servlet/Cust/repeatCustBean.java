package Wxzt.servlet.Cust;

/**
 * 重复电话的相关信息
 * Created by Administrator on 2016-8-18.
 */
public class repeatCustBean {
    public String mobile;
    public String telnum;
    public String custnum;

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getMobile(){
        return this.mobile;
    }

    public void setTelnum(String telnum){
        this.telnum = telnum;
    }
    public String getTelnum(){
        return this.telnum;
    }

    public void setCustnum(String custnum){
        this.custnum = custnum;
    }
    public String getCustnum(){
        return this.custnum;
    }
}
