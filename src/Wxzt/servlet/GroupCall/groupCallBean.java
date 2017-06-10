package Wxzt.servlet.GroupCall;

/**
 * Created by Administrator on 2016-10-11.
 */
public class groupCallBean {
    private String taskid;
    private String taskName;
    private String taskType;
    private String status;
    private String telCount;
    private String groupid;
    private String groupNum;
    private String groupName;
    private String luaName;
    private String outShowTel;
    private String callRate;
    private String dtCreate;
    private String worknumCreate;
    private String remart;

    public void setTaskType(String taskType){
        this.taskType = taskType;
    }
    public String getTaskType(){
        return this.taskType;
    }
    public void setTaskid(String taskid){
        this.taskid = taskid;
    }
    public String getTaskid(){
        return this.taskid;
    }
    public void setTaskName(String taskName){
        this.taskName = taskName;
    }
    public String getTaskName(){
        return this.taskName;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setTelCount(String telCount){
        this.telCount = telCount;
    }
    public String getTelCount(){
        return this.telCount;
    }
    public void setGroupid(String groupid){
        this.groupid = groupid;
    }
    public String getGroupid(){
        return this.groupid;
    }
    public void setGroupNum(String groupNum){
        this.groupNum = groupNum;
    }
    public String getGroupNum(){
        return this.groupNum;
    }
    public void setGroupName(String groupName){
        this.groupName = groupName;
    }
    public String getGroupName(){
        return this.groupName;
    }
    public void setLuaName(String luaName){
        this.luaName = luaName;
    }
    public String getLuaName(){
        return this.luaName;
    }
    public void setOutShowTel(String outShowTel){
        this.outShowTel = outShowTel;
    }
    public String getOutShowTel(){
        return this.outShowTel;
    }
    public void setCallRate(String callRate){
        this.callRate = callRate;
    }
    public String getCallRate(){
        return this.callRate;
    }
    public void setDtCreate(String dtCreate){
        this.dtCreate = dtCreate;
    }
    public String getDtCreate(){
        return this.dtCreate;
    }
    public void setWorknumCreate(String worknumCreate){
        this.worknumCreate = worknumCreate;
    }
    public String getWorknumCreate(){
        return this.worknumCreate;
    }
    public void setRemart(String remart){
        this.remart = remart;
    }
    public String getRemart(){
        return this.remart;
    }
}
