package com.iit.uni.engine;

import java.util.ArrayList;

/**
 * Class for managing 2D Scenes
 * 
 * @author Mileff Peter
 * 
 *         University of Miskolc
 *
 */
public class C2DSceneManager {

	private ArrayList<C2DScene> mScenes;

	public C2DSceneManager() {

		mScenes = new ArrayList<C2DScene>();
	}

	///
	/// Register a scene
	///
	public void RegisterScene(C2DScene scene) {
		if (scene != null)
			mScenes.add(scene);
	}

	///
	/// Render 2D objects
	/// objects
	///
	public void Render() {
		for (int i = 0; i < mScenes.size(); i++) {
			mScenes.get(i).Render();
		}
	}

	///
	/// Free an object based on its id
	///
	public void FreeObject(int id) {
		for (int i = 0; i < mScenes.size(); i++)
			mScenes.get(i).FreeObject(id);
	}

	///
	/// Free all registered objects
	///
	public void FreeObjects() {
		for (int i = 0; i < mScenes.size(); i++)
			mScenes.get(i).FreeObjects();
	}

	///
	/// Releases a whole scene
	///
	public void FreeAScene(String name) {
		for (int i = 0; i < mScenes.size(); i++) {
			if (mScenes.get(i).GetName().equals(name) == true) {
				mScenes.remove(i);
				break;
			}
		}
	}

	///
	/// Get Object by Name
	///
	public GameObject2D GetObjectByName(String name) {
		for (int i = 0; i < mScenes.size(); i++) {
			GameObject2D gameObject = mScenes.get(i).GetObjectByName(name);
			if (gameObject != null) {
				return gameObject;
			}
		}

		return null;
	}

	///
	/// Get a scene by ID
	///
	public C2DScene GetScene(int sceneID) {
		if (sceneID < mScenes.size())
			return mScenes.get(sceneID);

		return null;
	}

	///
	/// Get a scene by name
	///
	public C2DScene GetSceneByName(String name) {
		for (int i = 0; i < mScenes.size(); i++) {
			C2DScene scene = mScenes.get(i);
			if (scene.GetName().equals(name) == true)
				return scene;
		}

		return null;
	}

}
