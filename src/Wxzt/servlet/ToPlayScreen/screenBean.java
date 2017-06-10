package Wxzt.servlet.ToPlayScreen;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/12.
 */
public class screenBean {
    private Object DataList;//客户资料
    private ArrayList<ArrayList> DictList;//下拉框数据
    private ArrayList<ArrayList> WorkerList;//坐席数据
    private boolean screenResult;//更新客户信息结果
    private boolean dynamicResult;//更新动态结果
    private boolean investResult;//更新投资记录结果
    private boolean cdrResult;//更新录音结果
    private Object tabData;//弹屏中tab的数据

    public void setTabData(Object data){
        this.tabData = data;
    }
    public Object getTabData(){
        return this.tabData;
    }

    public Object getDataList() {
        return DataList;
    }
    public void setDataList(Object DataList) {
        this.DataList = DataList;
    }

    public ArrayList<ArrayList> getDictList() {
        return DictList;
    }
    public void setDictList(ArrayList<ArrayList> DictList) {
        this.DictList = DictList;
    }

    public ArrayList<ArrayList> getWorkerList() {
        return WorkerList;
    }
    public void setWorkerList(ArrayList<ArrayList> WorkerList) {
        this.WorkerList = WorkerList;
    }

    public void setScreenResult(boolean screenResult){
        this.screenResult = screenResult;
    }
    public boolean getScreenResult(){
        return this.screenResult;
    }

    public void setDynamicResult(boolean dynamicResult){
        this.dynamicResult = dynamicResult;
    }
    public boolean getDynamicResult(){
        return this.dynamicResult;
    }

    public void setInvestResult(boolean investResult){
        this.investResult = investResult;
    }
    public boolean getInvestResult(){
        return this.investResult;
    }

    public void setCdrResult(boolean cdrResult){
        this.cdrResult = cdrResult;
    }
    public boolean getCdrResult(){
        return this.cdrResult;
    }
}
