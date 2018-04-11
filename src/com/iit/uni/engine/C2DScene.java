package com.iit.uni.engine;

import java.util.ArrayList;

/**
 * Class for representing a 2D Scene
 * 
 * @author Mileff Peter
 * 
 *         University of Miskolc
 *
 */
public class C2DScene {

	/// Layer for objects
	ArrayList<C2DGraphicsLayer> mLayers;

	/// Name of the scene
	String mName = "";

	/// Visibility flag
	boolean mVisible;

	public C2DScene() {

		mLayers = new ArrayList<C2DGraphicsLayer>();
		mName = "Sample Scene";
		mVisible = true;
	}

	///
	/// Set Scene name
	///
	public void SetName(String name) {
		mName = name;
	}

	///
	/// Get Scene name
	///
	public String GetName() {
		return mName;
	}

	///
	/// Register a 2D Layer
	///
	public void RegisterLayer(C2DGraphicsLayer layer) {
		if (layer != null) {
			mLayers.add(layer);
		}
	}

	///
	/// Render 2D objects
	///
	public void Render() {
		if (mVisible == false)
			return;

		if (mLayers.size() == 0)
			return;

		for (int i = 0; i < mLayers.size(); i++) {
			// Reset collision flag
			mLayers.get(i).Render();
		}
	}

	///
	/// Free an object based on its id
	///
	public void FreeObject(int id) {
		for (int i = 0; i < mLayers.size(); i++)
			mLayers.get(i).RemoveGameObjectByID(id);
	}

	///
	/// Free all registered objects
	///
	public void FreeObjects() {
		for (int i = 0; i < mLayers.size(); i++)
			mLayers.get(i).FreeObjects();
	}

	///
	/// Return the game object reference based its ID
	///
	public GameObject2D GetObjectByID(int gameObjectID) {
		for (int i = 0; i < mLayers.size(); i++) {

			GameObject2D obj = mLayers.get(i).GetObjectByID(gameObjectID);
			if (obj != null) {
				return obj;
			}
		}

		return null;
	}

	///
	/// Return the game object reference based its name
	///
	public GameObject2D GetObjectByName(String name) {
		for (int i = 0; i < mLayers.size(); i++) {
			GameObject2D obj = mLayers.get(i).GetObjectByName(name);
			if (obj != null) {
				return obj;
			}
		}

		return null;
	}

	///
	/// Sort Layer Objects by z index
	///
	public void SortByZIndex() {
		for (int i = 0; i < mLayers.size(); i++)
			mLayers.get(i).SortByZIndex();
	}

	///
	/// Get layer pointer
	///
	public C2DGraphicsLayer GetLayer(int index) {
		if (mLayers.size() > index)
			return mLayers.get(index);

		return null;
	}

	///
	/// Set Visibility flag
	///
	public void SetVisible(boolean flag) {
		mVisible = flag;
	}

	///
	/// Get Visibility flag
	///
	public boolean isVisible() {
		return mVisible;
	}

}
