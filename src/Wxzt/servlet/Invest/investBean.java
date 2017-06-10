package Wxzt.servlet.Invest;

/**
 * Created by Administrator on 2016-5-11.
 */
public class investBean {
    private String id;
    private String custName;
    private String investAmount;
    private String investTime;
    private String worknum;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    public void setCustName(String custName){
        this.custName = custName;
    }
    public String getCustName(){
        return this.custName;
    }

    public void setInvestAmount(String investAmount){
        this.investAmount = investAmount;
    }
    public String getInvestAmount(){
        return this.investAmount;
    }

    public void setInvestTime(String investTime){
        this.investTime = investTime;
    }
    public String getInvestTime(){
        return this.investTime;
    }

    public void setWorknum(String worknum){
        this.worknum = worknum;
    }
    public String getWorknum(){
        return this.worknum;
    }
}
