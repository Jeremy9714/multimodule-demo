package com.example.demo.tutorial.web;

import com.example.demo.console.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.*;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/22 8:49
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class ProtocolTest {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolTest.class);

    @Test
    public void udpSenderTest() throws Exception {
        // 创建socket
        DatagramSocket socket = new DatagramSocket();
        String data = "发送端内容";
        InetAddress localHost = InetAddress.getLocalHost();
        // 创建数据包
        DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, localHost, 30000);
        // 发送数据
        socket.send(packet);
        socket.close();
        System.out.println("======udp数据发送完毕！======");
    }

    @Test
    public void udpReceiverTest() throws Exception {
        // 创建socket 接收端必须指定端口
        DatagramSocket socket = new DatagramSocket(30000);
        byte[] bytes = new byte[1024];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        // 接收
        socket.receive(packet);
        // 获取发送方ip
        InetAddress address = packet.getAddress();
        String hostAddress = address.getHostAddress();
        // 获取发送方端口
        int port = packet.getPort();
        // 获取发送内容
        byte[] data = packet.getData();
        int length = data.length;
        logger.info("======IP:{}, 端口号:{}, 发送了: {}======", hostAddress, port, new String(data, 0, length));
        socket.close();
        System.out.println("======接收完毕======");
    }

    @Test
    public void udpFileSenderTest() throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
        // 服务端端口号
        int serverPort = 30000;
        String filePath = "D:\\workplace\\test\\springboot_test3\\demo2\\demo-console\\src\\main\\resources\\files\\udp-sender1.txt";
        FileInputStream fis = new FileInputStream(filePath);
        byte[] bytes = new byte[1024];
        int bytesRead;
        System.out.println("======开始发送文件======");
        while ((bytesRead = fis.read(bytes)) != -1) {
            DatagramPacket packet = new DatagramPacket(bytes, bytesRead, inetAddress, serverPort);
            // 发送数据
            socket.send(packet);
        }
        DatagramPacket responsePacket = new DatagramPacket(new byte[1024], 1024);
        socket.receive(responsePacket);
        System.out.println(new String(responsePacket.getData(), 0, responsePacket.getLength()));
        System.out.println("======文件发送完毕！======");
        fis.close();
        socket.close();
    }

    @Test
    public void udpFileReceiverTest() throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(30000);
        byte[] bytes = new byte[1024];
        String filePath = "D:\\workplace\\test\\springboot_test3\\demo2\\demo-console\\src\\main\\resources\\files\\udp-receiver1.txt";
        FileOutputStream fos = new FileOutputStream(filePath);
        System.out.println("======开始接收文件======");
        while (true) {
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            // 接收数据
            serverSocket.receive(packet);
            fos.write(bytes, 0, bytes.length);
            // 读取完毕
            if (packet.getLength() < bytes.length) {
                String responseText = "已接收到文件";
                // 发送回执
                serverSocket.send(new DatagramPacket(responseText.getBytes(),
                        responseText.getBytes().length, packet.getSocketAddress()));
                break;
            }
        }
        System.out.println("======文件接收完毕！======");

        fos.close();
        serverSocket.close();
    }

    @Test
    public void tcpServerTest() throws IOException {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            // 获取客户端字符内容
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 创建输出流
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String line;
            while ((line = br.readLine()) != null) {
                String capitalizedLine = line.toUpperCase() + "\n";
                dos.writeBytes(capitalizedLine);
            }
            System.out.println("======响应完毕！=======");
        }
    }

    @Test
    public void tcpClientTest() throws Exception {
        System.out.println("======请输入英文！======");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = new Socket(InetAddress.getLocalHost(), 12345);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        String line;
        // 发送内容
        while ((line = input.readLine()) != null) {
            dos.writeBytes(line);
        }
        // 接收内容
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            System.out.println("======服务端回执: " + inputLine + "======");
        }
        System.out.println("======传输完毕！======");
        socket.close();
    }
}
