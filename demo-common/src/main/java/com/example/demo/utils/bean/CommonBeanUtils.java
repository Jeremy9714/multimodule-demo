package com.example.demo.utils.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/29 17:11
 * @Version: 1.0
 */
@Slf4j
public final class CommonBeanUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String STANDARD_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String TIME_PATTERN = "HH:mm:ss";

    static {
        // 设置java.util.Date时间类的序列化及反序列化的格式
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_PATTERN));
        // 初始话JavaTimeModule
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 处理LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        // 处理LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        // 处理LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        // 注册时间模块，支持jsr310 (java.time包下的时间类)
        objectMapper.registerModule(javaTimeModule);
        //序列化的时候序列对象的所有属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * 禁止实例化
     */
    private CommonBeanUtils() {
    }

    /**
     * 源对象属性绝对覆盖到目标对象
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        // 判断source是否为空
        if (source == null) {
            return null;
        }
        // 判断targetClass是否为空
        if (targetClass == null) {
            return null;
        }
        T target;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("bean负指数型异常", e);
        }
    }

    /**
     * 源对象属性绝对覆盖到目标对象
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * list中对象的copy
     *
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> collectionCopy(Collection source, Class<T> clazz) {
        if (source == null) {
            return new ArrayList<>();
        } else {
            List<T> list = new ArrayList<>();
            Iterator iterator = source.iterator();

            while (iterator.hasNext()) {
                Object item = iterator.next();
                T target = copyProperties(item, clazz);
                list.add(target);
            }
            return list;
        }
    }

    /**
     * 源对象上属性为null的，该属性不对目标进行覆盖
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T copyNotNullProperties(Object source, Class<T> targetClass) {
        // 判断source是否为空
        if (source == null) {
            return null;
        }
        // 判断targetClass是否为空
        if (targetClass == null) {
            return null;
        }
        T target;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
            String[] nullPropertyNames = getNullPropertyNames(target);
            BeanUtils.copyProperties(source, target, nullPropertyNames);
            return target;
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("负指数型异常", e);
        }
    }

    /**
     * 源对象上属性为null的，该属性不对目标进行覆盖
     *
     * @param source
     * @param target
     * @return
     */
    public static void copyNotNullProperties(Object source, Object target) {
        String[] nullPropertyNames = getNullPropertyNames(target);
        BeanUtils.copyProperties(source, target, nullPropertyNames);
    }

    /**
     * 获取空属性名称
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNamesSet = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object sourceValue = src.getPropertyValue(pd.getName());
            if (sourceValue == null) {
                emptyNamesSet.add(pd.getName());
            }
        }
        String[] result = new String[emptyNamesSet.size()];
        return emptyNamesSet.toArray(result);
    }

    /**
     * 实体类转json字符串
     *
     * @param obj
     * @return
     */
    public static String beanToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("实体类对象转换为Json字符串异常", e);
            throw new RuntimeException("实体类对象转换为Json字符串异常");
        }
    }

    /**
     * json字符串转实体类对象
     *
     * @param obj
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String obj, Class<T> targetClass) {
        try {
            return objectMapper.readValue(obj, targetClass);
        } catch (IOException e) {
            log.error("Json字符串转换为实体类对象异常", e);
            throw new RuntimeException("Json字符串转换为实体类对象异常");
        }
    }

    /**
     * json字符串转集合对象
     *
     * @param obj
     * @param toValueType
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String obj, Class<T> toValueType) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, toValueType);
        try {
            return objectMapper.readValue(obj, javaType);
        } catch (IOException e) {
            log.error("类型转换错误", e);
            throw new RuntimeException("类型转换错误");
        }
    }

    /**
     * map转实体类对象
     *
     * @param sourceMap
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> sourceMap, Class<T> targetClass) {
        try {
            return objectMapper.readValue(beanToJson(sourceMap), targetClass);
        } catch (IOException e) {
            log.error("Map转实体类对象异常", e);
            throw new RuntimeException("Map转实体类对象异常");
        }
    }

    /**
     * 实体类对象转map
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T obj) {
        try {
            return objectMapper.readValue(beanToJson(obj), Map.class);
        } catch (IOException e) {
            log.error("实体类对象为Map异常", e);
            throw new RuntimeException("实体类对象为Map异常");
        }
    }

    /**
     * 多bean转map
     *
     * @param objList
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map;
            T bean;
            for (T t : objList) {
                bean = t;
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 多map转bean
     *
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map;
            for (Map<String, Object> stringMap : maps) {
                map = stringMap;
                T bean = mapToBean(map, clazz);
                list.add(bean);
            }
        }
        return list;
    }

}
