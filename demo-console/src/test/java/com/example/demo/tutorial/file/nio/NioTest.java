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
import java.util.Iterator;
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
                    System.out.println("======完成第" + i + "次发送");
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

    @Test
    public void test3_server2() {
        ServerConnect.selector();
    }

    static class ServerConnect {
        private static final int BUF_SIZE = 1024;
        private static final int PORT = 12345;
        private static final int TIMEOUT = 3000;

        public static void handleAccept(SelectionKey key) throws IOException {
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_ACCEPT, ByteBuffer.allocate(BUF_SIZE));
            System.out.println("======连接完毕======");
        }

        public static void handleRead(SelectionKey key) throws IOException {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buff = (ByteBuffer) key.attachment();
            while (channel.read(buff) != -1) {
                buff.flip();
                while (buff.hasRemaining()) {
                    System.out.println((char) buff.get());
                }
                System.out.println("======读取完毕======");
                buff.clear();
            }
            channel.close();
        }

        public static void handleWrite(SelectionKey key) throws IOException {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buff = (ByteBuffer) key.attachment();
            buff.flip();
            while (buff.hasRemaining()) {
                channel.write(buff);
            }
            System.out.println("======写入完毕======");
            buff.compact();
        }

        public static void selector() {
            Selector selector = null;
            ServerSocketChannel channel = null;
            try {
                selector = Selector.open();
                channel = ServerSocketChannel.open();
                channel.socket().bind(new InetSocketAddress(PORT));
                // 关闭阻塞
                channel.configureBlocking(false);
                // 注册channel到selector
                channel.register(selector, SelectionKey.OP_ACCEPT);
                while (true) {
                    // 无就绪通道
                    if (selector.select(TIMEOUT) == 0) {
                        System.out.println("======无就绪channel======");
                        continue;
                    }
                    // 获取就绪通道
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            handleAccept(key);
                        }
                        if (key.isReadable()) {
                            handleRead(key);
                        }
                        if (key.isWritable() && key.isValid()) {
                            handleWrite(key);
                        }
                        if (key.isConnectable()) {
                            System.out.println("====== [isConnectable=true] ======");
                        }
                        // 需要处理完后手动移除
                        iterator.remove();
                        System.out.println("======channel处理完毕======");
                    }
                }
            } catch (Exception e) {
                logger.error("连接错误", e);
            } finally {
                if (selector != null) {
                    try {
                        selector.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

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
