package Wxzt.servlet.Cust;

import Wxzt.tool.IniReader;

import java.io.IOException;
import java.util.Objects;

/**
 * 客户基本属性
 */
public class custBean {
    /*客户基本属性*/
    private String custid;
    private String custName;//姓名
    private String idnum;//证件号码
    private String mobile;//注册手机
    private String telnum;//来电号码
    private String text01;//医保卡
    private String text02;//就诊卡
    private String custFrom;//用户来源
    private String custGrade;//标签
    private String birthday;//出生日期
    private String sex;//性别
    private String select01;//是否黑名单
    private String text03;//微医网站账号
    private String text04;//医联网站账号
    private String text05;//其他账号


    private String custNum;
    private String custDynamicInfo;//聊天记录
    private String dtUpdate;//更新时间
    private String dtCreate;//注册时间
    private String dtGive;//分配时间
    private String dtVisit;//回访时间
    private String custType;//客户性质
    private String isFocus;
    private String dtContact;
    private String qq;
    private String job;
    private String weixin;
    private String email;
    private String homeAddr;//网址
    private String addr;//公司地址
    private String groupid;
    private String worknumOwner;
    private String picId;
    private String picName;
    private String custNote;//备注

    /*卓达特有*/
    private String custNature;//成交类型
    private String callSituation;//工单类型
    private String underly;//推荐人

    /*特有属性，盈在线特有*/
    private String workNature;
    private String isRegister;//是否组册
    private String isTopUp;//是否充值
    private String isInvest;//是否投资
    private String dtInvest;//投资时间
    private String topUpAmount;//充值金额
    private String loan;//贷款
    private String marriage;//婚姻状况

    /*批量添加字段*/
    private String text06;
    private String text07;
    private String text08;
    private String text09;
    private String text10;
    private String text11;
    private String text12;
    private String text13;
    private String text14;
    private String text15;
    private String text16;
    private String text17;
    private String text18;
    private String text19;
    private String text20;

    private String select02;
    private String select03;
    private String select04;
    private String select05;
    private String select06;
    private String select07;
    private String select08;
    private String select09;
    private String select10;
    private String select11;
    private String select12;
    private String select13;
    private String select14;
    private String select15;




    public void setCustid(String custid){
        this.custid = custid;
    }
    public String getCustid(){
        return this.custid;
    }

    public void setCustNum(String custNum){
        this.custNum = custNum;
    }
    public String getCustNum(){
        return this.custNum;
    }

    public void setIdnum(String idnum){
        this.idnum = idnum;
    }
    public String getIdnum(){
        return this.idnum;
    }

    public void setCustDynamicInfo(String custDynamicInfo){
        this.custDynamicInfo = custDynamicInfo;
    }
    public String getCustDynamicInfo(){
        return this.custDynamicInfo;
    }

    public void setDtUpdate(String dtUpdate){
        this.dtUpdate = dtUpdate;
    }
    public String getDtUpdate(){
        return this.dtUpdate;
    }

    public void setDtCreate(String dtCreate){
        this.dtCreate = dtCreate;
    }
    public String getDtCreate(){
        return this.dtCreate;
    }

    public void setDtGive(String dtGive){
        this.dtGive = dtGive;
    }
    public String getDtGive(){
        return this.dtGive;
    }

    public void setDtVisit(String dtVisit){
        this.dtVisit = dtVisit;
    }
    public String getDtVisit(){
        return this.dtVisit;
    }

    public void setCustType(String custType){
        this.custType = custType;
    }
    public String getCustType(){
        return this.custType;
    }

    public void setCustGrade(String custGrade){
        this.custGrade = custGrade;
    }
    public String getCustGrade(){
        return this.custGrade;
    }

    public void setCustFrom(String custFrom){
        this.custFrom = custFrom;
    }
    public String getCustFrom(){
        return this.custFrom;
    }

    public void setCustName(String custName){
        this.custName = custName;
    }
    public String getCustName(){
        return this.custName;
    }

    public void setSex(String sex){
        this.sex = sex;
    }
    public String getSex(){
        return this.sex;
    }

    public void setBirthday(String birthday){
        this.birthday = birthday;
    }
    public String getBirthday(){
        return this.birthday;
    }

    public void setIsFocus(String isFocus){
        this.isFocus = isFocus;
    }
    public String getIsFocus(){
        return this.isFocus;
    }

    public void setDtContact(String dtContact){
        this.dtContact = dtContact;
    }
    public String getDtContact(){
        return this.dtContact;
    }

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

    public void setQq(String qq){
        this.qq = qq;
    }
    public String getQq(){
        return this.qq;
    }

    public void setJob(String job){
        this.job = job;
    }
    public String getJob(){
        return this.job;
    }

    public void setWeixin(String weixin){
        this.weixin = weixin;
    }
    public String getWeixin(){
        return this.weixin;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }

    public void setHomeAddr(String homeAddr){
        this.homeAddr = homeAddr;
    }
    public String getHomeAddr(){
        return this.homeAddr;
    }

    public void setAddr(String addr){
        this.addr = addr;
    }
    public String getAddr(){
        return this.addr;
    }


    public void setGroupid(String groupid){
        this.groupid = groupid;
    }
    public String getGroupid(){
        return this.groupid;
    }

    public void setWorknumOwner(String worknumOwner){
        this.worknumOwner = worknumOwner;
    }
    public String getWorknumOwner(){
        return this.worknumOwner;
    }

    public void setCustNote(String custNote){
        this.custNote = custNote;
    }
    public String getCustNote(){
        return this.custNote;
    }


    public void setIsRegister(String isRegister){
        this.isRegister = isRegister;
    }
    public String getIsRegister(){
        return this.isRegister;
    }

    public void setIsTopUp(String isTopUp){
        this.isTopUp = isTopUp;
    }
    public String getIsTopUp(){
        return this.isTopUp;
    }

    public void setIsInvest(String isInvest){
        this.isInvest = isInvest;
    }
    public String getIsInvest(){
        return this.isInvest;
    }

    public void setDtInvest(String dtInvest){
        this.dtInvest = dtInvest;
    }
    public String getDtInvest(){
        return this.dtInvest;
    }

    public void setTopUpAmount(String topUpAmount){
        this.topUpAmount = topUpAmount;
    }
    public String getTopUpAmount(){
        return this.topUpAmount;
    }

    public void setCallSituation(String callSituation){
        this.callSituation = callSituation;
    }
    public String getCallSituation(){
        return this.callSituation;
    }

    public void setUnderly(String underly){
        this.underly = underly;
    }
    public String getUnderly(){
        return this.underly;
    }

    public void setLoan(String loan){
        this.loan = loan;
    }
    public String getLoan(){
        return this.loan;
    }

    public void setMarriage(String marriage){
        this.marriage = marriage;
    }
    public String getMarriage(){
        return this.marriage;
    }

    public void setWorkNature(String workNature){
        this.workNature = workNature;
    }
    public String getWorkNature(){
        return this.workNature;
    }

    public void setPicId(String picId){
        this.picId = picId;
    }
    public String getPicId(){
        return this.picId;
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

    public void setCustNature(String custNature){
        this.custNature = custNature;
    }
    public String getCustNature(){
        return this.custNature;
    }



    public void setText01(String text01){
        this.text01 = text01;
    }
    public String getText01(){
        return this.text01;
    }
    public void setText02(String text02){
        this.text02 = text02;
    }
    public String getText02(){
        return this.text02;
    }
    public void setText03(String text03){
        this.text03 = text03;
    }
    public String getText03(){
        return this.text03;
    }
    public void setText04(String text04){
        this.text04 = text04;
    }
    public String getText04(){
        return this.text04;
    }
    public void setText05(String text05){
        this.text05 = text05;
    }
    public String getText05(){
        return this.text05;
    }
    public void setText06(String text06){
        this.text06 = text06;
    }
    public String getText06(){
        return this.text06;
    }
    public void setText07(String text07){
        this.text07 = text07;
    }
    public String getText07(){
        return this.text07;
    }
    public void setText08(String text08){
        this.text08 = text08;
    }
    public String getText08(){
        return this.text08;
    }
    public void setText09(String text09){
        this.text09 = text09;
    }
    public String getText09(){
        return this.text09;
    }
    public void setText10(String text10){
        this.text10 = text10;
    }
    public String getText10(){
        return this.text10;
    }
    public void setText11(String text11){
        this.text11 = text11;
    }
    public String getText11(){
        return this.text11;
    }
    public void setText12(String text12){
        this.text12 = text12;
    }
    public String getText12(){
        return this.text12;
    }
    public void setText13(String text13){
        this.text13 = text13;
    }
    public String getText13(){
        return this.text13;
    }
    public void setText14(String text14){
        this.text14 = text14;
    }
    public String getText14(){
        return this.text14;
    }
    public void setText15(String text15){
        this.text15 = text15;
    }
    public String getText15(){
        return this.text15;
    }
    public void setText16(String text16){
        this.text16 = text16;
    }
    public String getText16(){
        return this.text16;
    }
    public void setText17(String text17){
        this.text17 = text17;
    }
    public String getText17(){
        return this.text17;
    }
    public void setText18(String text18){
        this.text18 = text18;
    }
    public String getText18(){
        return this.text18;
    }
    public void setText19(String text19){
        this.text19 = text19;
    }
    public String getText19(){
        return this.text19;
    }
    public void setText20(String text20){
        this.text20 = text20;
    }
    public String getText20(){
        return this.text20;
    }


    public void setSelect01(String select01){
        this.select01 = select01;
    }
    public String getSelect01(){
        return this.select01;
    }
    public void setSelect02(String select02){
        this.select02 = select02;
    }
    public String getSelect02(){
        return this.select02;
    }
    public void setSelect03(String select03){
        this.select03 = select03;
    }
    public String getSelect03(){
        return this.select03;
    }
    public void setSelect04(String select04){
        this.select04 = select04;
    }
    public String getSelect04(){
        return this.select04;
    }
    public void setSelect05(String select05){
        this.select05 = select05;
    }
    public String getSelect05(){
        return this.select05;
    }
    public void setSelect06(String select06){
        this.select06 = select06;
    }
    public String getSelect06(){
        return this.select06;
    }
    public void setSelect07(String select07){
        this.select07 = select07;
    }
    public String getSelect07(){
        return this.select07;
    }
    public void setSelect08(String select08){
        this.select08 = select08;
    }
    public String getSelect08(){
        return this.select08;
    }
    public void setSelect09(String select09){
        this.select09 = select09;
    }
    public String getSelect09(){
        return this.select09;
    }
    public void setSelect10(String select10){
        this.select10 = select10;
    }
    public String getSelect10(){
        return this.select10;
    }
    public void setSelect11(String select11){
        this.select11 = select11;
    }
    public String getSelect11(){
        return this.select11;
    }
    public void setSelect12(String select12){
        this.select12 = select12;
    }
    public String getSelect12(){
        return this.select12;
    }
    public void setSelect13(String select13){
        this.select13 = select13;
    }
    public String getSelect13(){
        return this.select13;
    }
    public void setSelect14(String select14){
        this.select14 = select14;
    }
    public String getSelect14(){
        return this.select14;
    }
    public void setSelect15(String select15){
        this.select15 = select15;
    }
    public String getSelect15(){
        return this.select15;
    }

    public String toString()
    {
        return "User [Sex=" + sex + ", groupname=" + groupid + "]";
    }
}
