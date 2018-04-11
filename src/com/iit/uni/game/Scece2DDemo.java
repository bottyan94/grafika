package com.iit.uni.game;

import com.iit.uni.engine.GameEngine;
import com.iit.uni.engine.IGameLogic;



//Hello
public class Scece2DDemo {

	public static void main(String[] args) {
		try {
			boolean vSync = true;
			IGameLogic gameLogic = new DummyGame();
			GameEngine gameEng = new GameEngine("2D Scene Demo", 1280, 720, vSync, gameLogic);
			gameEng.start();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		}
	}
}