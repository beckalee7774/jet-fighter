package ui;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

// Represents a Sound effect
// got code from video: https://www.youtube.com/watch?v=qPVkRtuf9CQ
public class SoundEffect {
    Clip clip;

    //Modifies: this
    //Effects creates new file and opens clip
    public void setFile(String soundFileName) {
        try {
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    //Modifies: clip
    //Effects: plays clip
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }
}
