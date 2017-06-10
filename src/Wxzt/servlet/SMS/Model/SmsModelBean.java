package Wxzt.servlet.SMS.Model;

/**
 * Created by Administrator on 2016-9-27.
 */
public class SmsModelBean {
    private String id;
    private String type;
    private String name;
    private String modelContent;
    private String note;

    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getModelContent(){
        return this.modelContent;
    }
    public void setModelContent(String modelContent){
        this.modelContent = modelContent;
    }

    public String getNote(){
        return this.note;
    }
    public void setNote(String note){
        this.note = note;
    }
}
