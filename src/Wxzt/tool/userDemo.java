package Wxzt.tool;

/**
 * Created by Administrator on 2016-5-5.
 */
public class userDemo {
    private String name;
    private int age;
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getAge()
    {
        return age;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
    @Override
    //为了方便打印，添加一个toString方法
    public String toString()
    {
        return "User [age=" + age + ", name=" + name + "]";
    }
}
