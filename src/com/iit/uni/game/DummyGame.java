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
	private float gravity = 2f;
	private int spacePushed = 0;
	private int isAttacking = 0;
	private int CharacterIsAlive = 1;

	private BoundingBox2D zombAttackBBox;

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

	private GameObject2D testfold2;
	
	private GameObject2D menuButton;

	private GameObject2D doboz;
	private GameObject2D zomB;


	private ArrayList<GameObject2D> AllItems;
	private ArrayList<GameObject2D> Alltestfold;
	private ArrayList<GameObject2D> AllLebegoFold;
	private ArrayList<GameObject2D> AllDoboz;
	private ArrayList<BoundingBox2D> ZombAttackBBox;
	private ArrayList<GameObject2D> Zombik;
	private ArrayList<Integer> zomBIsAlive;
	private ArrayList<Integer> zomBDirection;

	private double mousePosX;
	private double mousePosY;
	
	private boolean inside = false;
	
	public static enum GSTATE{
		MENU,
		GAME,
	};
	
	public static GSTATE state = GSTATE.MENU;


	
	private int ID = 0;

	// Global Scene manager
	public static C2DSceneManager sceneManager;
	public static C2DSceneManager sceneManagerMenu;

	private C2DScene scene;
	private C2DScene sceneMenu;

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
		CSprite attackRight = new CSprite("textures/ninja/Attack", 10, 200, 200);
		CSprite attackLeft = new CSprite("textures/ninja/Attack_left", 10, 200, 200);
		CSprite jumpattackRight = new CSprite("textures/ninja/Jump_Attack", 10, 200, 200);
		CSprite jumpattackLeft = new CSprite("textures/ninja/Jump_Attack_left", 10, 200, 200);
		CSprite Die = new CSprite("textures/ninja/Dead", 10, 200, 200);
		CSprite DieLeft = new CSprite("textures/ninja/Dead_left", 10, 200, 200);
		CSprite ISDIE = new CSprite("textures/ninja/Dead10", 1, 200, 200);
		CSprite ISDIELEFT = new CSprite("textures/ninja/Dead_left10", 1, 200, 200);


		gameItem.AddFrame(idle);
		gameItem.AddFrame(frameRunRight);
		gameItem.AddFrame(frameRunLeft);
		gameItem.AddFrame(jumpRight);
		gameItem.AddFrame(jumpLeft);
		gameItem.AddFrame(slide);
		gameItem.AddFrame(slideLeft);
		gameItem.AddFrame(idleLeft);
		gameItem.AddFrame(attackRight);
		gameItem.AddFrame(attackLeft);
		gameItem.AddFrame(jumpattackRight);
		gameItem.AddFrame(jumpattackLeft);
		gameItem.AddFrame(Die);
		gameItem.AddFrame(DieLeft);
		gameItem.AddFrame(ISDIE);
		gameItem.AddFrame(ISDIELEFT);

		gameItem.SetAnimationSpeed(8, 12);
		gameItem.SetAnimationSpeed(8, 13);


		gameItem.SetPosition(400, 0);
		gameItem.SetScale(0.5f);
		gameItem.SetBoundingBox(gameItem.GetHeight(), gameItem.GetWidth());





		CSprite zomBMove = new CSprite("textures/zomb/Walk", 10, 200, 200);
		CSprite zomBMoveLeft = new CSprite("textures/zomb/Walk_left", 10, 200, 200);
		CSprite ZomBDead = new CSprite("textures/zomb/Dead", 12, 200, 200);
		CSprite ZombDeadLeft = new CSprite("textures/zomb/Dead_left", 12, 200, 200);
		CSprite ZomBIsDead = new CSprite("textures/zomb/Dead12", 1, 200, 200);
		CSprite ZomBIsDeadleft = new CSprite("textures/zomb/Dead_left12", 1, 200, 200);
		CSprite zombAttack = new CSprite("textures/zomb/Attack", 8, 200, 200);
		CSprite zombAttackLeft = new CSprite("textures/zomb/Attack_left", 8, 200, 200);

		Zombik = new ArrayList<>();
		ZombAttackBBox = new ArrayList<>();
		zomBIsAlive = new ArrayList<>();
		zomBDirection = new ArrayList<>();

		for(int i=0; i<3; i++){
			zomB = new GameObject2D();
			zombAttackBBox = new BoundingBox2D();
			int alive = 1;
			int direction = 1;

			zomB.AddFrame(zomBMove);
			zomB.AddFrame(zomBMoveLeft);
			zomB.AddFrame(ZomBDead);
			zomB.AddFrame(ZombDeadLeft);
			zomB.AddFrame(ZomBIsDead);
			zomB.AddFrame(ZomBIsDeadleft);
			zomB.AddFrame(zombAttack);
			zomB.AddFrame(zombAttackLeft);


			zomB.SetPosition(1400 + i * 150, 220);
			zomB.SetScale(0.5f);
			zomB.SetAnimationSpeed(6, 0);
			zomB.SetAnimationSpeed(6, 1);
			zomB.SetAnimationSpeed(7, 2);
			zomB.SetAnimationSpeed(7, 3);
			zomB.SetAnimationSpeed(7, 4);
			zomB.SetAnimationSpeed(7, 5);
			zomB.SetBoundingBox(zomB.GetHeight(), zomB.GetWidth());

			Zombik.add(zomB);
			ZombAttackBBox.add(zombAttackBBox);
			zomBIsAlive.add(alive);
			zomBDirection.add(direction);
		}






		sceneManager = new C2DSceneManager();
		scene = new C2DScene();
		
		sceneManagerMenu = new C2DSceneManager();
		sceneMenu = new C2DScene();

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
		playerLayer.AddGameObject(Zombik);

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

		//Men�-----------------------------------------------------------------------------------------------------
		C2DGraphicsLayer menuLayer = new C2DGraphicsLayer();
		
		
		//ArrayList<GameObject2D> MenuItems = new ArrayList<>();
		CSprite play = new CSprite("textures/items/Start", 1, 200, 200);
		
		menuButton = new GameObject2D();
		menuButton.AddFrame(play);
		menuButton.SetPosition(window.getWidth()/2-(menuButton.GetWidth()/2),window.getHeight()/2-(menuButton.GetHeight()/2));
		menuButton.SetBoundingBox(menuButton.GetHeight(),menuButton.GetWidth());
		
		
		menuLayer.AddGameObject(menuButton);
			
		//Men�-END-------------------------------------------------------------------------------------------------END
		
		// register layer at the scene
		scene.RegisterLayer(layerBG);
		scene.RegisterLayer(layer2);
        //scene.RegisterLayer(layer4);
        scene.RegisterLayer(layer3);
        scene.RegisterLayer(layer1);
        scene.RegisterLayer(playerLayer);
		scene.RegisterLayer(itemLayer);
		
		
		sceneMenu.RegisterLayer(menuLayer);


		// Register scene at the manager
		sceneManager.RegisterScene(scene);
		sceneManagerMenu.RegisterScene(sceneMenu);


		camera = new CCamera2D();


	}

	@Override
	public void input(Window window) {

		if (window.isKeyPressed(GLFW_KEY_ENTER) && state == GSTATE.MENU){
			state = GSTATE.GAME;
		}

		
		if (window.isMouseButtonPressed(GLFW_MOUSE_BUTTON_1) && inside == true && state == GSTATE.MENU){
			state = GSTATE.GAME;
		}
		
		mousePosX = window.getMouseX();
		mousePosY = window.getMouseY();
		
		//System.out.println("x:"+window.getMouseX()+ " y:"+window.getMouseY());
			
		if(state == GSTATE.GAME){
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

			if (window.isKeyPressed(GLFW_KEY_UP) && spacePushed == 0 && CharacterIsAlive == 1) {
				speedY = -30f;
				up = 1;
				spacePushed = 1;
			}

			if (window.isKeyPressed(GLFW_KEY_SPACE) && CharacterIsAlive == 1) {
				isAttacking = 1;
				if(direction == 1) {
					if(speedY <= 2f && spacePushed == 0) {
						gameItem.SetCurrentFrame(8);
					} else gameItem.SetCurrentFrame(10);
				}
				else {
					if(speedY <= 2f && spacePushed == 0) {
						gameItem.SetCurrentFrame(9);
					} else gameItem.SetCurrentFrame(11);
				}
			}

			if(window.isKeyReleased(GLFW_KEY_SPACE) && CharacterIsAlive == 1) {
				isAttacking = 0;
			}

		/*if (down == 1 && right ==1) {
		}*/

			if (left == 1 && CharacterIsAlive == 1) {
				direction = -1;
				if (spacePushed == 0 && speedY <= 2f  && isAttacking == 0) {
					gameItem.SetCurrentFrame(2);
				}
				Vector2D pos = gameItem.GetPosition();
				pos.x -= 5f;
				camera.MoveLeft(5f);
				gameItem.SetPosition(pos);
			}


			if (right == 1 && CharacterIsAlive == 1) {
				direction = 1;
				if (spacePushed == 0 && speedY <= 2f && isAttacking == 0) {
					gameItem.SetCurrentFrame(1);
				}
				Vector2D pos = gameItem.GetPosition();
				pos.x += 5f;
				camera.MoveRight(5f);
				gameItem.SetPosition(pos);
			}


			if (right == 0 && left == 0 && down == 0 && spacePushed == 0 && speedY <= 2f && isAttacking == 0 && CharacterIsAlive == 1) {
				if (direction == 1) {
					gameItem.SetCurrentFrame(0);
				} else {
					gameItem.SetCurrentFrame(7);
				}
			}

			if ((speedY > 2 || speedY < 0) && isAttacking == 0 && CharacterIsAlive == 1) {
				if (direction == 1) {
					gameItem.SetCurrentFrame(3);
				} else {
					gameItem.SetCurrentFrame(4);
				}
			}
		}




	}

	private boolean visible = true;

	
	@Override
	public void update(float interval){


				if (state == GSTATE.MENU && visible == true) {
					gameItem.SetVisible(false);
					for (int i = 0; i < AllItems.size(); i++) {
						AllItems.get(i).SetVisible(false);
					}
					for (int i = 0; i < Alltestfold.size(); i++) {
						Alltestfold.get(i).SetVisible(false);
					}
					for (int i = 0; i < AllLebegoFold.size(); i++) {
						AllLebegoFold.get(i).SetVisible(false);
					}

					menuButton.SetVisible(true);
					visible = false;
				}
				if (state == GSTATE.GAME && visible == false) {
					gameItem.SetVisible(true);
					for (int i = 0; i < AllItems.size(); i++) {
						AllItems.get(i).SetVisible(true);
					}
					for (int i = 0; i < Alltestfold.size(); i++) {
						Alltestfold.get(i).SetVisible(true);
					}
					for (int i = 0; i < AllLebegoFold.size(); i++) {
						AllLebegoFold.get(i).SetVisible(true);
					}

					menuButton.SetVisible(false);
					visible = true;
				}

				//SetAllBBox();

				if (menuButton.GetCurrentBBox().CheckOverlapping(new BoundingBox2D(new Vector2D((float) mousePosX, (float) mousePosY), new Vector2D((float) mousePosX + 1, (float) mousePosY + 1)))) {
					inside = true;
				} else {
					inside = false;
				}


				//System.out.println(gameItem.mBBoxTransformed.CheckOverlapping(AllItems.get(1).GetCurrentBBox()));

				//gameItem.SetBoundingBox();
				//System.out.println("x: " + gameItem.GetX() + " y: " + gameItem.GetY() + " speed: " +speedY);
				//gameItem.DrawBoundingBox();
				if (state == GSTATE.GAME) {

					float cameranakx = gameItem.GetPositionX();
					//camera.SetXAndGetKulonbseg(cameranakx);
					//System.out.println("karakter: " + gameItem.GetX() + " camera: " + camera.GetX());

					for(int i=0; i<Zombik.size(); i++){
						ZombAttackBBox.get(i).Setpoints(Zombik.get(i).GetCurrentBBox().GetMinPoint().x - 40f, Zombik.get(i).GetCurrentBBox().GetMinPoint().y - 40f, Zombik.get(i).GetCurrentBBox().GetMaxPoint().x + 40f, Zombik.get(i).GetCurrentBBox().GetMaxPoint().y + 40f);
					}


					UtkozesekVizsgalata();
					Gravity();
					Fall();
					for(int i=0; i< Zombik.size();i++){
						ZomBMove(i);
					}



					if (CharacterIsAlive == 0) {
						if (gameItem.GetCurrentFrameCurrentSprite() > 8) {
							if (direction == 1) {
								gameItem.SetCurrentFrame(14);
								Reset();
							} else {
								gameItem.SetCurrentFrame(15);
								Reset();
							}
						}
					}

				}
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
		if ( speedY < 30) {
			speedY = speedY + gravity;
		}
		gameItem.SetPosition(pos);
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

		for(int i=0; i<Zombik.size(); i++){
			if((Zombik.get(i).GetPosition().x - gameItem.GetPosition().x) < 300f ){
				if(zomBIsAlive.get(i) == 1) {
					if(gameItem.GetPosition().x < Zombik.get(i).GetPosition().x) {
						zomBDirection.set(i, 0);
					} else {
						zomBDirection.set(i, 1);
					}
					if (zomBDirection.get(i) == 1) {
						Zombik.get(i).SetCurrentFrame(6);
						if(Zombik.get(i).GetPosition().x > Zombik.get(i).GetPosition().x-200f && Zombik.get(i).GetPosition().x < Zombik.get(i).GetPosition().x+200f) {
							Vector2D pos = Zombik.get(i).GetPosition();
							pos.x += 1.5f;
							Zombik.get(i).SetPosition(pos);
						}
					}
					if (zomBDirection.get(i) == 0) {
						Zombik.get(i).SetCurrentFrame(7);
						if(Zombik.get(i).GetPosition().x > 1000 && Zombik.get(i).GetPosition().x < 1500) {
							Vector2D pos = Zombik.get(i).GetPosition();
							pos.x -= 1.5f;
							Zombik.get(i).SetPosition(pos);
						}
					}
					if (gameItem.GetCurrentBBox().CheckOverlapping(Zombik.get(i).GetCurrentBBox())) {
						NinjaDie(i);
					}
				}
			}
			//}


			if (gameItem.GetCurrentBBox().CheckOverlapping(ZombAttackBBox.get(i)) == true)  {
				if (isAttacking == 1) {
					if (zomBDirection.get(i) == 1) {
						ZomBDie(i);
					}
					if (zomBDirection.get(i) == 0) {
						ZomBDie(i);
					}
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

	@Override
	public void render(Window window){
		renderer.render(window, camera, gameItem, Alltestfold);
	}

	public void Fall() {
		if( gameItem.GetBBoxMinY() > 1300){
			gameItem.SetPosition(400, 200);
			camera.SetPosition(0, 0);
			CharacterIsAlive = 1;
			zomBIsAlive.set(0, 1);
			zomBIsAlive.set(1, 1);
			zomBIsAlive.set(2, 1);
			zomB.ResetAmitAkarsz(2);
			zomB.ResetAmitAkarsz(3);
			gameItem.ResetAmitAkarsz(12);
			gameItem.ResetAmitAkarsz(13);
			state = GSTATE.MENU;
			speedY = 2;
			for(int i = 0; i < AllItems.size(); i++){
				AllItems.get(i).SetVisible(true);
			}
		}
			}



	public void Reset() {
		gameItem.SetPosition(400, 200);
		camera.SetPosition(0, 0);
		CharacterIsAlive = 1;
		zomBIsAlive.set(0, 1);
		zomBIsAlive.set(1, 1);
		zomBIsAlive.set(2, 1);
		zomB.ResetAmitAkarsz(2);
		zomB.ResetAmitAkarsz(3);
		state = GSTATE.MENU;
		speedY = 2;
		gameItem.ResetAmitAkarsz(12);
		gameItem.ResetAmitAkarsz(13);
		for(int i = 0; i < AllItems.size(); i++){
			AllItems.get(i).SetVisible(true);
		}
	}

	public void ZomBMove (int i) {
			if (zomBDirection.get(i) == 1 && zomBIsAlive.get(i) == 1 && (Zombik.get(i).GetPosition().x - gameItem.GetPosition().x) > 300f ) {
				Zombik.get(i).SetCurrentFrame(0);
				Vector2D pos = Zombik.get(i).GetPosition();
				pos.x += 0.5f;
				Zombik.get(i).SetPosition(pos);
			}
			if (zomBDirection.get(i) == 0 && zomBIsAlive.get(i) == 1 && (Zombik.get(i).GetPosition().x - gameItem.GetPosition().x) > 300f ) {
				Zombik.get(i).SetCurrentFrame(1);
				Vector2D pos = Zombik.get(i).GetPosition();
				pos.x -= 0.5f;
				Zombik.get(i).SetPosition(pos);
			}

			if (Zombik.get(i).GetPosition().x > 1500 && zomBIsAlive.get(i) == 1) {
				zomBDirection.set(i, 0);
			}
			if (Zombik.get(i).GetPosition().x < 1000 && zomBIsAlive.get(i) == 1) {
				zomBDirection.set(i, 1);
			}

			if (zomBIsAlive.get(i) == 0) {
				if (zomBDirection.get(i) == 1) {
					if (Zombik.get(i).GetCurrentFrameCurrentSprite() > 10) {
						Zombik.get(i).SetCurrentFrame(4);
						Zombik.get(i).ResetAmitAkarsz(2);
						Zombik.get(i).ResetAmitAkarsz(3);
					}
				}
				if (zomBDirection.get(i) == 0) {
					if (Zombik.get(i).GetCurrentFrameCurrentSprite() > 10) {
						Zombik.get(i).SetCurrentFrame(5);
						Zombik.get(i).ResetAmitAkarsz(2);
						Zombik.get(i).ResetAmitAkarsz(3);
					}
				}
			}
	}

	public void ZomBDie(int i) {
		if(zomBDirection.get(i) == 1 && zomBIsAlive.get(i) == 1){
			Zombik.get(i).SetCurrentFrame(2);
			if(Zombik.get(i).GetCurrentFrameCurrentSprite() > 10){
				Zombik.get(i).SetCurrentFrame(4);
			}
			zomBIsAlive.set(i, 0);
		}
		if(zomBDirection.get(i) == 0 && zomBIsAlive.get(i) == 1){
			Zombik.get(i).SetCurrentFrame(3);
			if(Zombik.get(i).GetCurrentFrameCurrentSprite() > 10){
				Zombik.get(i).SetCurrentFrame(5);
			}
			zomBIsAlive.set(i, 0);
		}


	}

	public void NinjaDie(int i) {
		if (zomBIsAlive.get(i) == 1) {
			if (direction == 1) {
				gameItem.SetCurrentFrame(12);
			} else {
				gameItem.SetCurrentFrame(13);

			}
			CharacterIsAlive = 0;
		}
	}

	//lets try it

}