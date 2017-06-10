package Wxzt.servlet.CustHead;

/**
 * Created by Administrator on 2016-11-10.
 */
public class custHeadBean_tp {
    /*基本属性*/
    private String id;
    private String hotLine;
    private String callInLine;
    private String dest;
    private String greetings;
    private String note;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

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
