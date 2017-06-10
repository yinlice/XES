package Wxzt.servlet.Login;

/**
 * Created by Administrator on 2016-4-25.
 */
public class MenuUserCallBean {
    private String callDate;//呼叫时间
    private String callInNum;//呼入量
    private String callInSucc;//呼入成功量
    private String callInAverage;//呼入平均量
    private String callOutNum;//外呼量
    private String callOutSucc;//外呼成功量
    private String callOutAverage;//外呼平均量

    public String getCallDate() {
        return callDate;
    }
    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }
    public String getCallInNum() {
        return callInNum;
    }
    public void setCallInNum(String callInNum) {
        this.callInNum = callInNum;
    }

    public String getCallInSucc() {
        return callInSucc;
    }
    public void setCallInSucc(String callInSucc) {
        this.callInSucc = callInSucc;
    }

    public String getCallInAverage() {
        return callInAverage;
    }
    public void setCallInAverage(String callInAverage) {
        this.callInAverage = callInAverage;
    }
    public String getCallOutNum() {
        return callOutNum;
    }
    public void setCallOutNum(String callOutNum) {
        this.callOutNum = callOutNum;
    }

    public String getCallOutSucc() {
        return callOutSucc;
    }
    public void setCallOutSucc(String callOutSucc) {
        this.callOutSucc = callOutSucc;
    }

    public String getCallOutAverage() {
        return callOutAverage;
    }
    public void setCallOutAverage(String callOutAverage) {
        this.callOutAverage = callOutAverage;
    }
}
