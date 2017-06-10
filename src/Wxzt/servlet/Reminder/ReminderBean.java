package Wxzt.servlet.Reminder;

/**
 * Reminder 实体类
 */
public class ReminderBean {
    private String id;
    private String worknum;//工作组
    private String title;//标题
    private String isRead;//是否可读
    private String openURL;//打开路径
    private String createTime;//创建时间
    private String readTime;//阅读时间
    private String groupid;//工作组ID
    private String type;//类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorknum() {
        return worknum;
    }

    public void setWorknum(String worknum) {
        this.worknum = worknum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getOpenURL() {
        return openURL;
    }

    public void setOpenURL(String openURL) {
        this.openURL = openURL;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
