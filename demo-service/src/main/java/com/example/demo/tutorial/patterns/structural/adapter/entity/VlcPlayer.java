package com.example.demo.tutorial.patterns.structural.adapter.entity;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/10 8:39
 * @Version: 1.0
 */
public class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playMp4(String fileName) {
    }

    @Override
    public void playVlc(String fileName) {
        System.out.println("playing vlc: " + fileName);
    }
}
