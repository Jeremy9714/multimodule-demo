package com.example.demo.tutorial.algorithms;

import com.example.demo.console.Application;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/3 16:15
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
public class AlgTest {

    static Scanner scanner;

    @BeforeEach
    public void init() {
        scanner = new Scanner(System.in);
    }

    @Test
    public void multiTaskTest() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("======请输入======");
//        String line = scanner.nextLine();
        String[][] relations = Arrays.stream(scanner.nextLine().split(" ")).map(s -> s.split("->")).toArray(String[][]::new);
//        for (int i = 0; i < set.length; ++i) {
//            String[] tmpSet = set[i];
//            for (int j = 0; j < tmpSet.length; ++j) {
//                System.out.println(tmpSet[j]);
//            }
//            System.out.println("======");
//        }

//        System.out.println(Arrays.deepToString(relations));

        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, ArrayList<String>> next = new HashMap<>();

        for (String[] relation : relations) {
            String a = relation[0];
            String b = relation[1];

            inDegree.put(b, inDegree.getOrDefault(b, 0));
            inDegree.put(a, inDegree.getOrDefault(a, 0) + 1);

            next.putIfAbsent(b, new ArrayList<>());
            next.get(b).add(a);
            next.put(a, new ArrayList<>());
        }

        // 收集第一层入度为0的点
        List<String> queue = new ArrayList<>();
        for (String task : inDegree.keySet()) {
            if (inDegree.get(task) == 0) {
                queue.add(task);
            }
        }

        // 记录任务执行的顺序
        StringJoiner sj = new StringJoiner(" ");
        while (queue.size() > 0) {
            // 按照字母排序
            queue.sort(String::compareTo);

            List<String> newQueue = new ArrayList<>();
            for (String fa : queue) {
                sj.add(fa);

                for (String ch : next.get(fa)) {
                    // fa执行完，入度-1
                    inDegree.put(ch, inDegree.get(ch) - 1);

                    if (inDegree.get(ch) == 0) {
                        newQueue.add(ch);
                    }
                }
            }
            queue = newQueue;
        }
        System.out.println(sj);
    }

    /**
     * 动态规划
     * 一根X米长的树木，伐木工切割成不同长度的木材后进行交易，交易价格为每根木头长度的乘积。
     * 规定切割后的每根木头长度都为正整数；也可以不切割，直接拿整根树木进行交易。
     * 请问伐木工如何尽量少的切割，才能使收益最大化？
     */
    @Test
    public void dpTest1() {
        @Data
        class Wood {
            int profit; // 最大收益
            List<Integer> slices = new ArrayList<>(); // 切割状态
        }
        System.out.println("======请输入长度======");
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        Wood[] dp = new Wood[length + 1];

        // 初始化最大收益为不切割的状态
        for (int i = 0; i <= length; ++i) {
            dp[i] = new Wood();
            dp[i].setProfit(i);
            dp[i].slices.add(i);
        }

        // 从长度为2的木材开始切割
        for (int i = 2; i <= length; ++i) {
            for (int j = 1; j < i; ++j) {
                int profit = dp[j].getProfit() * dp[i - j].getProfit();
                if (profit > dp[i].getProfit() ||
                        (profit == dp[i].getProfit() && dp[i].getSlices().size() > dp[j].getSlices().size() + dp[i - j].getSlices().size())) {
                    dp[i].setProfit(profit);
                    dp[i].getSlices().clear();
                    dp[i].getSlices().addAll(dp[j].getSlices());
                    dp[i].getSlices().addAll(dp[i - j].getSlices());
                }
            }
        }

        // 升序排序
//        dp[length].getSlices().sort((a, b) -> b - a);
        dp[length].getSlices().sort(Comparator.comparingInt(a -> a));

        StringJoiner sj = new StringJoiner(" ");
        for (int slice : dp[length].getSlices()) {
            sj.add(slice + "");
        }

        System.out.println(sj);
    }

    /**
     * 镜面反射
     */
    @Test
    public void reflectionTest() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("======输入x轴长度======");
        int w = scanner.nextInt();
        System.out.println("======输入y轴长度======");
        int h = scanner.nextInt();
        System.out.println("======输入x坐标======");
        int x = scanner.nextInt();
        System.out.println("======输入y坐标======");
        int y = scanner.nextInt();
        System.out.println("======输入x轴速度======");
        int sx = scanner.nextInt();
        System.out.println("======输入y轴速度======");
        int sy = scanner.nextInt();
        System.out.println("======输入时间======");
        int t = scanner.nextInt();

        char[][] matrix = new char[h][w];
        for (int i = 0; i < h; ++i) {
            System.out.println("请输入第" + (i + 1) + "行矩阵，宽度为" + w);
            matrix[i] = scanner.nextLine().toCharArray();
        }

        int ans = 0;

        while (t >= 0) {
            if (matrix[y][x] == '1') {
                ans++;
            }

            y += sy;
            x += sx;

            if (x < 0) {
                x = 1;
                sx = -sx;
            } else if (x >= w) {
                x = w - 2;
                sx = -sx;
            }
            if (y < 0) {
                y = 1;
                sy = -sy;
            } else if (y >= h) {
                y = h - 2;
                sy = -sy;
            }

            --t;
        }

        System.out.println("======经过1点的个数为" + ans + "======");
    }

    /**
     * Wonderland是小王居住地一家很受欢迎的游乐园。Wonderland目前有4种售票方式，
     * 分别为一日票（1天）、三日票（3天）、周票（7天）和月票（30天）。
     * 每种售票方式的价格由一个数组给出，每种票据在票面时限内可以无限制地进行游玩。例如：
     * 小王在第10日买了一张三日票，小王可以在第10日、第11日和第12日进行无限制地游玩。
     * 小王计划在接下来一年多次游玩该游乐园。小王计划地游玩日期将由一个数组给出。
     * 现在，请您根据给出地售票价格数组和小王计划游玩日期数组，返回游玩计划所需要地最低消费。
     */
    @Test
    public void dpTest2() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("======输入价格======");
        int[] costs = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        System.out.println("======输入日期======");
        int[] days = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 最大游玩日
        int maxDay = days[days.length - 1];

        // dp[i] 表示前i天中最小花费
        // dp[0] = 0
        int[] dp = new int[maxDay + 1];

        int index = 0;

        for (int i = 1; i <= maxDay; ++i) {
            // 如果为游玩日
            if (i == days[index]) {
                // 一日票
                int buy1 = dp[i - 1] + costs[0];
                // 三日票
                int buy3 = ((i >= 3) ? dp[i - 3] : 0) + costs[1];
                // 周票
                int buy7 = ((i >= 7) ? dp[i - 7] : 0) + costs[2];
                // 月票
                int buy30 = ((i >= 30) ? dp[i - 30] : 0) + costs[3];

                dp[i] = Math.min(Math.min(Math.min(buy1, buy3), buy7), buy30);

                ++index;
            } else {
                dp[i] = dp[i - 1];
            }
        }
        System.out.println("======价格为: " + dp[maxDay] + "======");
    }

    /**
     * 双端队列
     */
    @Test
    public void doubleQueueTest() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("======输入放入顺序======");
        int[] inputs = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        System.out.println("======输入取出顺序======");
        int[] outputs = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 双端队列
        LinkedList<Integer> queue = new LinkedList<>();
        // outputs编号
        int index = 0;
        StringBuilder sb = new StringBuilder();

        for (int input : inputs) {
            queue.addLast(input);
            while (!queue.isEmpty() && index < outputs.length) {
                int left = queue.getFirst(); //左侧取出
                int right = queue.getLast(); //右侧取出

                // 取左边
                if (left == outputs[index]) {
                    sb.append("L");
                    queue.removeFirst();
                    index++;
                } else if (right == outputs[index]) {// 取右边
                    sb.append("R");
                    queue.removeLast();
                    index++;
                } else {
                    break; //无匹配则跳出
                }
            }
        }

        if (index != outputs.length) {
            System.out.println("NO");
        } else {
            System.out.println("======结果: " + sb + "======");
        }
    }

    /**
     * 已知火星人使用的运算符为#、$，其与地球人的等价公式如下：
     * x#y = 4*x+3*y+2
     * x$y = 2*x+y+3
     * 现有一段火星人的字符串报文，请你来翻译并计算结果。
     */
    @Test
    public void calculateTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("======输入表达式======");
        String inputStr = scanner.next();

        Pattern p = Pattern.compile("(\\d+)#(\\d+)");
        while (true) {
            Matcher matcher = p.matcher(inputStr);
            if (!matcher.find()) break;

            String subStr = matcher.group(0);
            long x = Long.parseLong(matcher.group(1));
            long y = Long.parseLong(matcher.group(2));
            inputStr = inputStr.replaceFirst(subStr, 4 * x + 3 * y + 2 + "");
        }
        long result = Arrays.stream(inputStr.split("\\&"))
                .map(Long::parseLong)
                .reduce((x, y) -> 2 * x + y + 3)
                .orElse(0L);

        System.out.println("======结果: " + result + "======");
    }

    /**
     * 螺旋矩阵问题
     */
    @Test
    public void matrixTest() {
        System.out.println("======输入个数======");
        int n = scanner.nextInt();
        System.out.println("======输入行数======");
        int m = scanner.nextInt();
        int step = 1;

        // 计算列数
        int k = (int) Math.ceil(n / m);

        int[][] matrix = new int[m][k];

        // 初始位置
        int x = 0;
        int y = 0;
        while (step <= n) {

            // 向右
            while (y < k && matrix[x][y] == 0 && step <= n) {
                matrix[x][y++] = step;
            }

            // 行遍历完处于越界位置k
            y -= 1;
            // 螺旋顺序，向下一格
            x += 1;

            // 向下
            while (x < m && matrix[x][y] == 0 && step <= n) {
                matrix[x++][y] = step;
            }

            // 列遍历完处于越界位置m
            x -= 1;
            // 螺旋顺序,向左一格
            y -= 1;

            // 向左
            while (y >= 0 && matrix[x][y] == 0 && step <= n) {
                matrix[x][y--] = step;
            }

            // 行遍历越界
            y += 1;
            // 螺旋顺序,向上一格
            x -= 1;

            // 向上
            while (x >= 0 && matrix[x][y] == 0 && step <= n) {
                matrix[x--][y] = step;
            }

            // 列遍历越界
            x += 1;
            // 螺旋顺序,向右一格
            

        }

    }
}
