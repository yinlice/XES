package Wxzt.servlet.Login;

/**
 * Created by Administrator on 2016-4-20.
 */
public class MenUNavBean {
    private String parentID;//父级编号
    private String ThumbnailID;//菜单编号
    private String ThumbnailName;//菜单名称
    private String ThumbnailURL;//菜单url
    private String ThumbnailOrder;//菜单顺序
    private String ThumbnailNote;//菜单顺序

    public String getParentID() {
        return parentID;
    }
    public void setParentID(String parentID) {
        this.parentID = parentID;
    }
    public String getThumbnailID() {
        return ThumbnailID;
    }
    public void setThumbnailID(String ThumbnailID) {
        this.ThumbnailID = ThumbnailID;
    }

    public String getThumbnailName() {
        return ThumbnailName;
    }
    public void setThumbnailName(String ThumbnailName) {
        this.ThumbnailName = ThumbnailName;
    }

    public String getThumbnailURL() {
        return ThumbnailURL;
    }
    public void setThumbnailURL(String ThumbnailURL) {
        this.ThumbnailURL = ThumbnailURL;
    }

    public String getThumbnailOrder() {
        return ThumbnailOrder;
    }
    public void setThumbnailOrder(String ThumbnailOrder) {
        this.ThumbnailOrder = ThumbnailOrder;
    }

    public String getThumbnailNote() {
        return ThumbnailNote;
    }
    public void setThumbnailNote(String ThumbnailNote) {
        this.ThumbnailNote = ThumbnailNote;
    }
}
