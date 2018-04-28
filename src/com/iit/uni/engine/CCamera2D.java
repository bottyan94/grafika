package com.iit.uni.engine;

import com.iit.uni.engine.math.MATRIX4X4;
import com.iit.uni.engine.math.Vector2D;
import com.iit.uni.engine.math.Vector3D;

/******************************************
 * Describes a simple 2D camera object
 *
 * @author Mileff Peter
 *
 *****************************************/
public class CCamera2D {

	// Camera position
	private Vector2D mPosition;
	private float recent;
	private float kulonbseg;

	// Camera ID
	private int mID;

	// Transformation matrix
	private MATRIX4X4 transformMatrix;

	public CCamera2D() {

		transformMatrix = new MATRIX4X4();
		mPosition = new Vector2D();
		mID = -1;
	}

	public CCamera2D(float x, float y, int id) {
		transformMatrix = new MATRIX4X4();
		mPosition = new Vector2D();
		mID = id;
		mPosition.set(x, y);
	}

	///
	/// Move left the camera
	///
	public void MoveLeft(float value) {
		mPosition.x += value;
	}

	///
	/// Move right the camera
	///
	public void MoveRight(float value) {
		mPosition.x -= value;
	}

	///
	/// Move up the camera
	///
	public void MoveUp(float value) {
		mPosition.y += value;
	}

	///
	/// Move down the camera
	///
	public void MoveDown(float value) {
		mPosition.y -= value;
	}

	///
	/// Moves relative with a value
	///
	public void MoveRelative(float valueX, float valueY) {
		mPosition.x += valueX;
		mPosition.y += valueY;
	}

	///
	/// Camera look
	///
	public MATRIX4X4 GetViewMatrix() {

		transformMatrix.identity();


		// Translate to position
		transformMatrix.translate(new Vector3D(mPosition.x, mPosition.y, 0));
		return transformMatrix;
	}

	///
	/// set the camera position
	///
	public void SetPosition(float x, float y) {
		mPosition.set(x, y);
	}

	///
	/// set the camera position
	///
	public void SetPosition(Vector2D vec) {
		mPosition = vec;
	}

	///
	/// Get Position
	///
	public Vector2D GetPosition() {
		return mPosition;
	}

	///
	/// Set camera ID
	///
	public void SetID(int id) {
		mID = id;
	}

	///
	/// Get camera ID
	///
	public int GetID() {
		return mID;
	}

	public float GetX() { return mPosition.x; }

	public float GetY() { return mPosition.y; }

	public void SetXAndGetKulonbseg(float uj) {
		recent = mPosition.x;
		kulonbseg = recent-uj;
		if(kulonbseg > 0) {
			MoveRight(kulonbseg);
		}
		if(kulonbseg < 0) {
			MoveLeft(kulonbseg);
		}
	}
}
