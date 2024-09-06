package com.example.demo.tutorial.algorithms.entity;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/5 10:23
 * @Version: 1.0
 */
public class RecursiveEntity {

    // 输入字符串
    static String s;
    // 拼接字符串长度
    static int n;

    public static int getResult() {
        // 校验合法性
        if (s.length() < n) {
            return 0;
        }
        char[] cArr = s.toCharArray();
        for (char c : cArr) {
            if (c < 'a' || c > 'z') {
                return 0;
            }
        }

        Arrays.sort(cArr);
        return dfs(cArr, -1, 0, new boolean[cArr.length], 0);
    }

    public static int dfs(char[] cArr, int prev, int level, boolean[] used, int count) {
        // 遍历到指定长度，结果加一
        if (level == n) {
            count++;
        }

        for (int i = 0; i < cArr.length; ++i) {
            // 跳过已使用元素
            if (used[i]) continue;
            // 相同的字符不能相邻
            if (prev >= 0 && cArr[prev] == cArr[i]) continue;
            // 去除重复排列
            if (i > 0 && cArr[i] == cArr[i - 1] && !used[i - 1]) continue;

            used[i] = true;
            count = dfs(cArr, i, level + 1, used, count);
            used[i] = false;
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("======输入字符串======");
        s = scanner.next();
        System.out.println("======输入拼接字符串长度======");
        n = scanner.nextInt();
        System.out.println(getResult());
    }
}
