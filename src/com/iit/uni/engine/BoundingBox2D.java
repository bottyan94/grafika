package com.iit.uni.engine;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.iit.uni.engine.math.MATRIX4X4;
import com.iit.uni.engine.math.Vector2D;
import com.iit.uni.engine.math.Vector3D;
import com.iit.uni.game.Renderer;

/***********************************
 * Describes an AABB 2D bounding box
 * 
 * @author Mileff Peter
 * 
 *         University of Miskolc
 *
 */
public class BoundingBox2D {

	private final int AABB_POINTS_2D = 4;

	/// Minimum point of the Bounding Box
	private Vector2D minpoint;

	/// Maximum point of the Bounding Box
	private Vector2D maxpoint;

	/// Bounding box points
	private Vector2D[] bbPoints;

	/// Half width of the Box
	float boxHalfWidth;

	/// Half height of the Box
	private float boxHalfHeight;

	/// Transformation matrix
	private MATRIX4X4 m_mTransformationMatrix;

	/// BB is usable or not
	private boolean mEnabled;

	public String WriteBB() {
		return "1x : " + bbPoints[0].x + " 1y: " + bbPoints[0].y + " 2x: " + bbPoints[1].x + " 2y: " + bbPoints[1].y + " 3x: " + bbPoints[2].x + " 3y: " + bbPoints[2].y + " 4x: " + bbPoints[3].x + " 4y: " + bbPoints[3].y;
	}

	public BoundingBox2D() {
		boxHalfWidth = 0;
		boxHalfHeight = 0;

		mEnabled = true;

		minpoint = new Vector2D();
		maxpoint = new Vector2D();

		bbPoints = new Vector2D[AABB_POINTS_2D];
		for (int i = 0; i < bbPoints.length; i++) {
			bbPoints[i] = new Vector2D();
		}

		m_mTransformationMatrix = new MATRIX4X4();
	}

	///
	/// Constructor
	///
	public BoundingBox2D(com.iit.uni.engine.math.Vector2D minpoint2, com.iit.uni.engine.math.Vector2D maxPoint2) {

		boxHalfWidth = 0;
		boxHalfHeight = 0;

		bbPoints = new Vector2D[AABB_POINTS_2D];
		for (int i = 0; i < bbPoints.length; i++) {
			bbPoints[i] = new Vector2D();
		}

		m_mTransformationMatrix = new MATRIX4X4();

		mEnabled = true;

		minpoint = new Vector2D(minpoint2.x, minpoint2.y);
		maxpoint = new Vector2D(maxPoint2.x, maxPoint2.y);

		setUpBBPoints();
		searchMinMax();

		boxHalfWidth = (maxpoint.x - minpoint.x) / 2.0f;
		boxHalfHeight = (maxpoint.y - minpoint.y) / 2.0f;
	}

	///
	/// Set AABB points and calculates center
	///
	public void SetPoints(Vector2D min, Vector2D max) {
		minpoint.set(min.x, min.y);
		maxpoint.set(max.x, max.y);

		setUpBBPoints();
		searchMinMax();

		boxHalfWidth = (maxpoint.x - minpoint.x) / 2.0f;
		boxHalfHeight = (maxpoint.y - minpoint.y) / 2.0f;
	}

	///
	/// Set up the BB points from the min and max point
	///
	public void setUpBBPoints() {
		// 1. pont
		bbPoints[0].x = minpoint.x;
		bbPoints[0].y = minpoint.y;

		// 2. pont
		bbPoints[1].x = maxpoint.x;
		bbPoints[1].y = minpoint.y;

		// 3. pont
		bbPoints[2].x = maxpoint.x;
		bbPoints[2].y = maxpoint.y;

		// 4. pont
		bbPoints[3].x = minpoint.x;
		bbPoints[3].y = maxpoint.y;
	}

	///
	/// Search min and max point of the bounding box
	///
	public void searchMinMax() {
		Vector2D min = new Vector2D(bbPoints[0].x, bbPoints[0].y);
		Vector2D max = new Vector2D(bbPoints[0].x, bbPoints[0].y);

		// loop each 4 points
		for (int i = 0; i < AABB_POINTS_2D; i++) {
			if (bbPoints[i].x < min.x) {
				min.x = bbPoints[i].x;
			}
			if (bbPoints[i].y < min.y) {
				min.y = bbPoints[i].y;
			}

			if (bbPoints[i].x > max.x) {
				max.x = bbPoints[i].x;
			}
			if (bbPoints[i].y > max.y) {
				max.y = bbPoints[i].y;
			}
		}

		minpoint.x = min.x;
		minpoint.y = min.y;

		maxpoint.x = max.x;
		maxpoint.y = max.y;
	}

	///
	/// Scale transformation on BB
	///
	public void transformByScale(Vector2D scale_vector) {

		// load scale vector into the matrix
		m_mTransformationMatrix.scale(new Vector3D(scale_vector.x, scale_vector.y, 0.0f));

		// loop all the 8 points
		for (int i = 0; i < AABB_POINTS_2D; i++) {

			// transform the points
			Vector3D point = new Vector3D(bbPoints[i].x, bbPoints[i].y, 0.0f);
			m_mTransformationMatrix.transformPoint(point);
			bbPoints[i].set(point.x, point.y);
		}

		// Search min and max points
		searchMinMax();

		// Setup AABB box
		setUpBBPoints();

	}

	///
	/// Translate transformation on BB
	///
	public void transformByTranslate(Vector2D translate_vector) {
		// load translate vector into the matrix
		m_mTransformationMatrix.identity();
		m_mTransformationMatrix.translate(new Vector3D(translate_vector.x, translate_vector.y, 0.0f));

		// loop all the 8 points
		for (int i = 0; i < AABB_POINTS_2D; i++) {
			// transform the points
			Vector3D point = new Vector3D(bbPoints[i].x, bbPoints[i].y, 0.0f);
			m_mTransformationMatrix.transformPoint(point);
			bbPoints[i].set(point.x, point.y);
		}

		// Search min and max points
		searchMinMax();

		// setup AABB box
		setUpBBPoints();
	}

	///
	/// Rotate transformation on BB
	///
	public void transformByRotate(float angle, int x, int y, int z) {
		if (x > 0) {
			// load rotate angle vector into the matrix
			m_mTransformationMatrix.rotate_x(angle);
		} else if (y > 0) {
			// load rotate angle vector into the matrix
			m_mTransformationMatrix.rotate_y(angle);
		} else if (z > 0) {
			// load rotate angle vector into the matrix
			m_mTransformationMatrix.rotate_z(angle);
		}

		// loop all the 4 points
		for (int i = 0; i < AABB_POINTS_2D; i++) {
			// transform the points
			// Prepeare points to center rotation!!!
			Vector3D point = new Vector3D(bbPoints[i].x - (minpoint.x + boxHalfWidth),
					bbPoints[i].y - (minpoint.y + boxHalfHeight), 0.0f);
			m_mTransformationMatrix.transformPoint(point);
			bbPoints[i].set(point.x + minpoint.x + boxHalfWidth, point.y + minpoint.y + boxHalfHeight);
		}

		// Search min and max points
		searchMinMax();

		// Setup AABB box
		setUpBBPoints();

	}

	///
	/// Draw AABB box
	///
	public void Draw() {

		if (mEnabled == false)
			return;

		// Data for quad
		float vertices[] = { minpoint.x, minpoint.y, 0.0f, minpoint.x, maxpoint.y, 0.0f, maxpoint.x, maxpoint.y, 0.0f,
				maxpoint.x, minpoint.y, 0.0f, minpoint.x, minpoint.y, 0.0f };

		// Create new VAO Object
		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);

		// Position VBO
		int vboId = glGenBuffers();

		FloatBuffer posBuffer = BufferUtils.createFloatBuffer(vertices.length);
		posBuffer.put(vertices).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		// Activate shader
		Renderer.mRenderer.lineShader.bind();
		Renderer.mRenderer.lineShader.setUniform("projectionMatrix", Renderer.mRenderer.projectionMatrix);

		// Set world/model matrix for this item
		Matrix4f modelMatrix = new Matrix4f();
		Renderer.mRenderer.lineShader.setUniform("modelMatrix", modelMatrix);

		// Set color
		Renderer.mRenderer.lineShader.setUniform4f("linecolor", 0.8f, 0.4f, 0.4f, 1.0f);

		// Render the VAO
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);

		glDisable(GL_DEPTH_TEST);
		glDrawArrays(GL_LINE_STRIP, 0, 5);
		glEnable(GL_DEPTH_TEST);
		// Restore state
		glDisableVertexAttribArray(0);

		Renderer.mRenderer.lineShader.unbind();
	}

	///
	/// Get AABB max point
	///
	public Vector2D GetMaxPoint() {
		return maxpoint;
	}

	///
	/// Get AABB min point
	///
	public Vector2D GetMinPoint() {
		return minpoint;
	}

	///
	/// Check two BB overlapping
	///
	public boolean CheckOverlapping(BoundingBox2D box) {

		// Checking x axis overlapping
		if (maxpoint.x < box.GetMinPoint().x || minpoint.x > box.GetMaxPoint().x) {
			// No collision
			return false;
		}

		// Checking y axis overlapping
		if (maxpoint.y < box.GetMinPoint().y || minpoint.y > box.GetMaxPoint().y) {
			// No collision
			return false;
		}

		return true;
	}

	///
	/// Get all the points of the BB
	///
	public Vector2D[] GetBBPoints() {
		return bbPoints;
	}

	///
	/// Set Box status
	///
	public void SetEnabled(boolean flag) {
		mEnabled = flag;
	}

	///
	/// Get Box status
	///
	public boolean isEnabled() {
		return mEnabled;
	}
}
