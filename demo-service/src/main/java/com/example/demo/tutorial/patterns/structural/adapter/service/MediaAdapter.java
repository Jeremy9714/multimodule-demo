package com.example.demo.tutorial.patterns.structural.adapter.service;

import com.example.demo.tutorial.patterns.structural.adapter.entity.AdvancedMediaPlayer;
import com.example.demo.tutorial.patterns.structural.adapter.entity.Mp4Player;
import com.example.demo.tutorial.patterns.structural.adapter.entity.VlcPlayer;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/10 8:41
 * @Version: 1.0
 */
public class MediaAdapter implements MediaPlayer {

    private AdvancedMediaPlayer advancedMediaPlayer;

    public MediaAdapter(String audioType) {
        if ("vlc".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer = new VlcPlayer();
        } else if ("mp4".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        if ("vlc".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer.playVlc(fileName);
        } else if ("mp4".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer.playMp4(fileName);
        }
    }
}
