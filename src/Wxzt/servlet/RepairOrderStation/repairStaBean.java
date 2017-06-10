package Wxzt.servlet.RepairOrderStation;

/**
 * Created by Administrator on 2016-5-16.
 */
public class repairStaBean {
    private String gdnum;
    private String gdInfor;
    private String state;
    private String custTel;
    private String emergency;
    private String problemType;
    private String dtUpdate;
    private String updataWorknum;
    private String worknumReceive;/*收件人*/
    private String worknumRecipient;/*抄送人*/
    private String gdtheme;
    private String custname;
    private String questionsBriefly;

    public void setGdnum(String gdnum){
        this.gdnum = gdnum;
    }
    public String getGdnum(){
        return this.gdnum;
    }

    public void setGdInfor(String gdInfor){
        this.gdInfor = gdInfor;
    }
    public String getGdInfor(){
        return this.gdInfor;
    }

    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }

    public void setCustTel(String custTel){
        this.custTel = custTel;
    }
    public String getCustTel(){
        return this.custTel;
    }

    public void setEmergency(String emergency){
        this.emergency = emergency;
    }
    public String getEmergency(){
        return this.emergency;
    }

    public void setProblemType(String problemType){
        this.problemType = problemType;
    }
    public String getProblemType(){
        return this.problemType;
    }

    public void setDtUpdate(String dtUpdate){
        this.dtUpdate = dtUpdate;
    }
    public String getDtUpdate(){
        return this.dtUpdate;
    }

    public void setUpdataWorknum(String updataWorknum){
        this.updataWorknum = updataWorknum;
    }
    public String getUpdataWorknum(){
        return this.updataWorknum;
    }

    public void setWorknumReceive(String worknumReceive){
        this.worknumReceive = worknumReceive;
    }
    public String getWorknumReceive(){
        return this.worknumReceive;
    }

    public void setWorknumRecipient(String worknumRecipient){
        this.worknumRecipient = worknumRecipient;
    }
    public String getWorknumRecipient(){
        return this.worknumRecipient;
    }

    public void setGdtheme(String gdtheme){
        this.gdtheme = gdtheme;
    }
    public String getGdtheme(){
        return this.gdtheme;
    }

    public void setCustname(String custname){
        this.custname = custname;
    }
    public String getCustname(){
        return this.custname;
    }

    public void setQuestionsBriefly(String questionsBriefly){
        this.questionsBriefly = questionsBriefly;
    }
    public String getQuestionsBriefly(){
        return this.questionsBriefly;
    }
}
