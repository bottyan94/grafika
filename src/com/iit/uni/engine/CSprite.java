package com.iit.uni.engine;

import java.util.ArrayList;

import org.joml.Matrix4f;

import com.iit.uni.engine.math.Vector2D;

/*******************************************************************************
 * Animated Texture class as sprite
 * 
 * @author Mileff Peter
 * 
 *         University of Miskolc
 *
 ******************************************************************************/
public class CSprite {

	// Frames vector
	private ArrayList<CSpriteFrame> m_vFrames;

	// Actual frame
	private int m_iActualFrame;

	// Position of the sprite
	private Vector2D m_vSpritePosition;

	// The last time the animation was update
	private long m_iLastUpdate;

	// The number of frames per second
	private int m_iFps;

	// Z oriented Orientation of the sprite
	private float mRotationZ;

	// Scale of the sprite
	private float mScale;

	public CSprite() {
		m_iActualFrame = 0;
		m_vFrames.clear();
		m_iLastUpdate = System.currentTimeMillis();
		m_iFps = 15; // default FPS animation rate
		m_vFrames = new ArrayList<CSpriteFrame>();
	}

	public CSprite(String filenames, int numOfFrames, float X, float Y) {
		m_iActualFrame = 0;
		m_iLastUpdate = System.currentTimeMillis();
		m_iFps = 15; // default FPS animation rate
		m_vFrames = new ArrayList<CSpriteFrame>();

		/** Loading textures */
		LoadTextures(filenames, numOfFrames);

		m_vSpritePosition = new Vector2D(X, Y);
	}

	/**
	 * Loads sprite textures
	 * 
	 * @param filenames
	 * @param numOfFrames
	 */
	public void LoadTextures(String filenames, int numOfFrames) {

		if (numOfFrames == 1) {
			Texture2D tex = new Texture2D();
			tex.CreateTexture(filenames + ".png");

			// Create sprite frame
			CSpriteFrame newFrame = new CSpriteFrame(tex, "Frame_1");
			newFrame.AddBoundingBox(new Vector2D(0.0f, 0.0f), new Vector2D(tex.GetWidth(), tex.GetHeight()));
			m_vFrames.add(newFrame);

		} else {
			/** Loading single textures */
			for (int i = 0; i < numOfFrames; i++) {
				Texture2D tex = new Texture2D();
				tex.CreateTexture(filenames + (i + 1) + ".png");

				// Create sprite frame
				CSpriteFrame newFrame = new CSpriteFrame(tex, "Frame_" + i);
				newFrame.AddBoundingBox(new Vector2D(0, 0), new Vector2D(tex.GetWidth(), tex.GetHeight()));

				m_vFrames.add(newFrame);
			}
		}
	}

	/** Draw Animated Sprite */
	public void Draw() {
		Texture2D tex = m_vFrames.get(m_iActualFrame).GetTexture();
		tex.Draw(m_vSpritePosition);
		Update();
	}

	/**
	 * Get the transformed AABB
	 * 
	 * @return
	 */
	public BoundingBox2D getCurrentFrameTransformedBoundingBox() {

		CSpriteFrame currentFrame = m_vFrames.get(m_iActualFrame);

		BoundingBox2D original = currentFrame.GetOriginalBB();
		BoundingBox2D transformed = currentFrame.GetTransformedBB();

		transformed.SetPoints(original.GetMinPoint(), original.GetMaxPoint());
		transformed.transformByRotate(0, 0, 0, 1);
		transformed.transformByScale(new Vector2D(1, 1));
		transformed.transformByTranslate(new Vector2D(m_vSpritePosition.x, m_vSpritePosition.y));

		return transformed;
	}

	/** Draw Animated Sprite */
	public void Draw(Vector2D pos) {
		Texture2D tex = m_vFrames.get(m_iActualFrame).GetTexture();
		tex.Draw(pos);
		Update();
	}

	/** Update frames */
	public void Update() {
		if (1000.0f / m_iFps < (System.currentTimeMillis() - m_iLastUpdate)) {
			m_iLastUpdate = System.currentTimeMillis();
			if (++m_iActualFrame == m_vFrames.size()) {
				m_iActualFrame = 0;
			}
		}
	}

	/** Draw Animated Sprite */
	public void DrawOne() {
		Texture2D tex = m_vFrames.get(m_iActualFrame).GetTexture();
		tex.Draw(m_vSpritePosition);
		if (1000.0f / m_iFps < (System.currentTimeMillis() - m_iLastUpdate)) {
			m_iLastUpdate = System.currentTimeMillis();
			if (m_iActualFrame == m_vFrames.size()) {
				m_iActualFrame++;
			}
		}
	}

	/** Draw Animated Sprite */
	public void DrawOne(Vector2D pos) {
		Texture2D tex = m_vFrames.get(m_iActualFrame).GetTexture();
		tex.Draw(pos);
		if (1000.0f / m_iFps < (System.currentTimeMillis() - m_iLastUpdate)) {
			m_iLastUpdate = System.currentTimeMillis();
			if (m_iActualFrame == m_vFrames.size()) {
				m_iActualFrame++;
			}
		}
	}

	/** Draw Animated Sprite */
	public void DrawOne(float posX, float posY) {
		Texture2D tex = m_vFrames.get(m_iActualFrame).GetTexture();

		tex.Draw(posX, posY);

		if (1000.0f / m_iFps < (System.currentTimeMillis() - m_iLastUpdate)) {
			m_iLastUpdate = System.currentTimeMillis();
			if (m_iActualFrame == m_vFrames.size()) {
				m_iActualFrame++;
			}
		}
	}

	/** Draw One Frane of the Animated Sprite */
	public void DrawFrame(int frame) {
		Texture2D tex = m_vFrames.get(frame).GetTexture();
		tex.Draw(m_vSpritePosition);
	}

	/** Draw One Frane of the Animated Sprite in X, Y position */
	public void DrawFrame(int frame, float X, float Y) {
		Texture2D tex = m_vFrames.get(frame).GetTexture();
		tex.Draw(X, Y);
	}

	/** Draw One Frane of the Animated Sprite */
	public void DrawFrame(int frame, Vector2D pos) {
		Texture2D tex = m_vFrames.get(frame).GetTexture();
		tex.Draw(pos);
	}

	/** Reset Animation */
	public void Reset() {
		m_iActualFrame = 0;
	}

	/** Get one frame of the Animated Sprite */
	public Texture2D GetTexture(int frame) {
		return m_vFrames.get(frame).GetTexture();
	}

	/** Get current frame of the Animated Sprite */
	public Texture2D GetCurrentFrameTexture() {
		return m_vFrames.get(m_iActualFrame).GetTexture();
	}

	/** Get one frame of the Animated Sprite */
	public Matrix4f GetCurrentFrameTextureWorldMatrix() {
		Texture2D frame = m_vFrames.get(m_iActualFrame).GetTexture();
		frame.setPosition(m_vSpritePosition.x, m_vSpritePosition.y, 0);
		return frame.getWorldMatrix();
	}

	/** Get the frame number of the Sprite */
	public int GetNumberOfFrames() {
		return m_vFrames.size();
	}

	/** Set Animation Speed */
	public void SetAnimationSpeed(int fps) {
		m_iFps = fps;
	}

	/** Set sprite position */
	public void SetPosition(float X, float Y) {
		m_vSpritePosition.set(X, Y);
	}

	/** Set sprite position */
	public void SetPosition(Vector2D pos) {
		m_vSpritePosition = pos;
	}

	public Vector2D GetSpritePos() {
		return m_vSpritePosition;
	}

	public float GetSpritePosX() {
		return m_vSpritePosition.x;
	}

	public float GetSpritePosY() {
		return m_vSpritePosition.y;
	}

	public void SetPositionX(float posX) {
		m_vSpritePosition.x = posX;
	}

	public void SetPositionY(float posY) {
		m_vSpritePosition.y = posY;
	}

	public void SetZRotation(float value) {
		mRotationZ = value;
	}

	public float GetZRotation() {
		return mRotationZ;
	}

	///
	/// Set Scale of the Sprite
	///
	public void SetScale(float size) {
		mScale = size;
		for (int i = 0; i < m_vFrames.size(); i++) {
			m_vFrames.get(i).GetTexture().setScale(size);
		}
	}

	///
	/// Get Scale of the Sprite
	///
	public float GetScale() {
		return mScale;
	}

	/** Returns the fps of the animation */
	public int GetAnimationSpeed() {
		return m_iFps;
	}

	public void cleanUp() {
		for (int i = 0; i < m_vFrames.size(); i++) {
			Texture2D tex = m_vFrames.get(i).GetTexture();
			tex.cleanup();
		}
	}
}
