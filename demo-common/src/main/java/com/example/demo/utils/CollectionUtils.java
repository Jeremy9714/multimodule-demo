package com.example.demo.utils;

import java.util.Iterator;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/14 15:40
 * @Version: 1.0
 */
public class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> String join(Iterable<T> collection, String conjunction) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        Object item;
        for(Iterator var4 = collection.iterator(); var4.hasNext(); sb.append(item)) {
            item = var4.next();
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(conjunction);
            }
        }

        return sb.toString();
    }
}
