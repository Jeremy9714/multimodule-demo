package com.example.demo.tutorial.mockito;

import com.example.demo.console.Application;
import com.example.demo.tutorial.mockito.entity.MockitoEntity;
import com.example.demo.tutorial.mockito.service.MockitoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/30 14:27
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
//@RunWith(SpringRunner.class)
@Slf4j
public class MockitoTest {

    @Mock
    private MockitoService mockitoService;

    @BeforeEach
    public void init() {
        // @Value注入内容模拟
        ReflectionTestUtils.setField(mockitoService, "mockitoValue", "mk123");
        // 模拟@Mock注解注释的类; 也可以通过Mockito.mock(xxx.class)手动模拟
        MockitoAnnotations.initMocks(this);
        System.out.println("初始化方法");
    }

    @Test
    public void testMock1() {
        Mockito.when(mockitoService.getEntity(Mockito.anyMap())).thenReturn(new MockitoEntity() {{
            setId("id");
            setName("n4");
        }});

        Map<String, Object> emptyMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<String, Object>() {{
            put("id", "i5");
        }};
        Mockito.when(mockitoService.getEntity(paramMap)).thenReturn(new MockitoEntity() {{
            setId("i5");
            setName("n5");
        }});

        // 调用测试
        System.out.println(mockitoService.getEntity(emptyMap));
        System.out.println(mockitoService.getEntity(paramMap));

        // 验证是否调用
        Mockito.verify(mockitoService).getEntity(emptyMap);
        System.out.println("测试一已调用");
        Mockito.verify(mockitoService).getEntity(paramMap);
        System.out.println("测试二已调用");
        Mockito.verify(mockitoService).getAll();
        System.out.println("测试三已调用");
    }

    /**
     * 模拟异常抛出
     */
    @Test
    public void testMock2() {
        try {
            Mockito.when(mockitoService.getEntity(Mockito.anyMap())).thenThrow(new RuntimeException("查询报错"));
            System.out.println(mockitoService.getEntity(new HashMap<>()));
        } catch (Exception e) {
            log.error("测试报错: ", e);
        }
    }

    /**
     * 模拟无返回值方法异常抛出
     */
    @Test
    public void testMock3() {
        try {
            Mockito.doThrow(new RuntimeException("无返回方法异常！")).when(mockitoService).returnNothing(Mockito.anyString());
            mockitoService.returnNothing("");
        } catch (Exception e) {
            log.error("测试报错: ", e);
        }
    }

    /**
     * 验证调用次数、顺序
     */
    @Test
    public void testMock4() {
        Mockito.when(mockitoService.getEntity(Mockito.anyMap())).thenReturn(Mockito.any());

        System.out.println(mockitoService.getEntity(new HashMap<>()));
//        System.out.println(mockitoService.getEntity(new HashMap<>()));
        System.out.println(mockitoService.getEntity(new HashMap<String, Object>() {{
            put("id", "i7");
            put("name", "n7");
        }}));

        // 验证指定方法调用次数是否为2
        Mockito.verify(mockitoService, Mockito.times(2)).getEntity(Mockito.anyMap());

        System.out.println("======顺序调用校验======");

        // 按顺序调用指定次数
        Map<String, Object> paramMap1 = new HashMap<String, Object>() {{
            put("name", "n1");
        }};
        Map<String, Object> paramMap2 = new HashMap<String, Object>() {{
            put("name", "n2");
        }};
        Mockito.when(mockitoService.getEntity(paramMap1)).thenReturn(new MockitoEntity() {{
            setId("i1");
            setName("n1");
        }});
        Mockito.when(mockitoService.getEntity(paramMap2)).thenReturn(new MockitoEntity() {{
            setId("i2");
            setName("n2");
        }});

        System.out.println(mockitoService.getEntity(paramMap1));
        System.out.println(mockitoService.getEntity(paramMap1));
        System.out.println(mockitoService.getEntity(paramMap2));

        InOrder inOrder = Mockito.inOrder(mockitoService);
        inOrder.verify(mockitoService, Mockito.times(2)).getEntity(paramMap1);
        inOrder.verify(mockitoService).getEntity(paramMap2);
    }

    /**
     * 对void方法设置模拟
     */
    @Test
    public void testMock5() {
        Mockito.doAnswer(InvocationOnMock -> {
            System.out.println("进入mock方法！");
            return null;
        }).when(mockitoService).returnNothing(Mockito.anyString());

        mockitoService.returnNothing("");
        List<MockitoEntity> list = mockitoService.getAll(); // 无任何操作
    }
}
