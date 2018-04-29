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
	private int isOnGround = 1;
	private int isIdle = 1;

	private final Renderer renderer;
	private int direction = 1;

	// 2D Texture items
	private Texture2D[] backgrounds;
	private CCamera2D camera;


	// 2D GameObject items
	public GameObject2D gameItem;
	private GameObject2D itemsOnGround;
	private GameObject2D platform;
	private GameObject2D testfold;
	private GameObject2D doboz;

	private ArrayList<GameObject2D> AllItems;
	private ArrayList<GameObject2D> Alltestfold;
	private ArrayList<GameObject2D> AllLebegoFold;
	private ArrayList<GameObject2D> AllDoboz;


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



		AllDoboz = new ArrayList<>();
		int k=1;
		CSprite Doboz = new CSprite("textures/items/Crate", 1, 128, 128);
		for(int i=3;i>=0;i--){
			for(int j=3; j>=k;j--){
				doboz = new GameObject2D();
				doboz.AddFrame(Doboz);
				doboz.SetPosition(1600+i*110,170+j*110);
				AllDoboz.add(doboz);
				k+=1;
			}
		}



		platform = new GameObject2D();
		AllLebegoFold = new ArrayList<>();
		CSprite lebegoBal = new CSprite("textures/platform/Plat1", 1, 128, 128);
		CSprite lebegoJob = new CSprite("textures/platform/Plat3", 1, 128, 128);
		CSprite lebegoKozep = new CSprite("textures/platform/Plat2", 1, 128, 128);
		for (int i = 1; i <= 6; i++) {
			if(i==1){
				platform.AddFrame(lebegoBal);
				platform.SetPosition(2000, 250);
				//platform.SetBoundingBox(platform.GetHeight(), platform.GetWidth());
				//	platform.SetID(12);
				AllLebegoFold.add(platform);
			}else if(i==6){
				platform = new GameObject2D();
				platform.AddFrame(lebegoJob);
				platform.SetPosition(2000+128*i, 250);
				//platform.SetBoundingBox(platform.GetHeight(), platform.GetWidth())
				//	platform.SetID(12);
				AllLebegoFold.add(platform);
			}else
				platform = new GameObject2D();
			platform.AddFrame(lebegoKozep);
			platform.SetPosition(2000+128*i, 250);
			//platform.SetBoundingBox(platform.GetHeight(), platform.GetWidth());
			//platform.SetID(12);
			AllLebegoFold.add(platform);}



		Alltestfold = new ArrayList<>();
		//asd


		CSprite test = new CSprite("textures/platform/Tile (2)", 1, 128, 128);
		CSprite testBal = new CSprite("textures/platform/Tile (1)", 1, 128, 128);
		CSprite testJob = new CSprite("textures/platform/Tile (3)", 1, 128, 128);

		for (int i = 1; i <= 40; i++) {
			if (i == 1 || i==9 || i==23){
				testfold = new GameObject2D();
				testfold.AddFrame(testBal);
				testfold.SetPosition(0 + i * 128, 595);
				testfold.SetBoundingBox(testfold.GetHeight(), testfold.GetWidth());
				//testfold.SetID(11);
				Alltestfold.add(testfold);

			} else if (i == 6 || i==15) {
				testfold = new GameObject2D();
				testfold.AddFrame(testJob);
				testfold.SetPosition(0 + i * 128, 595);
				testfold.SetBoundingBox(testfold.GetHeight(), testfold.GetWidth());
				//testfold.SetID(11);
				Alltestfold.add(testfold);

			}else if(i==7 || i==8||i==16||i==17||i==18||i==19||i==20||i==21||i==22){
				continue;
			}else
				testfold = new GameObject2D();
			testfold.AddFrame(test);
			testfold.SetPosition(0 + i * 128, 595);
			testfold.SetBoundingBox(testfold.GetHeight(), testfold.GetWidth());
			//	testfold.SetID(11);
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


		gameItem.SetPosition(400, 0);
		gameItem.SetScale(0.5f);
		gameItem.SetBoundingBox(gameItem.GetHeight(), gameItem.GetWidth());

		sceneManager = new C2DSceneManager();
		scene = new C2DScene();

		// Create a background texture
		Texture2D background = new Texture2D();
		background.CreateTexture("textures/background/sky.png");
		background.setPosition(0, -100, -1);
		float bw = background.GetWidth();
		Texture2D background1 = new Texture2D();
		background1.CreateTexture("textures/background/sky.png");
		background1.setPosition(bw, 0, -1);
		Texture2D background2 = new Texture2D();
		background2.CreateTexture("textures/background/sky.png");
		background2.setPosition(bw*2, 0, -1);

		// Create a cloud layer
		Texture2D clouds = new Texture2D();
		clouds.CreateTexture("textures/background/clouds_1.png");
		clouds.setScale(0.6f);
		clouds.setPosition(-100,-300,0);
		Texture2D clouds1 = new Texture2D();
		float cw = clouds.GetWidth();
		clouds1.CreateTexture("textures/background/clouds_1.png");
		clouds1.setPosition(cw, 0, 0);
		Texture2D clouds2 = new Texture2D();
		clouds2.CreateTexture("textures/background/clouds_1.png");
		clouds2.setPosition(cw*2, 0, 0);

		Texture2D felho = new Texture2D();
		felho.CreateTexture("textures/background/clouds_2");

		// Create a mountain layer
		Texture2D mountains = new Texture2D();
		mountains.CreateTexture("textures/background/grounds.png");
		mountains.setScale(0.7f);
        mountains.setPosition(-300, -300, 0);
        float mw = mountains.GetWidth()*0.7f;
        Texture2D mountains1 = new Texture2D();
        mountains1.CreateTexture("textures/background/grounds.png");
        mountains1.setScale(0.7f);
        mountains1.setPosition(mw-300, -300, 0);
        Texture2D mountains2 = new Texture2D();
        mountains2.CreateTexture("textures/background/grounds.png");
        mountains2.setScale(0.7f);
        mountains2.setPosition((mw*2)-300, -300, 0);



        /*
        Texture2D mountains00 = new Texture2D();
        mountains00.CreateTexture("textures/background/ground_2.png");
        mountains00.setScale(0.9f);
        mountains00.setPosition(0, -300, 0);
        Texture2D mountains10 = new Texture2D();
        mountains10.CreateTexture("textures/background/ground_3.png");
        mountains10.setScale(0.9f);
        mountains10.setPosition(0, -300, 0);
        Texture2D mountains1 = new Texture2D();
        mountains1.setScale(0.9f);
		mountains1.setPosition(mw, -300, 0);
		mountains1.CreateTexture("textures/background/ground_1.png");
        Texture2D mountains01 = new Texture2D();
        mountains01.CreateTexture("textures/background/ground_2.png");
        mountains01.setScale(0.9f);
        mountains01.setPosition(mw, -300, 0);
        Texture2D mountains11 = new Texture2D();
        mountains11.CreateTexture("textures/background/ground_3.png");
        mountains11.setScale(0.9f);
        mountains11.setPosition(mw, -300, 0);
        Texture2D mountains2 = new Texture2D();
		mountains2.CreateTexture("textures/background/ground_1.png");
		mountains2.setScale(0.9f);
		mountains2.setPosition(mw*2, -300, 0);
        Texture2D mountains02 = new Texture2D();
        mountains02.CreateTexture("textures/background/ground_2.png");
        mountains02.setScale(0.9f);
        mountains02.setPosition(mw*2, -300, 0);
        Texture2D mountains12 = new Texture2D();
        mountains12.CreateTexture("textures/background/ground_3.png");
        mountains12.setScale(0.9f);
        mountains12.setPosition(mw*2, -300, 0);*/

        // Create a tree layer
		Texture2D rocks = new Texture2D();
		rocks.CreateTexture("textures/background/rocks.png");
		rocks.setPosition(0, -300, -1);
        float gw1 = rocks.GetWidth();
		Texture2D rocks1 = new Texture2D();
		rocks1.CreateTexture("textures/background/rocks.png");
		rocks1.setPosition(gw1, -300, 0);
		Texture2D rocks2 = new Texture2D();
		rocks2.CreateTexture("textures/background/rocks.png");
		rocks2.setPosition(gw1*2, -300, 0);


		// Create graphics layer
		C2DGraphicsLayer layerBG = new C2DGraphicsLayer();
		layerBG.AddTexture(background);
        layerBG.AddTexture(background1);
        layerBG.AddTexture(background2);

		// Create graphics layer
		C2DGraphicsLayer layer1 = new C2DGraphicsLayer();
		layer1.AddTexture(clouds);
        layer1.AddTexture(clouds1);
        layer1.AddTexture(clouds2);
		// Create graphics layer
		C2DGraphicsLayer layer2 = new C2DGraphicsLayer();
		layer2.AddTexture(rocks);
        layer2.AddTexture(rocks1);
        layer2.AddTexture(rocks2);

		C2DGraphicsLayer layer3 = new C2DGraphicsLayer();
		layer3.AddTexture(mountains);
        layer3.AddTexture(mountains1);
        layer3.AddTexture(mountains2);

        C2DGraphicsLayer layer4 = new C2DGraphicsLayer();
		layer4.AddTexture(felho);
        /*layer4.AddTexture(ground);
        layer4.AddTexture(ground1);
        layer4.AddTexture(ground2);
*/
        C2DGraphicsLayer playerLayer = new C2DGraphicsLayer();
		playerLayer.AddGameObject(gameItem);
		playerLayer.AddGameObject(platform);
		playerLayer.AddGameObject(Alltestfold);
		playerLayer.AddGameObject(AllLebegoFold);
		playerLayer.AddGameObject(AllDoboz);

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
		scene.RegisterLayer(layerBG);
		scene.RegisterLayer(layer2);
        //scene.RegisterLayer(layer4);
        scene.RegisterLayer(layer3);
        scene.RegisterLayer(layer1);
        scene.RegisterLayer(playerLayer);
		scene.RegisterLayer(itemLayer);


		// Register scene at the manager
		sceneManager.RegisterScene(scene);

/*		backgrounds = new Texture2D[15];
		backgrounds[0] = background;
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
		backgrounds[14] = ground2;
*/
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
			speedY = -30f;
			up = 1;
			spacePushed = 1;
			isOnGround = 0;
		}

		/*if (down == 1 && right ==1) {

		}*/

		if (left == 1) {
			direction = -1;
			if (spacePushed == 0 && speedY <= 2f ) {
				gameItem.SetCurrentFrame(2);
			}
			Vector2D pos = gameItem.GetPosition();
			pos.x -= 5f;
			camera.MoveLeft(5f);
			gameItem.SetPosition(pos);
		}


		if (right == 1) {
			direction = 1;
			if (spacePushed == 0 && speedY <= 2f ) {
				gameItem.SetCurrentFrame(1);
			}
			Vector2D pos = gameItem.GetPosition();
			pos.x += 5f;
			camera.MoveRight(5f);
			gameItem.SetPosition(pos);
		}


		if (right == 0 && left == 0 && down == 0 && spacePushed == 0 && speedY <= 2f) {
			if (direction == 1) {
				gameItem.SetCurrentFrame(0);
			} else {
				gameItem.SetCurrentFrame(7);
			}
		}

		if (speedY > 2 || speedY < 0) {
			if (direction == 1) {
				gameItem.SetCurrentFrame(3);
			} else {
				gameItem.SetCurrentFrame(4);
			}
		}


	}

	@Override
	public void update(float interval) {

		UtkozesekVizsgalata();
		Gravity();

	}

	@Override
	public void render(Window window) {
		renderer.render(window, camera, gameItem, Alltestfold);

	}

	@Override
	public void cleanup() {
		renderer.cleanup();
/*		for (int i = 0; i < backgrounds.length; i++)
			backgrounds[i].cleanup();
*/		gameItem.cleanUp();
	}

	public void Gravity() {
		Vector2D pos = gameItem.GetPosition();
		pos.y += speedY;
		speedY = speedY + gravity;
		gameItem.SetPosition(pos);
		System.out.println(speedY);
	}

	public void UtkozesekVizsgalata() {
		for(int k=0; k<Alltestfold.size(); k++) {
			if (gameItem.GetCurrentBBox().CheckOverlapping(Alltestfold.get(k).GetCurrentBBox())) {
				if (gameItem.GetBBoxMaxY() > Alltestfold.get(k).GetBBoxMinY()-30f && gameItem.GetBBoxMaxY() < Alltestfold.get(k).GetBBoxMinY() + 35f && (gameItem.GetBBoxMaxX() > Alltestfold.get(k).GetBBoxMinX() || gameItem.GetBBoxMinX() < Alltestfold.get(k).GetBBoxMaxX())) {
					//System.out.println("FENTROL");
					if (speedY > 0f) {
						speedY = 0;
						up = 0;
						spacePushed = 0;
					}
				}
			}
		}

		for(int k=0; k<AllLebegoFold.size(); k++) {
			if (gameItem.GetCurrentBBox().CheckOverlapping(AllLebegoFold.get(k).GetCurrentBBox())) {
				if (gameItem.GetBBoxMaxY() > AllLebegoFold.get(k).GetBBoxMinY()-30f && gameItem.GetBBoxMaxY() < AllLebegoFold.get(k).GetBBoxMinY() + 35f && (gameItem.GetBBoxMaxX() > AllLebegoFold.get(k).GetBBoxMinX() || gameItem.GetBBoxMinX() < AllLebegoFold.get(k).GetBBoxMaxX())) {
					//System.out.println("FENTROL");
					if (speedY > 0f) {
						speedY = 0;
						up = 0;
						spacePushed = 0;
					}
				}
			}
		}
		for(int k=0; k<AllDoboz.size(); k++) {
			if (gameItem.GetCurrentBBox().CheckOverlapping(AllDoboz.get(k).GetCurrentBBox())) {
				if (gameItem.GetBBoxMaxY() > AllDoboz.get(k).GetBBoxMinY()-30f && gameItem.GetBBoxMaxY() < AllDoboz.get(k).GetBBoxMinY() + 35f && (gameItem.GetBBoxMaxX() > AllDoboz.get(k).GetBBoxMinX() || gameItem.GetBBoxMinX() < AllDoboz.get(k).GetBBoxMaxX())) {
					//System.out.println("FENTROL");
					if (speedY > 0f) {
						speedY = 0;
						up = 0;
						spacePushed = 0;
					}
				}
			}
		}

		for (int i = 0; i < AllItems.size(); i++) {

			if ((gameItem.GetBBox().CheckOverlapping(AllItems.get(i).GetCurrentBBox()) == true) && (AllItems.get(i).GetVisible() == true)) {


				AllItems.get(i).SetVisible(false);


				if (AllItems.get(i).GetID() == 1) {
					System.out.println("gem");
				} else if (AllItems.get(i).GetID() == 2) {
					System.out.println("poti");
				}
			}
		}


		//Példa vizsgálat, egy kimodnott itemre
		/*if (gameItem.GetCurrentBBox().CheckOverlapping(platform.GetCurrentBBox())) {
			if (gameItem.GetBBoxMaxX() > platform.GetBBoxMinX() && gameItem.GetBBoxMaxX() < platform.GetBBoxMinX() + 20f && (gameItem.GetBBoxMinY() < platform.GetBBoxMaxY() || gameItem.GetBBoxMaxY() > platform.GetBBoxMinY())) {
				System.out.println("BALROL");
			} else if (gameItem.GetBBoxMinX() < platform.GetBBoxMaxX() && gameItem.GetBBoxMinX() > platform.GetBBoxMaxX() - 20f && (gameItem.GetBBoxMinY() < platform.GetBBoxMaxY() || gameItem.GetBBoxMaxY() > platform.GetBBoxMinY())) {
				System.out.println("JOBBROL");
			} else if (gameItem.GetBBoxMaxY() > platform.GetBBoxMinY() && gameItem.GetBBoxMaxY() < platform.GetBBoxMinY() + 20f && (gameItem.GetBBoxMaxX() > platform.GetBBoxMinX() || gameItem.GetBBoxMinX() < platform.GetBBoxMaxX())) {
				System.out.println("FENTROL");
			} else if (gameItem.GetBBoxMinY() < platform.GetBBoxMaxY() && gameItem.GetBBoxMinY() > platform.GetBBoxMaxY() - 20f && (gameItem.GetBBoxMaxX() > platform.GetBBoxMinX() || gameItem.GetBBoxMinX() < platform.GetBBoxMaxX())) {
				System.out.println("LENTROL");
			}
		}*/

	}


}