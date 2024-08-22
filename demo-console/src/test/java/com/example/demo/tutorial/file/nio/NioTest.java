package com.example.demo.tutorial.file.nio;

import com.example.demo.console.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/21 15:25
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class NioTest {
    private static final Logger logger = LoggerFactory.getLogger(NioTest.class);

    @Test
    public void test1() {
        String filePath = "D:\\workplace\\test\\springboot_test3\\demo2\\demo-console\\src\\main\\resources\\files\\nio-test1.txt";
        String outputPath = "D:\\workplace\\test\\springboot_test3\\demo2\\demo-console\\src\\main\\resources\\files\\nio-test2.txt";

        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
             FileOutputStream fos = new FileOutputStream(outputPath);
        ) {
            // 获取channel
            inChannel = raf.getChannel();
            outChannel = fos.getChannel();
            // 获取buffer
            ByteBuffer buff = ByteBuffer.allocate(128);
            System.out.println("======开始读取======");
            while (inChannel.read(buff) != -1) {
                // 切换为写
                buff.flip();
                while (buff.hasRemaining()) {
//                    System.out.println((char) buff.get());
                    outChannel.write(buff); // 写入
                }
                // 清空缓冲区，切换为读
                buff.clear();
//                buff.compact();
            }
            System.out.println("======读取完成======");
        } catch (IOException e) {
            logger.error("nio测试报错", e);
        } finally {
            close(inChannel);
            close(outChannel);
        }
    }

    @Test
    public void test2() {
        String inputPath = "D:\\workplace\\test\\springboot_test3\\demo2\\demo-console\\src\\main\\resources\\files\\nio-test1.txt";
        String outputPath = "D:\\workplace\\test\\springboot_test3\\demo2\\demo-console\\src\\main\\resources\\files\\nio-test3.txt";
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try (RandomAccessFile in = new RandomAccessFile(inputPath, "rw");
             RandomAccessFile out = new RandomAccessFile(outputPath, "rw");
        ) {
            inChannel = in.getChannel();
            outChannel = out.getChannel();
            long position = 0L;
            long size = inChannel.size();
            System.out.println("======开始传输=====");
            // 通道之间数据传输
            outChannel.transferFrom(inChannel, position, size);
//            inChannel.transferTo(position, size, outChannel);
            System.out.println("======结束传输=====");
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            close(inChannel);
            close(outChannel);
        }
    }

    @Test
    public void test3_client() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try (SocketChannel channel = SocketChannel.open();) {
            channel.configureBlocking(false);
            // 连接
            channel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 12345));
            if (channel.finishConnect()) {
                int i = 0;
                while (i < 10) {
                    TimeUnit.SECONDS.sleep(1);
                    String info = "this is " + ++i + "th information input";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        System.out.println("======" + buffer + "======");
                        channel.write(buffer);
                    }
                    System.out.println("======完成第次" + i + "发送");
                }
            }
        } catch (Exception e) {
            logger.error("链接报错", e);
        }
    }

    @Test
    public void test3_server() {
        InputStream is = null;
        try (ServerSocket server = new ServerSocket(12345);) {
            int receMsgSize = 0;
            byte[] bytes = new byte[1024];
            while (true) {
                Socket clientSocket = server.accept();
                SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
                System.out.println("======客户端地址: " + clientAddress + " ======");
                is = clientSocket.getInputStream();
                while ((receMsgSize = is.read(bytes)) != -1) {
                    byte[] temp = new byte[receMsgSize];
                    // 拷贝
                    System.arraycopy(bytes, 0, temp, 0, receMsgSize);
                    System.out.println("======" + new String(temp) + " ======");
                }
            }
        } catch (Exception e) {
            logger.error("连接错误", e);
        } finally {
            closeStream(is);
        }
    }

    /**
     * selector
     */
    @Test
    public void test4() {
        int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        try (Selector selector = Selector.open()) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            // 通道必须为非阻塞模式
            ssc.configureBlocking(false);
            // 注册通道到选择器上
            SelectionKey selectionKey = ssc.register(selector, interestSet);

//            int interestOps = selectionKey.interestOps();
//            boolean isConnect = selectionKey.isConnectable();
        } catch (IOException e) {

        }
    }

    /**
     * 关闭channel
     *
     * @param channel
     */
    private static void close(FileChannel channel) {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
                logger.error("关闭失败", e);
            }
        }
    }

    private static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                logger.error("关闭失败", e);
            }
        }
    }
}
