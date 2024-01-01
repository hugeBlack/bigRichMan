package org.dp.music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class RealAudioPlayer implements MusicPlayer {
    private Clip audioClip;
    private String musicFile[]={"./assets/music/goodtime.wav"};

    private int playId;

    RealAudioPlayer(){
        playId=0;
    }

    @Override
    public void play() {
        try {
            File audioFile = new File(musicFile[playId]);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.start();
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            System.out.println("Error occurred while playing audio.");
            ex.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
            audioClip.close();
            System.out.println("Playback stopped.");
        }
    }
}

