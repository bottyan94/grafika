package com.iit.uni.engine;

import java.util.ArrayList;

/**
 * Class for representing a 2D Layer
 * 
 * @author Mileff Peter
 * 
 *         University of Miskolc
 *
 */
public class C2DGraphicsLayer {

	/// Game Object List
	ArrayList<GameObject2D> mObjectList;

	/// Background and static textures
	ArrayList<Texture2D> mTextures;

	/// Is layer visible or not
	boolean mVisible;

	/// Layer name
	String mName;

	/// Layer ID
	int mID;

	///
	/// Class constructor
	///
	public C2DGraphicsLayer() {

		mTextures = new ArrayList<Texture2D>();
		mObjectList = new ArrayList<GameObject2D>();
		// Default is visible
		mVisible = true;

		// Set id to default
		mID = -1;

		mName = "";
	}

	///
	/// Class constructor
	///
	public C2DGraphicsLayer(String name, int id) {

		mTextures = new ArrayList<Texture2D>();
		mObjectList = new ArrayList<GameObject2D>();

		mName = name;
		mID = id;
		mVisible = true;
	}

	///
	/// Add game object to layer
	///
	public int AddGameObject(GameObject2D gameobject) {
		if (gameobject != null) {

			mObjectList.add(gameobject);
			return mObjectList.size() - 1;
		}
		return -1;
	}
	
	public int AddGameObject(ArrayList<GameObject2D> gameobject) {
		
		if (gameobject != null) {
			
			for (int j = 0; j < gameobject.size(); j++) {
				System.out.println(j);
				mObjectList.add(gameobject.get(j));	
			}
			
			return mObjectList.size() - 1;
		}
		return -1;
	}
	

	///
	/// Add background/static texture
	///
	public int AddTexture(Texture2D texture) {
		if (texture != null) {
			mTextures.add(texture);
			return mTextures.size() - 1;
		}

		return -1;
	}

	///
	/// Get background/static texture
	///
	public Texture2D GetTexture(int index) {
		if (index > -1 && index < (int) mTextures.size())
			return mTextures.get(index);

		return null;
	}

	///
	/// Get background/static texture by ID
	///
	public Texture2D GetTextureByID(int targetid) {

		for (int i = 0; i < mTextures.size(); i++) {
			if (mTextures.get(i).GetID() == targetid) {
				return mTextures.get(i);
			}
		}

		return null;
	}

	///
	/// Get Object by ID
	///
	public GameObject2D GetObjectByID(int objectID) {
		for (int i = 0; i < mObjectList.size(); i++) {
			if (mObjectList.get(i).GetID() == objectID) {
				return mObjectList.get(i);
			}
		}

		return null;
	}

	///
	/// Get Object by Name
	///
	public GameObject2D GetObjectByName(String name) {
		for (int i = 0; i < mObjectList.size(); i++) {
			GameObject2D obj = mObjectList.get(i);
			if (obj.GetName().equals(name) == true) {
				return obj;
			}
		}

		return null;
	}

	///
	/// Get Layer Objects
	///
	public ArrayList<GameObject2D> GetObjects() {
		return mObjectList;
	}

	///
	/// Clear the layer
	///
	public void Clear() {
		mObjectList.clear();
		mTextures.clear();
	}

	///
	/// Set layer visibility flag
	///
	public void SetVisible(boolean flag) {
		mVisible = flag;
	}

	///
	/// Is layer visible
	///
	public boolean IsVisible() {
		return mVisible;
	}

	///
	/// Render 2D objects and textures
	/// objects
	///
	public void Render() {
		if (mVisible == false)
			return;

		// Draw static textures first
		for (int i = 0; i < mTextures.size(); i++)
			mTextures.get(i).Draw();

		// Drawing objects
		for (int i = 0; i < mObjectList.size(); i++) {

			GameObject2D obj = mObjectList.get(i);

			if (obj.GetVisible() == false)
				continue;

			obj.Draw();
		}
	}

	///
	/// Sort objects by Z index
	///
	public void SortByZIndex() {

	}

	///
	/// Free all registered objects
	///
	public void FreeObjects() {
		mObjectList.clear();
	}

	///
	/// Remove an object, but do not free its memory
	///
	public void RemoveGameObjectByID(int objectID) {
		int foundListIndex = -1;
		for (int i = 0; i < mObjectList.size(); i++) {
			if (mObjectList.get(i).GetID() == objectID) {
				foundListIndex = i;
				break;
			}
		}

		// If object has founded
		if (foundListIndex != -1)
			mObjectList.remove(foundListIndex);
	}

	public void SetName(String name) {
		mName = name;
	}

	public String GetName() {
		return mName;
	}

	///
	/// Set Layer ID
	///
	public void SetID(int id) {
		mID = id;
	}

	///
	/// Return Layer ID
	///
	public int GetID() {
		return mID;
	}

}
