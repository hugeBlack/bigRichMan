package org.dp.music;

public class AudioPlayerProxy implements MusicPlayer {
    private RealAudioPlayer realAudioPlayer;

    public AudioPlayerProxy() {
        this.realAudioPlayer = new RealAudioPlayer();
        this.realAudioPlayer.play();
    }

    @Override
    public void play() {

        System.out.println("通过代理访问 RealAudioPlayer...");
        realAudioPlayer.play( );
    }

    @Override
    public void stop() {

        realAudioPlayer.stop();
    }
}
