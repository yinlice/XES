package Wxzt.servlet.Login;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-4-25.
 */
public class MenuGetData {
    private ArrayList<Object> menu;//菜单
    private ArrayList<Object> thumbnail;//二级菜单
    private ArrayList<Object> userCall;//坐席呼叫数据
    private Object callinList;//呼入排行
    private Object calloutList;//外呼排行
    private Object averageList;//平均排行

    public ArrayList<Object> getMenu() {
        return menu;
    }
    public void setMenu(ArrayList<Object> menu) {
        this.menu = menu;
    }

    public ArrayList<Object> getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(ArrayList<Object> thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<Object> getUserCall() {
        return userCall;
    }
    public void setUserCall(ArrayList<Object> userCall) {
        this.userCall = userCall;
    }

    public Object getCallinList() {
        return callinList;
    }
    public void setCallinList(Object callinList) {
        this.callinList = callinList;
    }

    public Object getCalloutList() {
        return calloutList;
    }
    public void setCalloutList(Object calloutList) {
        this.calloutList = calloutList;
    }

    public Object getAverageList() {
        return averageList;
    }
    public void setAverageList(Object averageList) {
        this.averageList = averageList;
    }
}

