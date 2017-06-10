package Wxzt.servlet.Report.javabean;

/**
 * Created by Administrator on 2016-9-12.
 */
public class SipnumBean {
    private String countCallIn;
    private String countCallInFail;
    private String countCallOut;
    private String countCallOutFail;
    private String callinTimeLenSpeak;
    private String calloutTimeLenSpeak;
    private String countCallInEffective;
    private String countCallOutEffective;

    public String getCountCallIn() {
        return countCallIn;
    }
    public void setCountCallIn(String countCallIn) {
        this.countCallIn = countCallIn;
    }

    public String getCountCallInFail() {
        return countCallInFail;
    }
    public void setCountCallInFail(String countCallInFail) {
        this.countCallInFail = countCallInFail;
    }

    public String getCountCallInEffective() {
        return countCallInEffective;
    }
    public void setCountCallInEffective(String countCallInEffective) {
        this.countCallInEffective = countCallInEffective;
    }

    public String getCalloutTimeLenSpeak() {
        return calloutTimeLenSpeak;
    }
    public void setCalloutTimeLenSpeak(String calloutTimeLenSpeak) {
        this.calloutTimeLenSpeak = calloutTimeLenSpeak;
    }

    public String getCallinTimeLenSpeak() {
        return callinTimeLenSpeak;
    }
    public void setCallinTimeLenSpeak(String callinTimeLenSpeak) {
        this.callinTimeLenSpeak = callinTimeLenSpeak;
    }

    public String getCountCallOut() {
        return countCallOut;
    }
    public void setCountCallOut(String countCallOut) {
        this.countCallOut = countCallOut;
    }

    public String getCountCallOutFail() {
        return countCallOutFail;
    }
    public void setCountCallOutFail(String countCallOutFail) {
        this.countCallOutFail = countCallOutFail;
    }

    public String getCountCallOutEffective() {
        return countCallOutEffective;
    }
    public void setCountCallOutEffective(String countCallOutEffective) {
        this.countCallOutEffective = countCallOutEffective;
    }
}
