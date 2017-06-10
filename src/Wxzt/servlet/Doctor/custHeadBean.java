package Wxzt.servlet.Doctor;

/**
 * Created by Administrator on 2016-8-16.
 */
public class custHeadBean {
    private String hotLine;
    private String callInLine;
    private String dest;
    private String greetings;
    private String note;

    public void setHotLine(String hotLine){
        this.hotLine = hotLine;
    }
    public String getHotLine(){
        return this.hotLine;
    }

    public void setCallInLine(String callInLine){
        this.callInLine = callInLine;
    }
    public String getCallInLine(){
        return this.callInLine;
    }

    public void setDest(String dest){
        this.dest = dest;
    }
    public String getDest(){
        return this.dest;
    }

    public void setGreetings(String greetings){
        this.greetings = greetings;
    }
    public String getGreetings(){
        return this.greetings;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }

}
