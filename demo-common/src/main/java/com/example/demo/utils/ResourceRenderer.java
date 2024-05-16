package com.example.demo.utils;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/16 16:46
 * @Version: 1.0
 */
public class ResourceRenderer {
    public ResourceRenderer() {
    }

    public static InputStream resourceLoader(String fileFullPath) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(fileFullPath).getInputStream();
    }
}
