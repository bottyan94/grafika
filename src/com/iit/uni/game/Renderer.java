package com.iit.uni.game;

import com.iit.uni.engine.Utils;
import com.iit.uni.engine.Window;
import com.iit.uni.engine.graph.ShaderProgram;
import com.iit.uni.engine.graph.Transformation;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Simple renderer class
 * 
 * @author Mileff Peter
 *
 */
public class Renderer {

	private final Transformation transformation;

	public ShaderProgram shaderProgram;

	// Our line drawing shader
	public ShaderProgram lineShader;

	// Orthogonal projection Matrix
	public Matrix4f projectionMatrix;

	public static Renderer mRenderer;

	public Renderer() {
		transformation = new Transformation();
	}

	public void init(Window window) throws Exception {

		mRenderer = this;

		// Create shader
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(Utils.loadFile("shaders/vertex.vs"));
		shaderProgram.createFragmentShader(Utils.loadFile("shaders/fragment.fs"));
		shaderProgram.link();

		// Create uniforms for world and projection matrices and texture
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("worldMatrix");
		shaderProgram.createUniform("texture_sampler");

		lineShader = new ShaderProgram();
		lineShader.createVertexShader(Utils.loadFile("shaders/line.vs"));
		lineShader.createFragmentShader(Utils.loadFile("shaders/line.fs"));
		lineShader.link();

		// Create uniforms for world and projection matrices and texture
		lineShader.createUniform("projectionMatrix");
		lineShader.createUniform("modelMatrix");
		lineShader.createUniform("linecolor");

		// Update orthogonal projection Matrix
		projectionMatrix = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Window window) {
		clear();

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		// Render the sprite
		DummyGame.sceneManager.Render();

		// Render bounding box
		// gameObject.GetCurrentFrame().getCurrentFrameTransformedBoundingBox().Draw();*/
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
}
