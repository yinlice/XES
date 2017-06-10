package Wxzt.servlet.Common.system;

/**
 * Created by Administrator on 2016-6-24.
 */
public class systemFileBean {
    private String fileName;//硬盘名称
    private int totalSpace;//硬盘内存总量
    private int userSpace;//硬盘内存使用量
    private int freeSpace;//硬盘内存可使用量

    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTotalSpace() {
        return this.totalSpace;
    }
    public void setTotalSpace(int totalSpace) {
        this.totalSpace = totalSpace;
    }

    public int getUserSpace() {
        return this.userSpace;
    }
    public void setUserSpace(int userSpace) {
        this.userSpace = userSpace;
    }

    public int getFreeSpace() {
        return this.freeSpace;
    }
    public void setFreeSpace(int freeSpace) {
        this.freeSpace = freeSpace;
    }

    public String toString(){
        return this.fileName+"盘 内存大小为："+this.getTotalSpace() + " M 使用内存大小："+this.getUserSpace()+" M 可用内存大小："+this.getFreeSpace();
    }

}
