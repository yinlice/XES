package Wxzt.servlet.Cdr;


import Wxzt.servlet.Cust.custSql;
import Wxzt.servlet.Cust.repeatCustBean;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2016-5-10.
 */
public class cdrSql extends cdrTableSql {
    /*客户录音的查询条件，必填写*/
    public String setCdrStrSql(HttpServletRequest request){
        String strSQL = "";
        /*客户编号*/
        String CustNum = request.getParameter("CustNum");
        String dh = request.getParameter("dh");/*客户电话*/
        String repeatSql = "",mobile = "",telnum = "";
        if(CustNum!=null||dh!=null){
            /*查询客户以及联系人的电话*/
            try {
                String sql = "select mobile,telnum from v_custContact where custnum='"+CustNum+"'";
                String[] list = {"mobile","telnum"};
                custSql cust = new custSql();
                ArrayList<Object> custObject = cust.sqlListDataError(sql, list, "custRepeat");
                ArrayList<String> backUpTel = new ArrayList<>();/*成功导入备份数据库，并且相对于已有客户的重复电话*/
                for(int r = 0;r<custObject.size();r++){
                    repeatCustBean rep = (repeatCustBean) custObject.get(r);
                    mobile = rep.mobile;
                    telnum = rep.telnum;
                    if(mobile!=null&& !Objects.equals(mobile, "")){
                        backUpTel.add("'"+mobile+"'");
                    }
                    if(telnum!=null&& !Objects.equals(telnum, "")){
                        backUpTel.add("'"+telnum+"'");
                    }
                }
                repeatSql = " or telnum in("+cust.join(backUpTel,",")+") ";
            } catch (SQLException e) {
                e.printStackTrace();
            }
            strSQL+=" and (CallOID='"+CustNum+"' " +repeatSql+ " )";
        }
        return strSQL;
    }
}
