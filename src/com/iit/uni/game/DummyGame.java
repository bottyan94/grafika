package com.iit.uni.game;

import com.iit.uni.engine.C2DGraphicsLayer;
import com.iit.uni.engine.C2DScene;
import com.iit.uni.engine.C2DSceneManager;
import com.iit.uni.engine.CSprite;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.IGameLogic;
import com.iit.uni.engine.Texture2D;
import com.iit.uni.engine.Window;
import com.iit.uni.engine.math.Vector2D;
import java.awt.event.KeyEvent;

import static org.lwjgl.glfw.GLFW.*;

public class DummyGame implements IGameLogic {

	private int down = 0;
	private int up = 0;
	private int right = 0;
	private int left = 0;

	private final Renderer renderer;
	private int direction = 0;

	// 2D GameObject items
	private GameObject2D gameItem;
	private GameObject2D gameItemMonster;
	// Global Scene manager
	public static C2DSceneManager sceneManager;

	private C2DScene scene;

	public DummyGame() {
		renderer = new Renderer();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);

		/**
		 * Creating an animated game object
		 */
		
		
		gameItem = new GameObject2D();

		CSprite frameRunRight = new CSprite("textures/ninja/Run", 10, 200, 200);
		CSprite frameRunLeft = new CSprite("textures/ninja/Run_left", 10, 200, 200);
		CSprite idle = new CSprite("textures/ninja/Idle", 10, 200, 200);
		CSprite jumpRight = new CSprite("textures/ninja/Jump", 10, 200, 200);
		CSprite jumpLeft = new CSprite("textures/ninja/Jump_left", 10, 200, 200);
		CSprite slide = new CSprite("textures/ninja/Slide", 10, 200, 200);
		CSprite slideLeft = new CSprite("textures/ninja/Slide_left", 10, 200, 200);
		CSprite idleLeft = new CSprite("textures/ninja/Idle_left", 10, 200, 200);

		idle.SetScale(0.5f);
		frameRunLeft.SetScale(0.5f);
		frameRunRight.SetScale(0.5f);
		jumpLeft.SetScale(0.5f);
		jumpRight.SetScale(0.5f);
		slide.SetScale(0.5f);
		slideLeft.SetScale(0.5f);
		idleLeft.SetScale(0.5f);

		gameItem.AddFrame(idle);
		gameItem.AddFrame(frameRunRight);
		gameItem.AddFrame(frameRunLeft);
		gameItem.AddFrame(jumpRight);
		gameItem.AddFrame(jumpLeft);
		gameItem.AddFrame(slide);
		gameItem.AddFrame(slideLeft);
		gameItem.AddFrame(idleLeft);

		gameItem.SetPosition(200, 154);

		sceneManager = new C2DSceneManager();
		scene = new C2DScene();

		// Create a background texture
		Texture2D background = new Texture2D();
		background.CreateTexture("textures/background/layer_sd_01.png");

		// Create a cloud layer
		Texture2D clouds = new Texture2D();
		clouds.CreateTexture("textures/background/layer_sd_02.png");

		// Create a mountain layer
		Texture2D mountains = new Texture2D();
		mountains.CreateTexture("textures/background/layer_sd_03.png");

		// Create a tree layer
		Texture2D trees = new Texture2D();
		trees.CreateTexture("textures/background/layer_sd_04.png");

		// Create a ground layer
		Texture2D ground = new Texture2D();
		ground.CreateTexture("textures/background/layer_sd_05.png");

		// Create graphics layer
		C2DGraphicsLayer layer0 = new C2DGraphicsLayer();
		layer0.AddTexture(background);

		// Create graphics layer
		C2DGraphicsLayer layer1 = new C2DGraphicsLayer();
		layer1.AddTexture(clouds);

		// Create graphics layer
		C2DGraphicsLayer layer2 = new C2DGraphicsLayer();
		layer2.AddTexture(mountains);

		C2DGraphicsLayer layer3 = new C2DGraphicsLayer();
		layer3.AddTexture(trees);

		C2DGraphicsLayer layer4 = new C2DGraphicsLayer();
		layer4.AddTexture(ground);

		C2DGraphicsLayer playerLayer = new C2DGraphicsLayer();
		playerLayer.AddGameObject(gameItem);
		
		C2DGraphicsLayer monsterLayer = new C2DGraphicsLayer();
		playerLayer.AddGameObject(gameItemMonster);
		
		// register layer at the scene
		scene.RegisterLayer(layer0);
		scene.RegisterLayer(layer1);
		scene.RegisterLayer(layer2);
		scene.RegisterLayer(layer3);
		scene.RegisterLayer(layer4);
		scene.RegisterLayer(playerLayer);
		scene.RegisterLayer(monsterLayer);

		// Register scene at the manager
		sceneManager.RegisterScene(scene);
	}

	@Override
	public void input(Window window) {


		if(window.isKeyPressed(GLFW_KEY_RIGHT)) {
			right = 1;
		} else {
			right = 0;
		}

		if(window.isKeyPressed(GLFW_KEY_LEFT)) {
			left = 1;
		} else {
			left = 0;
		}

		if(window.isKeyPressed(GLFW_KEY_DOWN)) {
			down = 1;
		} else {
			down = 0;
		}

		if (down == 1 && right ==1) {

		}

		if (left == 1) {
			direction = -1;
			gameItem.SetCurrentFrame(2);
			Vector2D pos = gameItem.GetPosition();
			pos.x -= 5;
			gameItem.SetPosition(pos);
		}

		if (right == 1) {
			direction = 1;
			gameItem.SetCurrentFrame(1);
			Vector2D pos = gameItem.GetPosition();
			pos.x += 5;
			gameItem.SetPosition(pos);
		}


		if (right==0 && left ==0 && down == 0) {
			if (direction == 1) {
				gameItem.SetCurrentFrame(0);
			} else {
				gameItem.SetCurrentFrame(7);
			}
		}


	}

	@Override
	public void update(float interval) {

	}

	@Override
	public void render(Window window) {
		renderer.render(window);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		gameItem.cleanUp();
	}
}
