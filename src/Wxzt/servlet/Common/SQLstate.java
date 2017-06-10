package Wxzt.servlet.Common;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-5-3.
 */
public class SQLstate {
    private String sqlHeaderSelect;
    private String sqlHeaderOther;
    private String sqlJoin;

    public void setSqlHeaderSelect(String sqlHeaderSelect){
        this.sqlHeaderSelect = sqlHeaderSelect;
    }
    public String getSqlHeaderSelect(){
        return this.sqlHeaderSelect;
    }

    public void setSqlHeaderOther(String sqlHeaderOther){
        this.sqlHeaderOther = sqlHeaderOther;
    }
    public String getSqlHeaderOther(){
        return this.sqlHeaderOther;
    }

    public void setSqlJoin(String sqlJoin){
        this.sqlJoin = sqlJoin;
    }
    public String getSqlJoin(){
        return this.sqlJoin;
    }

    public void setSqlHeader(String tablename,String[] select,String dictName){
        String header = "",sqlLeftJoin = "";
        for(int i=0;i<select.length;i++){
            header += ","+tablename+i+".DictMean as "+select[i];
            sqlLeftJoin += " left join v_Dict as "+tablename+i+" on concat(" +
                    "'"+dictName+"_"+select[i]+"',"+tablename+"."+select[i]+" )= concat("+tablename+i+".DictType,"+tablename+i+".DictCode ) ";
        }
        this.setSqlHeaderSelect(header);
        this.setSqlJoin(sqlLeftJoin);
    }
}
