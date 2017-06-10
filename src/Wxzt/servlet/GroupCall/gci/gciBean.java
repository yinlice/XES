package Wxzt.servlet.GroupCall.gci;

/**
 * Created by Administrator on 2016-10-17.
 */
public class gciBean {
    private String telid;
    private String telnum;
    private String custName;
    private String callRet;
    private String dtCall;

    public void setTelid(String telid){
        this.telid = telid;
    }
    public String getTelid(){
        return this.telid;
    }

    public void setTelnum(String telnum){
        this.telnum = telnum;
    }
    public String getTelnum(){
        return this.telnum;
    }

    public void setCustName(String custName){
        this.custName = custName;
    }
    public String getCustName(){
        return this.custName;
    }

    public void setCallRet(String callRet){
        this.callRet = callRet;
    }
    public String getCallRet(){
        return this.callRet;
    }

    public void setDtCall(String dtCall){
        this.dtCall = dtCall;
    }
    public String getDtCall(){
        return this.dtCall;
    }
}
