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
import javafx.scene.control.Tab;
import sun.plugin.javascript.navig4.Layer;

import static org.lwjgl.glfw.GLFW.*;


public class DummyGame implements IGameLogic {
	private float karakterStartPoz;
	private int down = 0;
	private int up = 0;
	private int right = 0;
	private int left = 0;

	private float speedY = 0f;
	private float gravity = 2f;
	private int spacePushed = 0;
	private int isAttacking = 0;
	private int CharacterIsAlive = 1;
	private int megszerzettPont = 0;

	private BoundingBox2D zombAttackBBox;

	private final Renderer renderer;
	private int direction = 1;

	// 2D Texture items
	//private Texture2D[] backgrounds;
	private CCamera2D camera;


	// 2D GameObject items
	public GameObject2D gameItem;
	private GameObject2D itemsOnGround;

	private GameObject2D platform;
	private GameObject2D testfold;

	private GameObject2D testfold2;
	
	private GameObject2D menuButton;
	private GameObject2D table;
	private GameObject2D tableDie;
	private GameObject2D tableSign;
	private GameObject2D bokor;
	private GameObject2D doboz;
	private GameObject2D skull;
	private GameObject2D bones;
	private GameObject2D zomB;

    private Texture2D clouds;
    private Texture2D clouds1;
    private Texture2D clouds2;

	private ArrayList<GameObject2D> AllItems;
	private ArrayList<GameObject2D> Alltestfold;
	private ArrayList<GameObject2D> AllLebegoFold;
	private ArrayList<GameObject2D> AllDoboz;

	private ArrayList<BoundingBox2D> ZombAttackBBox;
	private ArrayList<GameObject2D> Zombik;
	private ArrayList<Integer> zomBIsAlive;
	private ArrayList<Integer> zomBDirection;

	private ArrayList<GameObject2D> decor;


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
		CSprite Doboz = new CSprite("textures/decor/Crate", 1, 128, 128);
		for(int i=3;i>=0;i--){
			for(int j=3; j>=k;j--){
				doboz = new GameObject2D();
				doboz.AddFrame(Doboz);
				doboz.SetPosition(1600+i*110,170+j*110);
				AllDoboz.add(doboz);
				k+=1;
			}
		}
		decor = new ArrayList<>();
		CSprite TablaStart = new CSprite("textures/decor/ArrowSign",1,128,128);
		CSprite TablaDie= new CSprite("textures/decor/ArrowSign1",1,128,128);
		CSprite Dead1= new CSprite("textures/decor/TombStone (1)",1,128,128);
		CSprite Dead2= new CSprite("textures/decor/TombStone (2)",1,128,128);

		for(int i=0; i<=3; i++){
			table = new GameObject2D();

			if(i==0){table.AddFrame(TablaStart);table.SetPosition(300,510);}
			if(i==1){table.AddFrame(TablaStart);table.SetPosition(2850,170);}
			if(i==2){table.AddFrame(Dead1);table.SetPosition(2440,200);}
			if(i==3){table.AddFrame(Dead2);table.SetPosition(2610,180);}
			decor.add(table);}
		tableDie = new GameObject2D();
		tableDie.AddFrame(TablaDie);
		tableDie.SetPosition(80,510);
		decor.add(tableDie);
		CSprite Bokor = new CSprite("textures/decor/Bush (2)",1,128,128);
		CSprite DeadBokor = new CSprite("textures/decor/DeadBush",1,128,128);
		CSprite DeadTree = new CSprite("textures/decor/Tree",1,128,128);
		for(int i=0; i<=9; i++){
			bokor = new GameObject2D();
			if(i==0){bokor.AddFrame(Bokor);bokor.SetPosition(180,530);}
			if(i==1){bokor.AddFrame(Bokor);bokor.SetPosition(2250,190);}
			if(i==2){bokor.AddFrame(Bokor);bokor.SetPosition(2700,190);}
			if(i==3){bokor.AddFrame(DeadBokor); bokor.SetPosition(1200,530);}
			if(i==4){bokor.AddFrame(DeadTree); bokor.SetPosition(2400,20);}
			if(i==5){bokor.AddFrame(Bokor);bokor.SetPosition(3100,530);}
			if(i==6){bokor.AddFrame(Bokor);bokor.SetPosition(3700,530);}
			if(i==7){bokor.AddFrame(Bokor);bokor.SetPosition(4300,530);}
			if(i==8){bokor.AddFrame(DeadTree); bokor.SetPosition(3300,360);}
			if(i==9){bokor.AddFrame(DeadTree); bokor.SetPosition(3900,360);}
			decor.add(bokor);
		}
		CSprite TableSign = new CSprite("textures/decor/Sign",1,128,128);
		tableSign = new GameObject2D();
		tableSign.AddFrame(TableSign);
		tableSign.SetPosition(830,500);
		decor.add(tableSign);
		CSprite Skull = new CSprite("textures/decor/Skull",1,128,128);
		CSprite Bones = new CSprite("textures/decor/bones",1,128,128);
		for(int i=0;i<=2;i++){
			skull = new GameObject2D();
			skull.AddFrame(Skull);
			if (i==0)skull.SetPosition(3000,510);
			if (i==1)skull.SetPosition(1710,510);
			if (i==2)skull.SetPosition(2780,180);
			decor.add(skull);
		}
		bones = new GameObject2D();
		bones.AddFrame(Bones);
		bones.SetPosition(1600,550);
		decor.add(bones);




		platform = new GameObject2D();
		AllLebegoFold = new ArrayList<>();
		CSprite lebegoBal = new CSprite("textures/platform/Plat1", 1, 128, 128);
		CSprite lebegoJob = new CSprite("textures/platform/Plat3", 1, 128, 128);
		CSprite lebegoKozep = new CSprite("textures/platform/Plat2", 1, 128, 128);
		for (int i = 1; i <= 6; i++) {
			if(i==1){
				platform.AddFrame(lebegoBal);
				platform.SetPosition(2000, 250);
				AllLebegoFold.add(platform);
			}else if(i==6){
				platform = new GameObject2D();
				platform.AddFrame(lebegoJob);
				platform.SetPosition(2000+128*i, 250);
					AllLebegoFold.add(platform);
			}else
				platform = new GameObject2D();
			platform.AddFrame(lebegoKozep);
			platform.SetPosition(2000+128*i, 250);
			AllLebegoFold.add(platform);}



		Alltestfold = new ArrayList<>();
		//asd


		CSprite test = new CSprite("textures/platform/Tile (2)", 1, 128, 128);
		CSprite testBal = new CSprite("textures/platform/Tile (1)", 1, 128, 128);
		CSprite testJob = new CSprite("textures/platform/Tile (3)", 1, 128, 128);

		for (int i = 1; i <= 35; i++) {
			if (i == 1 || i==9 || i==23){
				testfold = new GameObject2D();
				testfold.AddFrame(testBal);
				testfold.SetPosition(0 + i * 128, 595);
				testfold.SetBoundingBox(testfold.GetHeight(), testfold.GetWidth());
				//testfold.SetID(11);
				Alltestfold.add(testfold);

			} else if (i == 6 || i==15 || i==35) {
				testfold = new GameObject2D();
				testfold.AddFrame(testJob);
				testfold.SetPosition(0 + i * 128, 595);
				testfold.SetBoundingBox(testfold.GetHeight(), testfold.GetWidth());
				//testfold.SetID(11);
				Alltestfold.add(testfold);

			}else if(i==7 || i==8||i==16||i==17||i==18||i==19||i==20||i==21||i==22){
				continue;
			}else testfold = new GameObject2D();
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


		gameItem.SetPosition(400, -300);
		karakterStartPoz=gameItem.GetPositionY();
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


			//zomB.SetPosition(1400 + i * 150, 220);
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


		Zombik.get(0).SetPosition(1400, 220);
		Zombik.get(1).SetPosition(2350, -120);
		Zombik.get(2).SetPosition(3250, 220);

		stPosSet();
		moverRange();


		sceneManager = new C2DSceneManager();
		scene = new C2DScene();
		
		sceneManagerMenu = new C2DSceneManager();
		sceneMenu = new C2DScene();

		// Create a background texture
		Texture2D background = new Texture2D();
		background.CreateTexture("textures/background/sky.png");
		background.setPosition(0, -100, -1);
		float bw = background.GetWidth();
		Texture2D background0 = new Texture2D();
		background0.CreateTexture("textures/background/sky.png");
		background0.setPosition(0-bw, -100, -1);
		Texture2D background1 = new Texture2D();
		background1.CreateTexture("textures/background/sky.png");
		background1.setPosition(bw, -100, -1);
		Texture2D background2 = new Texture2D();
		background2.CreateTexture("textures/background/sky.png");
		background2.setPosition(bw*2, -100, -1);

		// Create a cloud layer
		clouds = new Texture2D();
		clouds.CreateTexture("textures/background/clouds_1.png");
		clouds.setScale(0.6f);
		clouds.setPosition(-100,-300,0);
        clouds1 = new Texture2D();
		float cw = clouds.GetWidth();
		clouds1.CreateTexture("textures/background/clouds_1.png");
		clouds1.setPosition(cw, -300, 0);
		clouds2 = new Texture2D();
		clouds2.CreateTexture("textures/background/clouds_1.png");
		clouds2.setPosition(cw*2, -300, 0);


		// Create a mountain layer
		Texture2D grounds0 = new Texture2D();
		grounds0.CreateTexture("textures/background/grounds2.png");
		grounds0.setScale(0.7f);
		grounds0.setPosition(-300, -300, 0);
		float mw = grounds0.GetWidth()*0.7f;
		Texture2D grounds1 = new Texture2D();
		grounds1.CreateTexture("textures/background/grounds2.png");
		grounds1.setScale(0.7f);
		grounds1.setPosition(0-mw-300, -300, 0);
        Texture2D grounds2 = new Texture2D();
		grounds2.CreateTexture("textures/background/grounds2.png");
		grounds2.setScale(0.7f);
		grounds2.setPosition(mw-300, -300, 0);
        Texture2D grounds3 = new Texture2D();
		grounds3.CreateTexture("textures/background/grounds2.png");
		grounds3.setScale(0.7f);
		grounds3.setPosition((mw*2)-300, -300, 0);
		Texture2D grounds4 = new Texture2D();
		grounds4.CreateTexture("textures/background/grounds2.png");
		grounds4.setScale(0.7f);
		grounds4.setPosition((mw*3)-300, -300, 0);
		Texture2D grounds5 = new Texture2D();
		grounds5.CreateTexture("textures/background/grounds2.png");
		grounds5.setScale(0.7f);
		grounds5.setPosition((mw*4)-300, -300, 0);



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
		layerBG.AddTexture(background0);
        layerBG.AddTexture(background1);
        layerBG.AddTexture(background2);

		// Create graphics layer
		C2DGraphicsLayer cloudsLayer = new C2DGraphicsLayer();
		cloudsLayer.AddTexture(clouds);
		cloudsLayer.AddTexture(clouds1);
		cloudsLayer.AddTexture(clouds2);
		// Create graphics layer
		C2DGraphicsLayer rocksLayer = new C2DGraphicsLayer();
		rocksLayer.AddTexture(rocks);
		rocksLayer.AddTexture(rocks1);
		rocksLayer.AddTexture(rocks2);

		C2DGraphicsLayer groundLayer = new C2DGraphicsLayer();
		groundLayer.AddTexture(grounds0);
		groundLayer.AddTexture(grounds1);
		groundLayer.AddTexture(grounds2);
		groundLayer.AddTexture(grounds3);
		groundLayer.AddTexture(grounds4);
		groundLayer.AddTexture(grounds5);



        C2DGraphicsLayer playerLayer = new C2DGraphicsLayer();
		playerLayer.AddGameObject(gameItem);
		playerLayer.AddGameObject(platform);
		playerLayer.AddGameObject(Alltestfold);
		playerLayer.AddGameObject(AllLebegoFold);
		playerLayer.AddGameObject(AllDoboz);
		playerLayer.AddGameObject(decor);
		playerLayer.AddGameObject(Zombik);
		playerLayer.AddGameObject(zomB);


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
		scene.RegisterLayer(rocksLayer);
        scene.RegisterLayer(groundLayer);
        scene.RegisterLayer(cloudsLayer);
        scene.RegisterLayer(playerLayer);
		scene.RegisterLayer(itemLayer);

		C2DGraphicsLayer hatter = new C2DGraphicsLayer();
		Texture2D menuHatter = new Texture2D();
		menuHatter.CreateTexture("textures/background/menu.png");
		menuHatter.setPosition(0, 0, 0);
		hatter.AddTexture(menuHatter);



		sceneMenu.RegisterLayer(hatter);
		sceneMenu.RegisterLayer(menuLayer);
		// Register scene at the manager
		sceneManager.RegisterScene(scene);
		sceneManagerMenu.RegisterScene(sceneMenu);


		camera = new CCamera2D();
		//gameItem.SetVisible(false);
		//gameItem.isCollidable();

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
				if(speedY<+3){speedY = -30f;
				up = 1;
				spacePushed = 1;

				}
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


				if (menuButton.GetCurrentBBox().CheckOverlapping(new BoundingBox2D(new Vector2D((float) mousePosX, (float) mousePosY), new Vector2D((float) mousePosX + 1, (float) mousePosY + 1)))) {
					inside = true;
				} else {
					inside = false;
				}

// karakter poz kiíratás
				//System.out.println("x: " + gameItem.GetX() + " y: " + gameItem.GetY() + " speed: " +speedY);

				if (state == GSTATE.GAME) {
                    if(clouds.getPositionx()<=-2000 ||clouds1.getPositionx()<=-2000||clouds2.getPositionx()<=-2000  ){
                        clouds.setPosition(4500,clouds.getPositiony(),clouds.getPositionz());
                        clouds1.setPosition(4500,clouds1.getPositiony(),clouds1.getPositionz());
                        clouds2.setPosition(4500,clouds2.getPositiony(),clouds2.getPositionz());
                    } else
                        clouds1.setPosition(clouds1.getPositionx() - 5.0f, clouds1.getPositiony(), clouds1.getPositionz());
                        clouds2.setPosition(clouds2.getPositionx() - 3.0f, clouds2.getPositiony(), clouds2.getPositionz());
                        clouds.setPosition(clouds.getPositionx() - 1.0f, clouds.getPositiony(), clouds.getPositionz());
                    }
                        // clouds.MoveLeft(-10f);


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



				//System.out.println("megszerzett: " + megszerzettPont);

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
		//System.out.println(gameItem.GetPositionY());
		if(gameItem.GetPositionY()<=50) {
            if (camera.GetY() < 60) camera.MoveUp(12f);
            //System.out.println(camera.GetY());
            //	System.out.println("Fent van");
        }
		if(gameItem.GetPositionY()>=110){
			if(camera.GetY()>0)
				camera.MoveUp(-12f);}
			//System.out.println("Lent van");
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
					megszerzettPont += 5;
				} else if (AllItems.get(i).GetID() == 2) {
					System.out.println("poti");
					megszerzettPont += 2;
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
						if(Zombik.get(i).GetPosition().x > stPos.get(i)-moveRange.get(i) && Zombik.get(i).GetPosition().x < stPos.get(i)+moveRange.get(i)) {
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
		renderer.render(window, camera, gameItem, AllItems);
	}

	public void Fall() {
		if( gameItem.GetBBoxMinY() > 1300){
			gameItem.SetPosition(400, -300);
			camera.SetPosition(0, 0);
			CharacterIsAlive = 1;
			zomBIsAlive.set(0, 1);
			zomBIsAlive.set(1, 1);
			zomBIsAlive.set(2, 1);
			zomB.ResetAmitAkarsz(2);
			zomB.ResetAmitAkarsz(3);
			gameItem.ResetAmitAkarsz(12);
			gameItem.ResetAmitAkarsz(13);
			megszerzettPont = 0;
			state = GSTATE.MENU;
			speedY = 2;
			for(int i = 0; i < AllItems.size(); i++){
				AllItems.get(i).SetVisible(true);
			}
		}
			}



	public void Reset() {
		gameItem.SetPosition(400, -300);
		camera.SetPosition(0, 0);
		CharacterIsAlive = 1;
		zomBIsAlive.set(0, 1);
		zomBIsAlive.set(1, 1);
		zomBIsAlive.set(2, 1);
		zomB.ResetAmitAkarsz(2);
		zomB.ResetAmitAkarsz(3);
		megszerzettPont = 0;
		state = GSTATE.MENU;
		speedY = 2;
		gameItem.ResetAmitAkarsz(12);
		gameItem.ResetAmitAkarsz(13);
		for(int i = 0; i < AllItems.size(); i++){
			AllItems.get(i).SetVisible(true);
		}
	}


	ArrayList<Float> stPos = new ArrayList<>();
	public void stPosSet(){

		for(int i=0;i<Zombik.size();i++){
			stPos.add(Zombik.get(i).GetPositionX());
	}
	}

	ArrayList<Float> moveRange = new ArrayList<>();

	public void moverRange(){
		moveRange.add(200f);
		moveRange.add(320f);
		moveRange.add(300f);

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

			if (Zombik.get(i).GetPosition().x > stPos.get(i)+moveRange.get(i) && zomBIsAlive.get(i) == 1) {
				zomBDirection.set(i, 0);
			}
			if (Zombik.get(i).GetPosition().x < stPos.get(i)-moveRange.get(i) && zomBIsAlive.get(i) == 1) {
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