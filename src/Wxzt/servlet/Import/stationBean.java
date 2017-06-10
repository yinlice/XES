package Wxzt.servlet.Import;

import java.util.Objects;

/**
 * Created by Administrator on 2016-5-11.
 */
public class stationBean {
    private String id;
    private String importID;
    private String creatWorker;
    private String creatTime;
    private String totalNum;
    private String custOwner;
    private String remainNum;
    private String isShow;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    public void setImportID(String importID){
        this.importID = importID;
    }
    public String getImportID(){
        return this.importID;
    }

    public void setCreatWorker(String creatWorker){
        this.creatWorker = creatWorker;
    }
    public String getCreatWorker(){
        return this.creatWorker;
    }

    public void setCreatTime(String creatTime){
        this.creatTime = creatTime;
    }
    public String getCreatTime(){
        return this.creatTime;
    }

    public void setTotalNum(String totalNum){
        this.totalNum = totalNum;
    }
    public String getTotalNum(){
        return this.totalNum;
    }

    public void setCustOwner(String custOwner){
        this.custOwner = custOwner;
    }
    public String getCustOwner(){
        return this.custOwner;
    }

    public void setRemainNum(String remainNum){
        if(remainNum==null|| Objects.equals(remainNum, "null")){
            remainNum = "0";
        }
        this.remainNum = remainNum;
    }
    public String getRemainNum(){
        return this.remainNum;
    }

    public void setIsShow(String isShow){
        this.isShow = isShow;
    }
    public String getIsShow(){
        return this.isShow;
    }
}
