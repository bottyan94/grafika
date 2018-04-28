package com.iit.uni.game;


import com.iit.uni.engine.C2DGraphicsLayer;
import com.iit.uni.engine.C2DScene;
import com.iit.uni.engine.C2DSceneManager;
import com.iit.uni.engine.CSprite;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.IGameLogic;
import com.iit.uni.engine.Texture2D;
import com.iit.uni.engine.Window;

import java.util.ArrayList;

import com.iit.uni.engine.*;
import com.iit.uni.engine.math.Vector2D;

import static org.lwjgl.glfw.GLFW.*;


public class DummyGame implements IGameLogic {

	private int down = 0;
	private int up = 0;
	private int right = 0;
	private int left = 0;

	private float speedY = 0f;
	private float speedX = 0f;
	private float gravity = 2f;
	private int spacePushed = 0;

	private final Renderer renderer;
	private int direction = 0;

	// 2D Texture items
	private Texture2D[] backgrounds;
	private CCamera2D camera;
	private float recentcameraX;
	private float newcameraX;

	// 2D GameObject items
	public GameObject2D gameItem;
	private GameObject2D itemsOnGround;
	private GameObject2D platform;
	private GameObject2D testfold;
	private GameObject2D testfold2;

	private ArrayList<GameObject2D> AllItems;
	private ArrayList<GameObject2D> Alltestfold;
	private ArrayList<GameObject2D> AllLebegoFold;


	private int ID = 0;

	// Global Scene manager
	public static C2DSceneManager sceneManager;

	private C2DScene scene;

	public DummyGame() {
		renderer = new Renderer();
	}

	private int a = 5;

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);


		/**
		 * Creating an animated game object
		 */


		platform = new GameObject2D();
		AllLebegoFold = new ArrayList<>();
		CSprite lebegoBal = new CSprite("textures/platform/Plat1", 1, 128, 128);
		platform.AddFrame(lebegoBal);
		platform.SetPosition(500, 250);
		//platform.SetBoundingBox(platform.GetHeight(), platform.GetWidth());
		AllLebegoFold.add(platform);

		platform = new GameObject2D();
		CSprite lebegoKozep = new CSprite("textures/platform/Plat2", 1, 128, 128);
		platform.AddFrame(lebegoKozep);
		platform.SetPosition(628, 250);
		//platform.SetBoundingBox(platform.GetHeight(), platform.GetWidth());
		AllLebegoFold.add(platform);

		platform = new GameObject2D();
		CSprite lebegoJob = new CSprite("textures/platform/Plat3", 1, 128, 128);
		platform.AddFrame(lebegoJob);
		platform.SetPosition(756, 250);
		//platform.SetBoundingBox(platform.GetHeight(), platform.GetWidth());
		AllLebegoFold.add(platform);


		Alltestfold = new ArrayList<>();
		//asd


		CSprite test = new CSprite("textures/platform/Tile (2)", 1, 128, 128);
		CSprite testBal = new CSprite("textures/platform/Tile (1)", 1, 128, 128);
		CSprite testJob = new CSprite("textures/platform/Tile (3)", 1, 128, 128);
		for (int i = 1; i < 9; i++) {
			if (i == 1) {
				testfold = new GameObject2D();
				testfold.AddFrame(testBal);
				testfold.SetPosition(0 + i * 128, 595);
				testfold.SetBoundingBox(testfold.GetHeight(), testfold.GetWidth());

				Alltestfold.add(testfold);

			} else if (i == 8) {
				testfold = new GameObject2D();
				testfold.AddFrame(testJob);
				testfold.SetPosition(0 + i * 128, 595);
				testfold.SetBoundingBox(testfold.GetHeight(), testfold.GetWidth());

				Alltestfold.add(testfold);

			} else
				testfold = new GameObject2D();
			testfold.AddFrame(test);
			testfold.SetPosition(0 + i * 128, 595);
			testfold.SetBoundingBox(testfold.GetHeight(), testfold.GetWidth());

			Alltestfold.add(testfold);
		}


		gameItem = new GameObject2D();


		CSprite frameRunRight = new CSprite("textures/ninja/Run", 10, 200, 200);
		CSprite frameRunLeft = new CSprite("textures/ninja/Run_left", 10, 200, 200);
		CSprite idle = new CSprite("textures/ninja/Idle", 10, 200, 200);
		CSprite jumpRight = new CSprite("textures/ninja/Jump", 10, 200, 200);
		CSprite jumpLeft = new CSprite("textures/ninja/Jump_left", 10, 200, 200);
		CSprite slide = new CSprite("textures/ninja/Slide", 10, 200, 200);
		CSprite slideLeft = new CSprite("textures/ninja/Slide_left", 10, 200, 200);
		CSprite idleLeft = new CSprite("textures/ninja/Idle_left", 10, 200, 200);


		/*idle.SetScale(0.5f);
		frameRunLeft.SetScale(0.5f);
		frameRunRight.SetScale(0.5f);
		jumpLeft.SetScale(0.5f);
		jumpRight.SetScale(0.5f);
		slide.SetScale(0.5f);
		slideLeft.SetScale(0.5f);
		idleLeft.SetScale(0.5f);*/

		gameItem.AddFrame(idle);
		gameItem.AddFrame(frameRunRight);
		gameItem.AddFrame(frameRunLeft);
		gameItem.AddFrame(jumpRight);
		gameItem.AddFrame(jumpLeft);
		gameItem.AddFrame(slide);
		gameItem.AddFrame(slideLeft);
		gameItem.AddFrame(idleLeft);


		gameItem.SetPosition(400, 235);
		gameItem.SetScale(0.5f);
		gameItem.SetBoundingBox(gameItem.GetHeight(), gameItem.GetWidth());

		sceneManager = new C2DSceneManager();
		scene = new C2DScene();

		// Create a background texture
		Texture2D background = new Texture2D();
		background.CreateTexture("textures/background/layer_sd_01.png");
		background.setPosition(0, 0, -1);
		Texture2D background1 = new Texture2D();
		background1.CreateTexture("textures/background/layer_sd_01.png");
		background1.setPosition(1280, 0, -1);
		Texture2D background2 = new Texture2D();
		background2.CreateTexture("textures/background/layer_sd_01.png");
		background2.setPosition(2560, 0, -1);

		// Create a cloud layer
		/*Texture2D clouds = new Texture2D();
		clouds.CreateTexture("textures/background/layer_sd_02.png");
		Texture2D clouds1 = new Texture2D();
		clouds1.CreateTexture("textures/background/layer_sd_02.png");
		clouds1.setPosition(1280, 0, 0);
		Texture2D clouds2 = new Texture2D();
		clouds2.CreateTexture("textures/background/layer_sd_02.png");
		clouds2.setPosition(2560, 0, 0);
		// Create a mountain layer
		Texture2D mountains = new Texture2D();
		mountains.CreateTexture("textures/background/layer_sd_03.png");
		Texture2D mountains1 = new Texture2D();
		mountains1.setPosition(1280, 0, 0);
		mountains1.CreateTexture("textures/background/layer_sd_03.png");
		Texture2D mountains2 = new Texture2D();
		mountains2.CreateTexture("textures/background/layer_sd_03.png");
		mountains2.setPosition(2560, 0, 0);*/

		// Create a tree layer
		Texture2D trees = new Texture2D();
		trees.CreateTexture("textures/background/layer_sd_04.png");
		Texture2D trees1 = new Texture2D();
		trees1.CreateTexture("textures/background/layer_sd_04.png");
		trees1.setPosition(1280, 0, 0);
		Texture2D trees2 = new Texture2D();
		trees2.CreateTexture("textures/background/layer_sd_04.png");
		trees2.setPosition(2506, 0, 0);

		// Create a ground layer
		Texture2D ground = new Texture2D();
		ground.CreateTexture("textures/background/layer_sd_05.png");
		ground.setPosition(0, 0, -1);
		Texture2D ground1 = new Texture2D();
		ground1.CreateTexture("textures/background/layer_sd_05.png");
		ground1.setPosition(1280, 0, -1);
		Texture2D ground2 = new Texture2D();
		ground2.CreateTexture("textures/background/layer_sd_05.png");
		ground2.setPosition(2560, 0, -1);

		// Create graphics layer
		C2DGraphicsLayer layer0 = new C2DGraphicsLayer();
		layer0.AddTexture(background);

		// Create graphics layer
		C2DGraphicsLayer layer1 = new C2DGraphicsLayer();
		//layer1.AddTexture(clouds);

		// Create graphics layer
		C2DGraphicsLayer layer2 = new C2DGraphicsLayer();
		//layer2.AddTexture(mountains);

		C2DGraphicsLayer layer3 = new C2DGraphicsLayer();
		//layer3.AddTexture(trees);

		C2DGraphicsLayer layer4 = new C2DGraphicsLayer();
		layer4.AddTexture(ground);

		C2DGraphicsLayer playerLayer = new C2DGraphicsLayer();
		playerLayer.AddGameObject(gameItem);
		playerLayer.AddGameObject(platform);
		playerLayer.AddGameObject(Alltestfold);
		playerLayer.AddGameObject(AllLebegoFold);

		//ItemsOnGround--------------------------------------------------------------------------------------------

		AllItems = new ArrayList<>();
		//itemsOnGround = new GameObject2D();

		C2DGraphicsLayer itemLayer = new C2DGraphicsLayer();


		ArrayList<GameObject2D> gems = new ArrayList<>();
		CSprite gem = new CSprite("textures/items/gem", 4, 200, 200, 3);


		for (int i = 0; i < a; i++) {

			itemsOnGround = new GameObject2D();
			gem.SetScale(2);
			itemsOnGround.AddFrame(gem);
			itemsOnGround.SetPosition(500 + i * 130, 200);
			itemsOnGround.SetID(1);
			ID++;
			//itemsOnGround.SetBoundingBox(gem.GetHeight(), gem.GetWidth());

			gems.add(itemsOnGround);

			//itemLayer.AddGameObject(itemsOnGround);
			//System.out.println(ar.get(i).GetPosition().getX());
		}


		/*gyemant = new GameObject2D();
		gyemant.AddFrame(gem);
		gyemant.SetPosition(500, 200);
		gyemant.SetBoundingBox(gem.GetHeight(), gem.GetWidth());*/


		ArrayList<GameObject2D> potion = new ArrayList<>();
		CSprite Potion = new CSprite("textures/items/glass02blue", 1, 200, 200);

		for (int i = 0; i < a; i++) {
			itemsOnGround = new GameObject2D();
			Potion.SetScale(1);
			itemsOnGround.AddFrame(Potion);
			itemsOnGround.SetPosition(450 + i * 150, 560);
			itemsOnGround.SetID(2);
			ID++;

			//itemsOnGround.SetBoundingBox(Potion.GetHeight(), Potion.GetWidth());

			potion.add(itemsOnGround);
		}


		AllItems.addAll(gems);
		AllItems.addAll(potion);


		for (int i = 0; i < AllItems.size(); i++) {
			System.out.println("ID:" + AllItems.get(i).GetID());
		}

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

		backgrounds = new Texture2D[0];
		/*backgrounds[0] = background;
		backgrounds[1] = background1;
		backgrounds[2] = background2;
		backgrounds[3] = clouds;
		backgrounds[4] = clouds1;
		backgrounds[5] = clouds2;
		backgrounds[6] = mountains;
		backgrounds[7] = mountains1;
		backgrounds[8] = mountains2;
		backgrounds[9] = trees;
		backgrounds[10] = trees1;
		backgrounds[11] = trees2;
		backgrounds[12] = ground;
		backgrounds[13] = ground1;
		backgrounds[14] = ground2;*/


		camera = new CCamera2D();

	}

	@Override
	public void input(Window window) {


		if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
			right = 1;
		} else {
			right = 0;
		}

		if (window.isKeyPressed(GLFW_KEY_LEFT)) {
			left = 1;
		} else {
			left = 0;
		}

		if (window.isKeyPressed(GLFW_KEY_DOWN)) {
			down = 1;
		} else {
			down = 0;
		}

		if (window.isKeyPressed(GLFW_KEY_SPACE) && spacePushed == 0) {
			speedY = -25;
			up = 1;
			spacePushed = 1;
		}

		/*if (down == 1 && right ==1) {

		}*/

		if (left == 1) {
			direction = -1;
			if (spacePushed == 0) {
				gameItem.SetCurrentFrame(2);
			}
			Vector2D pos = gameItem.GetPosition();
			if (gameItem.GetCurrentBBox().GetMinPoint().x >= 5) {
				pos.x -= 5f;
				recentcameraX = camera.GetX();
				camera.MoveLeft(5f);
				newcameraX = camera.GetX();
			/*if(pos.x>=400 && pos.x<=2960){
			camera.SetPosition(pos.x, pos.y);}*/
			gameItem.SetPosition(pos); }
		}


		if (right == 1) {
			direction = 1;
			if (spacePushed == 0) {
				gameItem.SetCurrentFrame(1);
			}
			Vector2D pos = gameItem.GetPosition();
			if (gameItem.GetCurrentBBox().GetMaxPoint().x <= 1285) {
				pos.x += 5f;
				recentcameraX = camera.GetX();
				camera.MoveRight(5f);
				newcameraX = camera.GetX();
			/*if(pos.x >= 400 && pos.x <=2960){
			camera.SetPosition(pos.x, pos.y);}*/
			gameItem.SetPosition(pos); }
		}


		if (right == 0 && left == 0 && down == 0 && speedY == 0 && gameItem.GetY() == 235) {
			if (direction == 1) {
				gameItem.SetCurrentFrame(0);
			} else {
				gameItem.SetCurrentFrame(7);
			}
		}


	}

	@Override
	public void update(float interval) {

		//System.out.println(gameItem.mBBoxTransformed.CheckOverlapping(AllItems.get(1).GetCurrentBBox()));

		//gameItem.SetBoundingBox();
		//System.out.println("x: " + gameItem.GetX() + " y: " + gameItem.GetY() + " speed: " +speedY);
		//gameItem.DrawBoundingBox();

		SetAllBBox();
		float cameranakx = gameItem.GetPositionX();
		//camera.SetXAndGetKulonbseg(cameranakx);
		//System.out.println("karakter: " + gameItem.GetX() + " camera: " + camera.GetX());



		for (int i = 0; i < AllItems.size(); i++) {

			if ((gameItem.GetBBox().CheckOverlapping(AllItems.get(i).GetCurrentBBox()) == true) && (AllItems.get(i).GetVisible() == true)) {


				AllItems.get(i).SetVisible(false);


				if (AllItems.get(i).GetID() == 1) {
					System.out.println("gem");
				} else if (AllItems.get(i).GetID() == 2) {
					System.out.println("poti");
				}
			}
			;
		}

		if (gameItem.GetCurrentBBox().CheckOverlapping(platform.GetCurrentBBox())) {
			if (gameItem.GetBBoxMaxX() > platform.GetBBoxMinX() && gameItem.GetBBoxMaxX() < platform.GetBBoxMinX() + 20f && (gameItem.GetBBoxMinY() < platform.GetBBoxMaxY() || gameItem.GetBBoxMaxY() > platform.GetBBoxMinY())) {
				System.out.println("BALROL");
			} else if (gameItem.GetBBoxMinX() < platform.GetBBoxMaxX() && gameItem.GetBBoxMinX() > platform.GetBBoxMaxX() - 20f && (gameItem.GetBBoxMinY() < platform.GetBBoxMaxY() || gameItem.GetBBoxMaxY() > platform.GetBBoxMinY())) {
				System.out.println("JOBBROL");
			} else if (gameItem.GetBBoxMaxY() > platform.GetBBoxMinY() && gameItem.GetBBoxMaxY() < platform.GetBBoxMinY() + 20f && (gameItem.GetBBoxMaxX() > platform.GetBBoxMinX() || gameItem.GetBBoxMinX() < platform.GetBBoxMaxX())) {
				System.out.println("FENTROL");
			} else if (gameItem.GetBBoxMinY() < platform.GetBBoxMaxY() && gameItem.GetBBoxMinY() > platform.GetBBoxMaxY() - 20f && (gameItem.GetBBoxMaxX() > platform.GetBBoxMinX() || gameItem.GetBBoxMinX() < platform.GetBBoxMaxX())) {
				System.out.println("LENTROL");
			}
		}

		if (up == 1) {
			if (direction == 1) {
				gameItem.SetCurrentFrame(3);
			} else {
				gameItem.SetCurrentFrame(4);
			}
			Vector2D pos = gameItem.GetPosition();
			pos.y += speedY;
			speedY = speedY + 2.0f;
			gameItem.SetPosition(pos);
			if (gameItem.GetBBoxMaxY() >= 630) {
				speedY = 0;
				up = 0;
				pos.y = 235;
				gameItem.SetPosition(pos);
				spacePushed = 0;
			}
		}
	}

	@Override
	public void render(Window window) {
		renderer.render(window, camera, gameItem, Alltestfold);

	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		for (int i = 0; i < backgrounds.length; i++)
			backgrounds[i].cleanup();
		gameItem.cleanUp();
	}


	public void SetAllBBox() {
		gameItem.SetKulonbseg(GetCameraMove());
		//gameItem.SetBoundingBox(gameItem.GetHeight(), gameItem.GetWidth());
		gameItem.GetCurrentBBox();

		for (int i = 1; i < 9; i++){
			//Alltestfold.get(i).SetBoundingBox(Alltestfold.get(i).GetHeight(), Alltestfold.get(i).GetWidth());
			Alltestfold.get(i).GetCurrentBBox();
			Alltestfold.get(i).DrawBoundingBox();
		}

		//System.out.println("KarakterX: " + gameItem.GetX() + " KarakterY:" + gameItem.GetY());
		//System.out.println("BBox    X: " + gameItem.GetBBoxMinX() + " BBox    Y: " + gameItem.GetBBoxMinY());
		System.out.println(GetCameraMove());
	}

	public float GetCameraMove() {
		return recentcameraX - newcameraX;
	}
}