package com.example.demo.tutorial.proxy;

import com.example.demo.console.Application;
import com.example.demo.tutorial.proxy.entity.cglib.Animal;
import com.example.demo.tutorial.proxy.entity.cglib.CglibProxy;
import com.example.demo.tutorial.proxy.entity.jdk.BigStar;
import com.example.demo.tutorial.proxy.entity.jdk.ProxyStar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/2 9:45
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class ProxyTest {

    @Test
    public void testJdkProxy() {
        // TODO 报错
        BigStar obj1 = (BigStar) new ProxyStar(new BigStar("test1")).createProxy();
        BigStar obj2 = (BigStar) new ProxyStar(new BigStar("test2")).createProxy();
        obj1.printInfo();
        obj2.printInfo();
    }

    @Test
    public void testCglibProxy() {
        Animal obj1 = (Animal) new CglibProxy(new Animal("test1")).createProxy();
        Animal obj2 = (Animal) new CglibProxy(new Animal("test2")).createProxy();
        obj1.printInfo();
        obj2.printInfo();
    }

}
