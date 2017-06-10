package Wxzt.tool;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Created by Administrator on 2016-5-5.
 */
public class demo1 {
    public static void main(String[] args) throws Exception
    {
        userDemo user = new userDemo();
        BeanUtils.setProperty(user, "name", "lisi");
        BeanUtils.setProperty(user, "age", "11");
        //创建属性描述器
//        PropertyDescriptor descriptor = new PropertyDescriptor("name",userDemo.class);
        //获得写方法
//        Method writeMethod = descriptor.getWriteMethod();

        //调用写方法
//        writeMethod.invoke(user, "lisi");
        System.out.println(user);

    }
}
