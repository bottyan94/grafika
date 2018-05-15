package com.iit.uni.game;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Zene extends Thread {
    private FileInputStream backgroundmusic;
    private Player player;
    private Boolean running = new Boolean(true);

    public void run() {
        while (running) {
            try {
                backgroundmusic = new FileInputStream(getName());
                player = new Player(backgroundmusic);
                player.play();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    public void interrupt(){
        running= false;
    }

}