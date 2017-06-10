package Wxzt.servlet.Common.table;

/**
 * Created by Administrator on 2016-7-8.
 */
public class tableHeadBean {
    private String id;
    private String tableName;
    private String headName;
    private String headMean;
    private String headWidth;
    private String headOrder;
    private String headType;
    private String isCommon;
    private String dataDav;
    private String isShow;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    public void setTableName(String tableName){
        this.tableName = tableName;
    }
    public String getTableName(){
        return this.tableName;
    }

    public void setHeadName(String headName){
        this.headName = headName;
    }
    public String getHeadName(){
        return this.headName;
    }

    public void setHeadMean(String headMean){
        this.headMean = headMean;
    }
    public String getHeadMean(){
        return this.headMean;
    }

    public void setHeadWidth(String headWidth){
        this.headWidth = headWidth;
    }
    public String getHeadWidth(){
        return this.headWidth;
    }

    public void setHeadOrder(String headOrder){
        this.headOrder = headOrder;
    }
    public String getHeadOrder(){
        return this.headOrder;
    }

    public void setHeadType(String headType){
        this.headType = headType;
    }
    public String getHeadType(){
        return this.headType;
    }

    public void setIsCommon(String isCommon){
        this.isCommon = isCommon;
    }
    public String getIsCommon(){
        return this.isCommon;
    }

    public void setDataDav(String dataDav){
        this.dataDav = dataDav;
    }
    public String getDataDav(){
        return this.dataDav;
    }

    public void setIsShow(String isShow){
        this.isShow = isShow;
    }
    public String getIsShow(){
        return this.isShow;
    }
}
