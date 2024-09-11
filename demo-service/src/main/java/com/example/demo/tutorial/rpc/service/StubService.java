package com.example.demo.tutorial.rpc.service;

import com.example.demo.tutorial.rpc.entity.StudentRpc;
import com.example.demo.utils.service.BaseService;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/11 13:57
 * @Version: 1.0
 */
public class StubService<T> extends BaseService<T> {
    
    private Class<T> clazz;
    
    public StubService(Class<T> clazz){
        this.clazz = clazz;
    }

    public T getStub() throws Exception {
        InvocationHandler h = (proxy, method, args) -> {
            Socket socket = new Socket(InetAddress.getLocalHost(), 23456);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            // 方法名
            String methodName = method.getName();
            // 方法参数类型
            Class<?>[] parameterTypes = method.getParameterTypes();
            oos.writeUTF(methodName);
            oos.writeObject(parameterTypes);
            // 方法参数
            oos.writeObject(args);
            oos.flush();

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String sid = dis.readUTF();
            String sname = dis.readUTF();
            StudentRpc studentRpc = new StudentRpc(sid, sname);
            oos.close();
            dis.close();
            socket.close();
            return studentRpc;
        };

//        clazz = getParameterizedClass();
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, h);
        return (T) o;
    }

//    @SuppressWarnings("unchecked")
//    private Class<T> getParameterizedClass() {
//        Type type = getClass().getGenericSuperclass();
//        ParameterizedType parameterizedType = (ParameterizedType) type;
////        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
//        Class<T> clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
//        return clazz;
//    }
}
