package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;

public class Sound {
    Clip musicClip;
    URL url[] = new URL[10];
    

    public Sound(){
        url[0] = getClass().getResource("/sounds/Game_Theme.wav");
        url[1] = getClass().getResource("/sounds/delete line.wav");
        url[2] = getClass().getResource("/sounds/gameover.wav");
        url[3] = getClass().getResource("/sounds/rotation.wav");
        url[4] = getClass().getResource("/sounds/touch floor.wav");

    }

    public void play(int i, boolean music){
        try {
            // audio input stream is loaded from the chosen wav file
            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();
            if(music){
                musicClip = clip;
            }
            // open the audio clip by passing the audio input stream
            clip.open(ais);
            // when the clip is finished close the clip to free up memory
            // otherwise the memory usage increases every time a clip is opened
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if(event.getType()== Type.STOP){
                        clip.close();
                    }
                }
            
            });
            ais.close();
            clip.start();
        } catch (Exception e) {
            System.err.println("Invalid File Type");
        }
    }
    public void loop(){
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        musicClip.stop();
        musicClip.close();
    }
}
