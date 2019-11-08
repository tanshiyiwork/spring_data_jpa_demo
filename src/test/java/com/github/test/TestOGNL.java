package com.github.test;

import com.jpa.entity.User;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestOGNL {

    @Test
    public void text1() throws OgnlException {//取出root和context的属性值
        //准备root
        User u1 = new User("qqq",18);

        //准备context
        Map<String,User> context = new HashMap<String,User>();
        context.put("user1", new User("sss", 19));
        context.put("user2", new User("yyy", 20));

        //设置root和context
        OgnlContext ognl = new OgnlContext();
        ognl.setRoot(u1);
        ognl.setValues(context);

        //取出root中的数据,直接写属性名
        String name = (String) Ognl.getValue("name", ognl, ognl.getRoot());
        Integer age = (Integer) Ognl.getValue("age", ognl, ognl.getRoot());
        System.out.println(name);
        System.out.println(age);

        //取出context中的属性值
        //#代表从context中取值
        String user1name = (String) Ognl.getValue("#user1.name", ognl, ognl.getRoot());
        Integer user1age = (Integer) Ognl.getValue("#user1.age", ognl, ognl.getRoot());
        System.out.println(user1name+user1age);
    }

    @Test
    public void fun2() throws OgnlException{//修改root和context的属性值
        //准备root
        User u1 = new User("qqq",18);

        //准备context
        Map<String,User> context = new HashMap<String,User>();
        context.put("user1", new User("sss", 19));
        context.put("user2", new User("yyy", 20));

        //设置root和context
        OgnlContext ognl = new OgnlContext();
        ognl.setRoot(u1);
        ognl.setValues(context);

        //修改root的name属性值
        Ognl.getValue("name = 'aaaa'", ognl, ognl.getRoot());
        String rootname = (String) Ognl.getValue("name", ognl, ognl.getRoot());
        System.out.println(rootname);

        //修改user1的name和age属性值
        Ognl.getValue("#user1.name = 'bbbb'", ognl, ognl.getRoot());
        Ognl.getValue("#user1.age = 99", ognl, ognl.getRoot());
        String user1name = (String) Ognl.getValue("#user1.name", ognl, ognl.getRoot());
        Integer user1age = (Integer) Ognl.getValue("#user1.age", ognl, ognl.getRoot());
        System.out.println(user1name+user1age);
    }

    @Test
    public void fun3() throws OgnlException{//调用user的方法
        //准备root
        User u1 = new User("qqq",18);

        //准备context
        Map<String,User> context = new HashMap<String,User>();
        context.put("user1", new User("sss", 19));
        context.put("user2", new User("yyy", 20));

        //设置root和context
        OgnlContext ognl = new OgnlContext();
        ognl.setRoot(u1);
        ognl.setValues(context);

        //从root中调用user对象的getName方法。
        String rootname = (String) Ognl.getValue("getName()", ognl, ognl.getRoot());
        System.out.println(rootname);

        //从root中调用user对象的setName方法。
        Ognl.getValue("setName('jjj')", ognl, ognl.getRoot());
        String rootname2 = (String) Ognl.getValue("getName()", ognl, ognl.getRoot());
        System.out.println(rootname2);
    }

    @Test
    public void fun4() throws OgnlException{//调用静态的方法和属性
        //准备root
        User u1 = new User("qqq",18);

        //准备context
        Map<String,User> context = new HashMap<String,User>();
        context.put("user1", new User("sss", 19));
        context.put("user2", new User("yyy", 20));

        //设置root和context
        OgnlContext ognl = new OgnlContext();
        ognl.setRoot(u1);
        ognl.setValues(context);

        //访问静态方法,属性
        String xxx = (String) Ognl.getValue("@zl.ognl.XXX@xxx('1234567')", ognl, ognl.getRoot());
        Double pi = (Double) Ognl.getValue("@java.lang.Math@PI", ognl, ognl.getRoot());
        System.out.println(xxx);
        System.out.println(pi);
    }

    @Test
    public void fun5() throws OgnlException{//创建list对象,操作list
        //准备root
        User u1 = new User("qqq",18);

        //准备context
        Map<String,User> context = new HashMap<String,User>();
        context.put("user1", new User("sss", 19));
        context.put("user2", new User("yyy", 20));

        //设置root和context
        OgnlContext ognl = new OgnlContext();
        ognl.setRoot(u1);
        ognl.setValues(context);

        //创建list，取出list的某个元素
        Integer size = (Integer)Ognl.getValue("{'aaa','bbb','ccc','ddd'}.size()", ognl, ognl.getRoot());
        String first = (String)Ognl.getValue("{'aaa','bbb','ccc','ddd'}[0]", ognl, ognl.getRoot());
        String second = (String)Ognl.getValue("{'aaa','bbb','ccc','ddd'}.get(1)", ognl, ognl.getRoot());
        System.out.println(size);
        System.out.println(first);
        System.out.println(second);

        List list = (List) Ognl.getValue("{'aaa','bbb','ccc','ddd'}", ognl, ognl.getRoot());
        System.out.println(list);
    }

    @Test
    public void fun6() throws OgnlException{//创建map对象，操作map
        //准备root
        User u1 = new User("qqq",18);

        //准备context
        Map<String,User> context = new HashMap<String,User>();
        context.put("user1", new User("sss", 19));
        context.put("user2", new User("yyy", 20));

        //设置root和context
        OgnlContext ognl = new OgnlContext();
        ognl.setRoot(u1);
        ognl.setValues(context);

        //创建map,取出某个元素
        Integer size = (Integer)Ognl.getValue("#{'name':'bbb','age':18}.size()", ognl, ognl.getRoot());
        String name = (String)Ognl.getValue("#{'name':'bbb','age':18}['name']", ognl, ognl.getRoot());
        String name2 = (String)Ognl.getValue("#{'name':'bbb','age':18}.get('name')", ognl, ognl.getRoot());
        System.out.println(size);
        System.out.println(name);
        System.out.println(name2);

        Map map = (Map)Ognl.getValue("#{'name':'bbb','age':18}", ognl, ognl.getRoot());
        System.out.println(map);
    }
}
