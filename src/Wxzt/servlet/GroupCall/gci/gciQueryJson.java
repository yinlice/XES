package Wxzt.servlet.GroupCall.gci;

import Wxzt.servlet.bean.Common.queryJson;

/**
 * Created by Administrator on 2016-10-19.
 */
public class gciQueryJson extends queryJson {
    protected String countType;//查询数据的类型

    public String getCountType() {
        return countType;
    }
    public void setCountType(String countType) {
        this.countType = countType;
    }
}
