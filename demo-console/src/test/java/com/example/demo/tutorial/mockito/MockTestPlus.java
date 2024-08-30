package com.example.demo.tutorial.mockito;

import com.example.demo.console.Application;
import com.example.demo.tutorial.mockito.dao.MockitoDao;
import com.example.demo.tutorial.mockito.entity.MockitoEntity;
import com.example.demo.tutorial.mockito.service.MockitoServicePlus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/30 15:55
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
public class MockTestPlus {

    @Mock
    private MockitoDao mockitoDao;

    @InjectMocks
    MockitoServicePlus mockitoServicePlus;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(mockitoServicePlus, "mockitoValue", "mk123");
        MockitoAnnotations.initMocks(this);
        MockitoEntity me1 = new MockitoEntity();
        MockitoEntity me2 = new MockitoEntity();
        me1.setId("id1");
        me2.setId("id2");
        me1.setName("name1");
        me2.setName("name2");
        Mockito.when(mockitoDao.getAll()).thenReturn(Arrays.asList(me1, me2));
    }

    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<MockitoEntity> list = mockitoServicePlus.getAll();
        list = mockitoDao.getAll();

        MockitoServicePlus msp = new MockitoServicePlus();
        Class<? extends MockitoServicePlus> clazz = msp.getClass();
        Method method = clazz.getDeclaredMethod("printMsg", String.class);
        method.setAccessible(true);
        method.invoke(mockitoServicePlus, "msg");
    }
}
