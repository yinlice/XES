package Wxzt.tool;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class JDBC {
//    private final String dbDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    //设置SQL Server2005的驱动名称
//    private final String url = "jdbc:sqlserver://192.168.1.18:1433;DatabaseName=Hujiaoyi";
//    private final String user = "sa";                 //设置连接数据库用户名
//    private final String password = "wxzt";                     //设置连接数据库的密码
    private ResultSet rs = null;                 //设置将查询结果存放在ResultSet结果集类中
    private Statement stmt = null;                    //设置执行SQL语句的声明对象
    private Connection con = null;                   //设置创建数据库连接的对象
    public boolean conBool = true;
    File f = new File(JDBC.class.getClassLoader().getResource("/").getPath());
    String confURL = f+"\\conf\\sysconf.ini";

    public JDBC() throws IOException {
        IniReader reader = new IniReader(confURL);
        String dbDriver = reader.getValue("SQLServer","dbDriver");
        try {
            Class.forName(dbDriver).newInstance(); //系统自动加载数据库驱动
            System.out.println("数据库加载成功");
        } catch (Exception ex) {
            System.out.println("数据库加载失败");   //如果加载驱动失败，则在控制台中输出信息
        }
    }

    private boolean creatConnection() throws IOException{
        IniReader reader = new IniReader(confURL);
        String url = reader.getValue("SQLServer","url");
        String user = reader.getValue("SQLServer","user");
        String password = reader.getValue("SQLServer","password");
        try {
            con = DriverManager.getConnection(url, user, password);
            //根据数据库用户名、密码及url地址取得数据库连接操作
            con.setAutoCommit(conBool); //将连接设置为自动方式
            System.out.println("数据库登录成功");
            return true;                    //返回true，已经取得数据库连接
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;                   //返回false，未能取得数据库连接
        }
    }

    public void setConBool(boolean bool){this.conBool = bool;}

    public Connection getCon() throws IOException {
        boolean t = creatConnection();
        return this.con;
    }

    public ResultSet executeQuery(String sql) throws IOException {
        try {
            if (null == con) {
                //如果Connection类的对象con为空，则执行创建数据库连接的方法
                creatConnection();
            }
            stmt = con.createStatement();
            //根据数据库连接对象内容，创建执行SQL语句的声明对象
            try {
                rs = stmt.executeQuery(sql);
                //执行查询的SQL语句，并将查询结果存在ResultSet结果集类中
                return rs;         //将查询结果rs结果通过return关键字返回
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;            //将null对象通过return关键字返回
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;                  //将null对象通过return关键字返回
        }
    }

    public boolean executeUpdate(String sql) throws IOException {
        if (null == con) {
            creatConnection();
            //如果Connection类的对象con为空，则执行创建数据库连接的方法
        }
        try {
            stmt = con.createStatement();
            //根据数据库连接对象内容，创建执行SQL语句的声明对象
            stmt.executeUpdate(sql);          //执行数据库更新的SQL语句
            return true;                                  //如果执行成功，则通过关键字return返回true
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;                                //如果执行失败，则通过关键字return返回false
        }
    }

    public void closeConnection() {
        if (null != rs) {
            try {
                rs.close();                         //执行关闭ResultSet对象
            } catch (SQLException e) {
                e.printStackTrace();   //抛出关闭rs对象的异常信息
            }
        }
        if (null != stmt) {
            try {
                stmt.close();              //执行关闭Statement对象
            } catch (SQLException e) {
                e.printStackTrace();   //抛出关闭stmt对象的异常信息
            }
        }
        if (null != con) {
            try {
                con.close();             //执行关闭Connection对象
            } catch (SQLException ignored) {
            } finally {
                con = null;
            }
        }
    }
}
