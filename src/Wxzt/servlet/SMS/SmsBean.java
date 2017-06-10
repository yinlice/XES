package Wxzt.servlet.SMS;

/**
 * Created by Administrator on 2016-4-28.
 */
public class SmsBean {
    private String nID;
    private String smsContent;//短信信息
    private String mobile;//发送号码
    private String worknum;//发送人
    private String custnum;//客户编号
    private String dt;
    private String mTreport;
    private String mTdt;
    private String mTid;
    private String readFlag;

    public String getSmsContent(){
        return this.smsContent;
    }
    public void setSmsContent(String smsContent){
        this.smsContent = smsContent;
    }

    public String getMobile(){
        return this.mobile;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getWorknum(){
        return this.worknum;
    }
    public void setWorknum(String worknum){
        this.worknum = worknum;
    }

    public String getCustnum(){
        return this.custnum;
    }
    public void setCustnum(String custnum){
        this.custnum = custnum;
    }

    public String getDt(){
        return this.dt;
    }
    public void setDt(String dt){
        this.dt = dt;
    }

    public String getnID(){
        return this.nID;
    }
    public void setnID(String nID){
        this.nID = nID;
    }

    public String getmTreport(){
        return this.mTreport;
    }
    public void setmTreport(String mTreport){
        this.mTreport = mTreport;
    }

    public String getmTdt(){
        return this.mTdt;
    }
    public void setmTdt(String mTdt){
        this.mTdt = mTdt;
    }

    public String getmTid(){
        return this.mTid;
    }
    public void setmTid(String mTid){
        this.mTid = mTid;
    }

    public String getReadFlag(){
        return this.readFlag;
    }
    public void setReadFlag(String readFlag){
        this.readFlag = readFlag;
    }
}
