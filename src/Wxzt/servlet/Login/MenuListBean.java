package Wxzt.servlet.Login;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-4-25.
 */
public class MenuListBean {
    private ArrayList<String> userNames;
    private ArrayList<String> userData;

    public ArrayList<String> getUserNames() {
        return userNames;
    }
    public void setUserNames(ArrayList<String> userNames) {
        this.userNames = userNames;
    }

    public ArrayList<String> getUserData() {
        return userData;
    }
    public void setUserData(ArrayList<String> userData) {
        this.userData = userData;
    }
}
