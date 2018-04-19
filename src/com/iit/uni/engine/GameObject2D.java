package com.iit.uni.engine;

import java.util.ArrayList;

import com.iit.uni.engine.math.Vector2D;

/**
 * 2D Game Object class
 * 
 * @author Mileff Peter
 * 
 *         University of Miskolc
 *
 */
public class GameObject2D {

	private boolean mVisible;

	private int mID;

	// Entity Name
	private String mName = "";

	private Vector2D m_vPosition; // Position of the object
	private Vector2D m_vDirection; // Direction of the movement
	private float mScale; // scale value

	private ArrayList<CSprite> m_Animations; // Animations

	private boolean m_bInFrustum; // Object is in screen frustum or not
	private boolean mCollidable; // Object is collidable or not

	private int m_uiCurrentAnim; // Current Animation Frame
	private int m_uiNumberOfFrames; // Number of Animations

	private float m_fSpeed; // Speed of the object
	private float m_fRotateValue; // rotate value of the object

	private int m_iZindex; // Z index of the object

	private BoundingBox2D mBBoxOriginal;
	private BoundingBox2D mBBoxTransformed;

	///
	/// Default Constructor
	///
	public GameObject2D() {
		m_vDirection = new Vector2D(0.0f, 0.0f);
		m_vPosition = new Vector2D(0.0f, 0.0f);
		m_Animations = new ArrayList<CSprite>();
		m_fSpeed = 1.0f;
		m_uiNumberOfFrames = 0;
		m_uiCurrentAnim = 0;
		mVisible = true;
		mID = 0;
		m_fRotateValue = 0.0f;
		m_iZindex = 0;
		m_bInFrustum = false;

		mScale = 1.0f;

		mCollidable = true;
	}

	///
	/// Init GameObject and Load animations
	///
	public GameObject2D(String spriteFilename, int numOfFrames, int ID) {

		CSprite sprite = new CSprite(spriteFilename, numOfFrames, 0.0f, 0.0f);
		m_Animations = new ArrayList<CSprite>();
		m_Animations.add(sprite);
		m_uiNumberOfFrames = 1;
		m_uiCurrentAnim = 0;

		m_vPosition = new Vector2D(0.0f, 0.0f);
		m_vDirection = new Vector2D(0.0f, 0.0f);
		mScale = 1.0f;

		m_fSpeed = 1.0f;
		m_Animations.get(m_uiCurrentAnim).SetAnimationSpeed(25);
		mVisible = true;

		mID = ID;

		m_fRotateValue = 0.0f;
		m_iZindex = 0;
		m_bInFrustum = false;

		mCollidable = true;
	}

	public float GetX(){
		return m_vPosition.getX();
	}

	public float GetY(){
		return m_vPosition.getY();
	}

	///
	/// Add new frame to the animation array
	///
	public void AddFrame(CSprite frame) {
		if (frame == null)
			return;

		m_uiNumberOfFrames++;
		m_Animations.add(frame);
	}

	///
	/// Add new frame to the animation array
	///
	public void AddFrame(String spriteFilename, int numOfFrames) {
		CSprite sprite = new CSprite(spriteFilename, numOfFrames, m_vPosition.x, m_vPosition.y);

		m_Animations.add(sprite);
		m_uiNumberOfFrames++;
	}

	///
	/// Return frame by index
	///
	public CSprite GetFrame(int index) {
		if (index < m_uiNumberOfFrames)
			return m_Animations.get(index);

		return null;
	}

	///
	/// Get the current frame
	///
	public CSprite GetCurrentFrame() {
		return m_Animations.get(m_uiCurrentAnim);
	}

	///
	/// Set position of the object
	///
	public void SetPosition(Vector2D pos) {
		m_vPosition.set(pos.x, pos.y);
	}

	///
	/// Set position of the object
	///
	public void SetPosition(float x, float y) {
		m_vPosition.set(x, y);
	}

	///
	/// Get position of the object. It includes with layer camera translation.
	///
	public Vector2D GetPosition() {
		return m_vPosition;
	}

	public float GetPositionX() {
		return m_vPosition.x;
	}

	public float GetPositionY() {
		return m_vPosition.y;
	}

	///
	/// Sets the object's speed
	///
	public void SetSpeed(float speed) {
		m_fSpeed = speed;
	}

	///
	/// Get the object's speed
	///
	public float GetSpeed() {
		return m_fSpeed;
	}

	///
	/// Set the object visible
	///
	public void SetVisible(boolean flag) {
		mVisible = flag;
	}

	///
	/// Return object is visible or not
	///
	public boolean GetVisible() {
		return mVisible;
	}

	///
	/// Draw frames. Loop animation.
	///
	public void Draw() {
		if (m_uiNumberOfFrames > 0) {
			if (mVisible == true) {
				// Draw the current frame
				m_Animations.get(m_uiCurrentAnim).Draw(m_vPosition);
			}
		}
	}

	///
	/// Draw current frames once. Loop animation once.
	///
	public void DrawOnce() {
		if (m_uiNumberOfFrames > 0) {
			if (mVisible == true) {
				m_Animations.get(m_uiCurrentAnim).DrawOne(m_vPosition);
			}
		}
	}

	///
	/// Draw the sprite in x, y positions
	/// @param x - coord x
	/// @param y - coord y
	public void DrawOnce(float x, float y) {
		if (m_uiNumberOfFrames > 0) {
			if (mVisible == true) {
				m_Animations.get(m_uiCurrentAnim).DrawOne(x, y);
			}
		}
	}

	///
	/// Set Animation Speed
	/// @param fps - speed
	/// @param frame - which animation (-1 means all)
	public void SetAnimationSpeed(int fps, int frame) {
		if (frame == -1) {
			// loop all animations
			for (int i = 0; i < m_uiNumberOfFrames; ++i) {
				m_Animations.get(i).SetAnimationSpeed(fps);
			}
		} else {
			m_Animations.get(frame).SetAnimationSpeed(fps);
		}
	}

	///
	/// Reset Frame
	/// @param frame
	public void ResetFrame(int frame) {
		if (frame < m_uiNumberOfFrames) {
			m_Animations.get(1 + frame).Reset();
		}
	}

	///
	/// Reset current Frame
	///
	public void ResetCurrentFrame() {
		m_Animations.get(m_uiCurrentAnim).Reset();
	}

	///
	/// Set current frame
	/// @param frame number
	///
	public void SetCurrentFrame(int frame) {
		if (m_uiNumberOfFrames > frame) {
			m_uiCurrentAnim = frame;
		}
	}

	///
	/// Sets the direction of the object
	///
	public void SetDirection(Vector2D dir) {
		m_vDirection = dir;
		m_vDirection.normalize();
	}

	///
	/// Gets the direction of the object
	///
	public Vector2D GetDirection() {
		return m_vDirection;
	}

	///
	/// Set Rotation Value
	///
	public void SetRotate(float value) {
		m_fRotateValue = value;

		for (int i = 0; i < m_uiNumberOfFrames; i++) {
			m_Animations.get(i).SetZRotation(m_fRotateValue);
		}
	}

	///
	/// Get Rotation Value
	///
	public float GetRotateValue() {
		return m_fRotateValue;
	}

	///
	/// Draw the Bounding Box
	///

	public void SetBoundingBox() {

		Vector2D maxpoint = new Vector2D(GetPositionX() + 320f, GetPositionY() + 640f);

		mBBoxOriginal = new BoundingBox2D(GetPosition() , maxpoint);
		mBBoxTransformed = new BoundingBox2D(GetPosition() , maxpoint);
	}

	public void DrawBoundingBox() {
		System.out.println(mBBoxOriginal.WriteBB());
	}

	public BoundingBox2D GetCurrentBBox() {
		return mBBoxOriginal;
	}

	///
	/// Set Z index of the object. Needs for proper rendering order.
	///
	public void SetZIndex(int value) {
		m_iZindex = value;
	}

	///
	/// Get Z index of the object.
	///
	public int GetZIndex() {
		return m_iZindex;
	}

	///
	/// Returns that object is in frustum or not
	///
	boolean IsInFrustum() {
		return m_bInFrustum;
	}

	///
	/// Sets scale of the object. All object's animation frame will be scaled.
	///
	public void SetScale(float size) {

		mScale = size;

		// loop all animations
		for (int i = 0; i < m_uiNumberOfFrames; ++i) {
			m_Animations.get(i).SetScale(size);
		}
	}

	///
	/// Returns the scale of the object
	///
	public float GetScale() {
		return mScale;
	}

	///
	/// Set object collidable flag
	///
	public void SetCollidable(boolean flag) {
		mCollidable = flag;
	}

	///
	/// Is object collidable
	///
	public boolean isCollidable() {
		return mCollidable;
	}

	public void cleanUp() {
		for (int i = 0; i < m_Animations.size(); i++) {
			m_Animations.get(i).cleanUp();
		}
	}

	public int GetID() {
		return mID;
	}

	public void SetID(int id) {
		mID = id;
	}

	public String GetName() {
		return mName;
	}

	public void SetName(String name) {
		mName = name;
	}

}
