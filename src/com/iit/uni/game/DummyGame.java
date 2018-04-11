package com.iit.uni.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import java.util.ArrayList;

import com.iit.uni.engine.*;

import com.iit.uni.engine.math.Vector2D;

import static org.lwjgl.glfw.GLFW.*;

public class DummyGame implements IGameLogic {

	private final Renderer renderer;
	private int direction = 0;

	// 2D Texture items
	private Texture2D[] backgrounds;
	private CCamera2D camera;

	// 2D GameObject items
	private GameObject2D gameItem;
	private GameObject2D itemsOnGround;
	// Global Scene manager
	public static C2DSceneManager sceneManager;

	private C2DScene scene;

	public DummyGame() {
		renderer = new Renderer();
	}

	private int a=5;
	
	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);


		/**
		 * Creating an animated game object
		 */
		
		gameItem = new GameObject2D();

		CSprite frameRunRight = new CSprite("textures/Run", 8, 200, 200);
		CSprite frameRunLeft = new CSprite("textures/Run_left", 8, 200, 200);
		CSprite idle = new CSprite("textures/Idle", 10, 200, 200);
		CSprite jumpRight = new CSprite("textures/Jump_right_", 10, 200, 200);
		CSprite jumpLeft = new CSprite("textures/Jump_left_", 10, 200, 200);

		gameItem.AddFrame(idle);
		gameItem.AddFrame(frameRunRight);
		gameItem.AddFrame(frameRunLeft);
		gameItem.AddFrame(jumpRight);
		gameItem.AddFrame(jumpLeft);

		gameItem.SetPosition(200, 504);

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
		
		//ItemsOnGround--------------------------------------------------------------------------------------------
		
		ArrayList<GameObject2D> AllItems = new ArrayList<>();
		//itemsOnGround = new GameObject2D();
		
		C2DGraphicsLayer itemLayer = new C2DGraphicsLayer();
		
		
		ArrayList<GameObject2D> gems = new ArrayList<>();
		CSprite gem = new CSprite("textures/items/gem", 4, 200, 200, 3);
	

		for(int i=0;i< a;i++){
			itemsOnGround = new GameObject2D();
			gem.SetScale(2);
			itemsOnGround.AddFrame(gem);
			itemsOnGround.SetPosition(500+i*130, 200);
			
			gems.add(itemsOnGround);
			
			//itemLayer.AddGameObject(itemsOnGround);
			//System.out.println(ar.get(i).GetPosition().getX());
		}
		
		
		
		ArrayList<GameObject2D> potion = new ArrayList<>();
		CSprite Potion = new CSprite("textures/items/glass02blue", 1, 200, 200);

		for(int i=0;i< a;i++){
			itemsOnGround = new GameObject2D();
			Potion.SetScale(2);
			itemsOnGround.AddFrame(Potion);
			itemsOnGround.SetPosition(450+i*150, 560);
			
			potion.add(itemsOnGround);
		}
		
		
		AllItems.addAll(gems);
		AllItems.addAll(potion);
		
		itemLayer.AddGameObject(AllItems);
		//ItemsOnGround--------------------------------------------------------------------------------------------END
		
		// register layer at the scene
		scene.RegisterLayer(layer0);
		scene.RegisterLayer(layer1);
		scene.RegisterLayer(layer2);
		scene.RegisterLayer(layer3);
		scene.RegisterLayer(layer4);
		scene.RegisterLayer(playerLayer);
		scene.RegisterLayer(itemLayer);
		

		// Register scene at the manager
		sceneManager.RegisterScene(scene);

		Texture2D background1 = new Texture2D();
		Texture2D background2 = new Texture2D();
		Texture2D background3 = new Texture2D();

		background1.CreateTexture("textures/background.png");
		background1.setPosition(-1280, 0, -1);

		background2.CreateTexture("textures/background.png");
		background2.setPosition(1280, 0, -1);

		background3.CreateTexture("textures/background.png");
		background3.setPosition(2560, 0, -1);

		backgrounds = new Texture2D[3];
		backgrounds[0] = background1;
		backgrounds[1] = background2;
		backgrounds[2] = background3;

		camera = new CCamera2D();
	}

	@Override
	public void input(Window window) {

		if (window.isKeyPressed(GLFW_KEY_UP)) {

			if (direction == 1) {
				gameItem.SetCurrentFrame(3);
			} else {
				gameItem.SetCurrentFrame(4);
			}
			Vector2D pos = gameItem.GetPosition();
			pos.y -= 5;
			gameItem.SetPosition(pos);

		} else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
			Vector2D pos = gameItem.GetPosition();
			pos.y += 5;
			gameItem.SetPosition(pos);

		} else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
			direction = -1;
			gameItem.SetCurrentFrame(2);
			Vector2D pos = gameItem.GetPosition();
			pos.x -= 5;
			camera.MoveLeft(5);
			gameItem.SetPosition(pos);
		} else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
			direction = 1;
			gameItem.SetCurrentFrame(1);
			Vector2D pos = gameItem.GetPosition();
			pos.x += 5;
			camera.MoveRight(5);
			gameItem.SetPosition(pos);
		}
	}

	@Override
	public void update(float interval) {

	}

	@Override
	public void render(Window window) {
		renderer.render(window, backgrounds, camera);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		for (int i = 0; i < backgrounds.length; i++)
			backgrounds[i].cleanup();
		gameItem.cleanUp();
	}
}
