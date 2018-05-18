package com.iit.uni.game;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class Zene extends Thread {
    private FileInputStream backgroundmusic;
    private Player player;
    private Boolean running = new Boolean(true);
    public static Clip clip;
    public static Mixer mixer;


    public void run() {
        Mixer.Info[] mixinfos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixinfos[0]);
        DataLine.Info datainfo = new DataLine.Info(Clip.class, null);


        while (running) {
            try {
                if (DummyGame.soundStatement == 1) {
                    backgroundmusic = new FileInputStream("sounds/mario.wav");
                    player = new Player(backgroundmusic);
                    player.play();
                } else if(DummyGame.soundStatement == 2) {
                    backgroundmusic = new FileInputStream("sounds/boss.wav");
                    player = new Player(backgroundmusic);
                    player.play();
                } else if(DummyGame.soundStatement == 3) {
                    File hang = new File("sounds/jump.wav");
                    clip = (Clip) mixer.getLine(datainfo);
                    clip.open(AudioSystem.getAudioInputStream(hang));
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength()/1000);
                } else if(DummyGame.soundStatement == 4) {
                    File hang = new File("sounds/start.wav");
                    clip = (Clip) mixer.getLine(datainfo);
                    clip.open(AudioSystem.getAudioInputStream(hang));
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength()/1000);
                } else if(DummyGame.soundStatement == 5) {
                    File hang = new File("sounds/die.wav");
                    clip = (Clip) mixer.getLine(datainfo);
                    clip.open(AudioSystem.getAudioInputStream(hang));
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength()/1000);
                } else if(DummyGame.soundStatement == 6) {
                    backgroundmusic = new FileInputStream("sounds/win.wav");
                    player = new Player(backgroundmusic);
                    player.play();
                } else if(DummyGame.soundStatement == 99) {
                    File hang = new File("sounds/jump.wav");
                    clip = (Clip) mixer.getLine(datainfo);
                    clip.open(AudioSystem.getAudioInputStream(hang));
                    //clip.start();
                    Thread.sleep(clip.getMicrosecondLength() / 1000);
                    backgroundmusic = new FileInputStream("sounds/boss.wav");
                    player = new Player(backgroundmusic);
                } else if(DummyGame.soundStatement == 0){

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e){
                e.printStackTrace();
            } catch (java.io.IOException e){
                e.printStackTrace();
            } catch (javax.sound.sampled.LineUnavailableException e){
                e.printStackTrace();
            }catch (java.lang.InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void interrupt(){
        running= false;
    }

}