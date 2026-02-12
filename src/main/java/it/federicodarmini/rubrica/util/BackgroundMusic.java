package it.federicodarmini.rubrica.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class BackgroundMusic {

    private static Clip clip;

    public static void playLoop(String resourcePath) {
        try {
            if (clip != null && clip.isRunning()) {
                return;
            }

            URL url = BackgroundMusic.class.getResource(resourcePath);
            if (url == null) {
                System.err.println("File audio non trovato: " + resourcePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
