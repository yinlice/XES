package Wxzt.tool.weixin;

import Wxzt.tool.JDBC;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by pengxi on 2016/12/12.
 */
public class wx {
    protected Boolean upOpenid(String worknum,String pw,String openid) {
        Boolean bool = false;
        if(worknum!=null&& !Objects.equals(worknum, "")&&pw!=null&& !Objects.equals(pw, "") &&openid!=null&& !Objects.equals(openid, "")){
            String strsql = "update dddWork set openid='"+openid+"' where worknum='"+worknum+"' and password='"+pw+"'";
            System.out.println("更新openid："+strsql);
            try {
                JDBC jdbc = new JDBC();
                bool = jdbc.executeUpdate(strsql);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return bool;
    }
}
