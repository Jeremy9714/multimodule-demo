package com.example.demo.tutorial.patterns.structural.adapter.entity;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/10 8:39
 * @Version: 1.0
 */
public class Mp4Player implements AdvancedMediaPlayer{
    @Override
    public void playMp4(String fileName) {
        System.out.println("playing mp4: " + fileName);
    }

    @Override
    public void playVlc(String fileName) {
    }
}
