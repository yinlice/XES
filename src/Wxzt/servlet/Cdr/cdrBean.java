package Wxzt.servlet.Cdr;

/**
 * Created by Administrator on 2016-5-10.
 */
public class cdrBean {
    private String dtCreate;
    private String dthangup;
    private String recfilename;
    private String sipnum;
    private String telnum;
    private String callret;
    private String direction;
    private String worknum;
    private String groupid;

    private String id;
    private String cdrId;
    private String timelenSpeak;
    private String TimelenTotalCall;
    private String hotLine;
    private String ticketType;
    private String remark;
    private String isRead;
    private String scoreSatis;
    private String qualityNum;//语音质检评分
    private String qualityNote;//语音质检评价
    private String qualityTime;//语音质检时间
    private String qualityWorknum;//语音质检员

    public void setCallret(String callret){
        this.callret = callret;
    }
    public String getCallret(){
        return this.callret;
    }

    public void setDthangup(String dthangup){
        this.dthangup = dthangup;
    }
    public String getDthangup(){
        return this.dthangup;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    public void setCdrId(String cdrId){
        this.cdrId = cdrId;
    }
    public String getCdrId(){
        return this.cdrId;
    }

    public void setTimelenSpeak(String timelenSpeak){
        this.timelenSpeak = timelenSpeak;
    }
    public String getTimelenSpeak(){
        return this.timelenSpeak;
    }

    public void setTimelenTotalCall(String callret){
        this.TimelenTotalCall = TimelenTotalCall;
    }
    public String getTimelenTotalCall(){
        return this.TimelenTotalCall;
    }

    public void setHotLine(String hotLine){
        this.hotLine = hotLine;
    }
    public String getHotLine(){
        return this.hotLine;
    }

    public void setTicketType(String ticketType){
        this.ticketType = ticketType;
    }
    public String getTicketType(){
        return this.ticketType;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }
    public String getRemark(){
        return this.remark;
    }

    public void setIsRead(String isRead){
        this.isRead = isRead;
    }
    public String getIsRead(){
        return this.isRead;
    }

    public void setScoreSatis(String scoreSatis){
        this.scoreSatis = scoreSatis;
    }
    public String getScoreSatis(){
        return this.scoreSatis;
    }

    public void setDtCreate(String dtCreate){
        this.dtCreate = dtCreate;
    }
    public String getDtCreate(){
        return this.dtCreate;
    }

    public void setRecfilename(String recfilename){
        String[] recfile = recfilename.split("/");
        int recfileLength = recfile.length;
        if(recfileLength>1){
            recfilename = recfile[recfileLength-2]+"/"+recfile[recfileLength-1];
        }
        this.recfilename = recfilename;
    }
    public String getRecfilename(){
        return this.recfilename;
    }

    public void setSipnum(String sipnum){
        this.sipnum = sipnum;
    }
    public String getSipnum(){
        return this.sipnum;
    }

    public void setTelnum(String telnum){
        this.telnum = telnum;
    }
    public String getTelnum(){
        return this.telnum;
    }

    public void setDirection(String direction){
        this.direction = direction;
    }
    public String getDirection(){
        return this.direction;
    }

    public void setWorknum(String worknum){
        this.worknum = worknum;
    }
    public String getWorknum(){
        return this.worknum;
    }

    public void setGroupid(String groupid){
        this.groupid = groupid;
    }
    public String getGroupid(){
        return this.groupid;
    }

    public void setQualityNum(String qualityNum){
        this.qualityNum = qualityNum;
    }
    public String getQualityNum(){
        return this.qualityNum;
    }

    public void setQualityNote(String qualityNote){
        this.qualityNote = qualityNote;
    }
    public String getQualityNote(){
        return this.qualityNote;
    }

    public void setQualityTime(String qualityTime){
        this.qualityTime = qualityTime;
    }
    public String getQualityTime(){
        return this.qualityTime;
    }

    public void setQualityWorknum(String qualityWorknum){
        this.qualityWorknum = qualityWorknum;
    }
    public String getQualityWorknum(){
        return this.qualityWorknum;
    }
}
