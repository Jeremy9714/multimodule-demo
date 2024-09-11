package com.example.demo.tutorial.rpc;

import com.example.demo.console.Application;
import com.example.demo.tutorial.rpc.entity.StudentRpc;
import com.example.demo.tutorial.rpc.service.StubService;
import com.example.demo.tutorial.rpc.service.StudentRpcService;
import com.example.demo.tutorial.rpc.service.impl.StudentRpcServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/11 10:28
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
//@RunWith(SpringRunner.class)
public class RpcTest {

    @Test
    public void client() throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), 12345);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        StudentRpcService studentRpcService = id -> {
            dos.writeUTF(id);
            socket.getOutputStream().write(bos.toByteArray());
            socket.getOutputStream().flush();

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String sid = dis.readUTF();
            String sname = dis.readUTF();
            dis.close();
            StudentRpc student = new StudentRpc(sid, sname);
            return student;
        };

        StudentRpc studentRpc = studentRpcService.getElementById("1");
        System.out.println(studentRpc);

        dos.close();
        socket.close();
    }

    @Test
    public void server() throws Exception {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("======服务端开始运行======");
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            DataInputStream dis = new DataInputStream(is);
            DataOutputStream dos = new DataOutputStream(os);

            String id = dis.readUTF();
            StudentRpcServiceImpl studentRpcService = new StudentRpcServiceImpl();
            StudentRpc studentRpc = studentRpcService.getElementById(id);
            dos.writeUTF(studentRpc.getId());
            dos.writeUTF(studentRpc.getName());
            dos.flush();
            socket.close();
            System.out.println("======服务端结束运行======");
        }
    }

    /**
     * 客户端stub模拟
     *
     * @throws Exception
     */
    @Test
    public void clientAdv() throws Exception {
        StubService<StudentRpcService> studentRpcStubService = new StubService<>(StudentRpcService.class);
        StudentRpcService studentRpcService = studentRpcStubService.getStub();
        StudentRpc studentRpc = studentRpcService.getElementById("1");
        System.out.println(studentRpc);
    }

    @Test
    public void serverAdv() throws Exception {
        ServerSocket serverSocket = new ServerSocket(23456);
        System.out.println("======服务端已启动======");
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            DataOutputStream dos = new DataOutputStream(os);

            String methodName = ois.readUTF();
            Class<?>[] parameterTypes = (Class<?>[]) ois.readObject();
            Object[] args = (Object[]) ois.readObject();
            StudentRpcServiceImpl studentRpcService = new StudentRpcServiceImpl();
            Method method = studentRpcService.getClass().getDeclaredMethod(methodName, parameterTypes);
            StudentRpc studentRpc = (StudentRpc) method.invoke(studentRpcService, args);

            dos.writeUTF(studentRpc.getId());
            dos.writeUTF(studentRpc.getName());
            dos.flush();

            ois.close();
            dos.close();
            socket.close();
            System.out.println("======服务端已关闭======");
        }
    }
}
