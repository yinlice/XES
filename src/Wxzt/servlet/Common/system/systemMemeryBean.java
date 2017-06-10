package Wxzt.servlet.Common.system;

/**
 * Created by Administrator on 2016-6-24.
 */
public class systemMemeryBean {
    private int totalMemery = 0;//总内存(以M为单位)
    private int useMemery = 0;//使用内存(以M为单位)

    public int getTotalMemery() {
        return this.totalMemery;
    }
    public void setTotalMemery(int totalMemery) {
        this.totalMemery = totalMemery;
    }

    public int getUseMemery() {
        return this.useMemery;
    }
    public void setUseMemery(int useMemery) {
        this.useMemery = useMemery;
    }

}
